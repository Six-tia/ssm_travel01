//由于引入了jQuery，所以可以直接用$
//表示文档（页面）加载完成后执行此匿名函数
$(document).ready(function(){
	//js添加断点方法如下，直接加上debugger关键字
	//debugger
	$("#queryFormId")
		.on("click", ".btn-search", doQueryObjects)
		.on("click", ".btn-valid, .btn-invalid", doValidById)
		.on("click", ".btn-add", doLoadEditPage)
	doGetObjects();
})

//加载编辑页面
function doLoadEditPage(){
	var url = "project/editUI.do";
	var title = "添加项目信息";
	var cls = $(this).attr("class");
	if($(this).hasClass("btn-add")){
		title = "添加项目信息"
	}else if($(this).hasClass("btn-update")){
		title = "更新项目信息"
	}
	//$(".content").load(url);
	//本项目模态框在index.jsp中，且默认是隐藏的
	//在模态框中异步加载显示编辑页面
	//模态框（madal）：覆盖在父窗口的子窗口
	$("#modal-dialog .modal-body")
		.load(url, function(){
			$(".modal-title").html(title);
			//页面加载完成显示模态框（bootstrap的modal函数show：显示 hide：隐藏）
			$("#modal-dialog").modal("show");
		})
}

/**点击查询按钮执行方法*/
function doQueryObjects(){
	//1.初始化当前页码数（查询后应该从第一页显示）
	$("#pageId").data("pageCurrent", 1);
	//2.根据条件查询数据
	doGetObjects();
}

/**获取项目信息*/
function doGetObjects(){
	//此处测试从project_list.jsp文件直接进入，因此url不用加project/
	//若正常情况下project_list.jsp是index.jsp文件的异步加载，应该加上project/
	//var url = "project/doGetProjects.do";
	var url = "project/doGetPageObjects.do";
	//ajax的getJSON函数的参数赋值方式，此名字为Controller类对应的参数名，
	//两者相同才能保证注入
	var pageCurrent = $("#pageId").data("pageCurrent");

	if(!pageCurrent)
		pageCurrent = 1;
	var params = {"pageCurrent":pageCurrent};
	//若以下两个变量都有值，则params有3个键值对
	params.name = $("#searchNameId").val();
	params.valid = $("#searchValidId").val();
	console.log(params); //用于检测params
	//第一种写法
	// $.ajax({
	// 	url:url,
	// 	type:"get",
	//  async:false, //默认为true（异步），false为同步
	// 	dataType:"json",
	// 	success:function(result){
	//
	// 	}
	// });
	//第二种写法（默认为get请求）
	//若要使用result，即以下方法return result，会出现错误，因为
	//ajax是异步的，即此函数未执行完就会return了，接着执行后面的
	// $.getJSON(url, function(result){
	// 	console.log(result); //自动将json串封装为json对象,在控制台打印
	// 	//return result;
	// 	setTableBodyRows(result);
	// });
	$.getJSON(url, params, function(result){
			//console.log(result); //map格式json对象
		if(result.state==1){
			setTableBodyRows(result.data.list); //取出map中key为list的value
			setPagination(result.data.pageObject);
		}else{
			alert(result.message);
		}
	});

}

/*将数据显示在表格（table对象）的body中*/
function setTableBodyRows(result){
	//1.获得tbody对象（project_list.jsp中id为tbodyId的对象）
	var tbody = $("#tbodyId");
	tbody.empty();
	//2.迭代数据集Result
	//for(var i = 0; i < result.length; i++){}
	for(var i in result){
		//2.1.构建tr对象
		var tr = $("<tr></tr>>");
		//2.2.构建每行td对象,并填充具体数据
		//写法1
		//var td0 = $("<td></td>>");
		//td0.append(result[i].id);
		//写法2
			//checkBox设定了name而非id，因为id是唯一的，但是name可以有多个值（多条记录）
		var tds = "<td><input type = 'checkbox' name = 'checkedId' value = '" + result[i].id + "'></td>" +
			"<td>" + result[i].code + "</td>>" +
			"<td>" + result[i].name + "</td>>" +
			//因为result传来的是json数据，因此日期被转换为长整型，需要重新转换为日期
			//方法1：使用js自带的toLocaleDateString()方法转换
			//"<td>" + new Date(result[i].beginDate).toLocaleDateString() + "</td>>" +
			//"<td>" + new Date(result[i].endDate).toLocaleDateString() + "</td>>" +
			//方法2：继承了jackson中的类JsonSerializer<Date>，
			// 编写了com.tiaedu.travel.common.web.JsonDateTypeConvert.java实现自动转换
			//通过在对应的getter()方法上加上注解，使填充到json中时自动转换为目标格式
			"<td>" + result[i].beginDate + "</td>>" +
			"<td>" + result[i].endDate + "</td>>" +
			//1默认为有效，0默认为无效
			"<td>" + (result[i].valid?"valid":"invalid") + "</td>>" +
			//button的class为bootstrap定义好的，可以直接去bootstrap网站上查找喜欢的样式，
			//将对应类的名字加上就可以用了
			"<td><input type = 'button' value = '修改' class = 'btn btn-success'></td>>";
		//2.3.将td添加到tr中
		tr.append(tds);
		//2.4.将tr追加到tbody中
		tbody.append(tr);
	}
}

/*禁用或启用项目信息*/
function doValidById(){
	//1.设置valid值
	var valid;
	//1.1获取点击的对象
	//写法一：比较笨重
	// var cla = ${this}.attr("class");
	// if(cla == "btn btn-primary btn-invalid"){
	// 	valid = 0;
	// }
	//写法二：
	if($(this).hasClass("btn-valid")){
		valid = 1; //启用
	}
	if($(this).hasClass("btn-invalid")){
		valid = 0; //禁用
	}
	//2.获得选中的checkbox（id）的值
	var ids = "";
	//迭代input对象
	$("#tbodyId input[name='checkedId']")
		.each(function(){
			//判定此input对象是否是选中的
			//debugger
			if($(this).prop("checked")){ //prop为“checked”表示被选中
				if(ids==""){
					ids += $(this).val();
				}else{
					ids += "," + $(this).val(); //为项目id
				}
			}
		});
	console.log("valid=" + valid);
	console.log("ids=" + ids);
	if(ids == ""){
		alert("please select at least one project!");
		return;
	}
	//3.发起异步请求更新数据
	var url = "project/doValidById.do";
	var params={
		"valid":valid,
		"ids":ids
	}
	$.post(url,params,function(result){
		if(result.state==1){
			alert(result.message);
			//重新执行查询操作
			doGetObjects();
		}else{
			alert(result.message);
		}

	})
}


// /*点击添加按钮时执行一个动作
//  *1)初始化index页面的模态框(bootstrap 框架提供)
//  *2)在模态框内部显示project_edit.jsp
//  * */
// function doShowEditDialog(){
// 	var title;
// 	//1.定义模态框的标题
// 	if($(this).hasClass("btn-add")){
// 		title="添加项目"
// 	}
// 	if($(this).hasClass("btn-update")){
// 		//模态框上绑定id值(在修改页面中要根据此值获取数据)
// 		//data(key,value)函数用于绑定数据
// 		//data(key)函数用于获取数据
// 		$("#modal-dialog").data("id",$(this).parent().parent().data("id"));
// 		//定义修改时的标题
// 		title="修改项目,id为"+$("#modal-dialog").data("id");
// 	}
// 	//2.启动模态框,并加载页面
// 	//在模态框对应位置异步加载url
// 	var url="project/editUI.do";
// 	$("#modal-dialog .modal-body")
// 	.load(url,function(){//异步加载完成回调此函数
// 		//设置标题内容
// 		$(".modal-title").html(title);
// 		//显示模态框(index.jsp中)
// 		$("#modal-dialog").modal("show");
// 	});
// }








