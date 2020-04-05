//由于引入了jQuery，所以可以直接用$
//表示文档（页面）加载完成后执行此匿名函数
$(document).ready(function(){
	//js添加断点方法如下，直接加上debugger关键字
	//debugger
	doGetProjects();
})

/*获取项目信息*/
function doGetProjects(){
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
			console.log(result); //map格式json对象
			setTableBodyRows(result.list); //取出map中key为list的value
			setPagination(result.pageObject);
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
		var tds = "<td><input type = 'checkbox' name = 'chenkedID' value = '" + result[i].id + "'></td>" +
			"<td>" + result[i].code + "</td>>" +
			"<td>" + result[i].name + "</td>>" +
			//因为result传来的是json数据，因此日期被转换为长整型，需要重新转换为日期
			//方法1：使用js自带的toLocaleDateString()方法转换
			//"<td>" + new Date(result[i].beginDate).toLocaleDateString() + "</td>>" +
			//"<td>" + new Date(result[i].endDate).toLocaleDateString() + "</td>>" +
			//方法2：继承了jachson中的类JsonSerializer<Date>，
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





// $(document).ready(function(){
// 	//在queryFormId对应对象的btn-search元素上注册click事件
// 	$("#queryFormId").on("click",
// 			".btn-search",doQueryObjects);
// 	//在禁用和启用按钮上注册点击事件
// 	$("#queryFormId").on(
// 			"click",
// 			".btn-valid,.btn-invalid",
// 			 doValidById);
// 	//在添加,修改按钮上注册点击事件
// 	$('#queryFormId')
// 	.on("click",".btn-add,.btn-update",doShowEditDialog);
// 	//初始化页面数据
// 	doGetObjects();
//
// });
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
// /*执行禁用启用操作
//  * 1.获得数据(禁用或启动哪些项目信息)
//  * a)id(选中的那个checkbox的值)
//  * b)valid(由点击按钮决定)
//  * 2.发送异步请求,修改记录信息
//  * a)url
//  * b)params
//  * c)post(url,params,functions(result){})
//  */
// function doValidById(){
//  //debugger
//  //1.获得页面数据(valid,checkedIds)
//  //1.1 获得valid的值(根据点击的按钮)
//  var valid;//定义一个变量(默认值undefined)
//  //判定点击的按钮是启用还是禁用
//  //hasClass用于判定对象上是否有某个选择器
//  //$(this).attr("class"):课后了解此方法的含义及用法
//  if($(this).hasClass("btn-valid")){
// 	 valid=1;//表示启用
//  }
//  if($(this).hasClass("btn-invalid")){
// 	 valid=0;//表示禁用
//  }
//  //1.2获得选中的id值(可能1个也可能是多个)
//  var checkedIds=getCheckedIds();
//  console.log("checkedIds="+checkedIds);
//  if(checkedIds.length==0){
// 	 alert("请至少选择一项");
// 	 return;
//  }
//  //2.提交异步请求,更新对应记录的状态信息
//  //2.1 定义url (对应控制器中的一个方法)
//   var url="project/doValidById.do"
//  //2.2 将数据封装为一个json对象
//   var params={"checkedIds":checkedIds,"valid":valid};
//   //2.3发起类型为post的ajax请求(类型为post)
//   $.post(url,params,function(result){//回调函数
// 	 //debugger
// 	 //alert("result="+JSON.stringify(result));
// 	 if(result.state==1){//异步请求成功了
// 		 alert(result.message);//ok
// 		 doGetObjects();//重新查询
// 	 }else{//请求过程出现异常
// 		 alert(result.message);
// 	 }
//   });
//  }
// function getCheckedIds(){//1,2,3,4,5
//   var checkedIds="";
//   //1.遍历所有的checkbox,获得选中的值
//   /*$('tbody input[name="checkedItem"]').
//     each(function(){
//        if($(this).is(":checked")){}//课后了解
//     })
//   */
//   $('#tbodyId input[name="checkedItem"]').each(function(){//each函数用于迭代对象
// 	  //判定当前对象是否是选中的
// 	  if($(this).prop("checked")){
// 		  if(checkedIds==""){
// 			  checkedIds+=$(this).val();//id
// 		  }else{
// 			 checkedIds+=","+$(this).val();
// 		 }
// 	 }
//   });
//   //2.返回获得的数据
//   return checkedIds;
// }
//
//
// /*执行表单查询*/
// function doQueryObjects(){
// 	//1.修改当前页的值为1
// 	$("#pageId").data("pageCurrent",1);
// 	//2.执行查询动作(重用doGetObjects方法)
// 	doGetObjects();
// }
// /*获得查询表单中的数据*/
// function getQueryFormData(){
// 	//根据id获得具体对象的值,然后封装到JSON对象
// 	var params={
// 		name:$("#searchNameId").val(),
// 		valid:$("#searchValidId").val()
// 	};
// 	console.log(JSON.stringify(params));
// 	//一定要记得返回
//     return params;
// }
// function doGetObjects(){
// 	//定义一个url(对应服务端控制器中的一个方法)
// 	var url="project/doFindObjects.do"
// 	//获取当前页的页码值,假如没有值,默认值设置为1
// 	var pageCurrent=$("#pageId").data("pageCurrent");
// 	if(!pageCurrent){
// 		pageCurrent=1;
// 	}
// 	//定义一个params对象
// 	var params=getQueryFormData();
// 	//动态的向params对象中添加key/value
// 	params.pageCurrent=pageCurrent;
// 	console.log(JSON.stringify(params))
// 	//底层发起ajax异步请求($.ajax({....}))
// 	//post方法是一个增强版的ajax方法
//     $.ajaxSettings.async = true;
//     $.post(url,params,function(result){//result为一个json对象(JsonResult)
//     	//console.log("result="+result);
//     	//将json对象转换为json字符串输出
//     	//console.log(JSON.stringify(result));
//     	//将json对象中的数据,填充到table的tbody元素中
//     	if(result.state==1){//成功
//     	//alert(result.message);//假如有需要
//     	//显示记录信息
//     	setTableBodyRows(result.data.list);//data属性的值对应一个json对象
//     	//设置及显示分页信息
//     	//console.log(JSON.stringify(result.data.pageObject))
//     	setPagination(result.data.pageObject);
//     	}else{//失败了(获取数据时出现异常了)
//     	alert(result.message);
//     	}
//     });
//
// }
// //定义函数将json对象中的数据取出来填充到表格中
// function setTableBodyRows(result){//result 为一个json对象
// 	//获得tbody对象(根据id获得)
// 	//在使用$函数获得某个id对应的对象一定要添加#
// 	var tBody=$("#tbodyId");
// 	//清空body中内容(假如方法不记得了怎么办)
// 	tBody.empty();//doc.tedu.cn
// 	//迭代json对象
// 	for(var i in result){//循环一次取一行[{"name":"AAA",...},{},{}]
// 	//构建一个tr对象
// 	var tr=$("<tr></tr>");
// 	//在tr对象上绑定一个值
// 	tr.data("id",result[i].id);//key/value
// 	//tr对象中追加td字符串对象
// 	var firstTd='<td><input type="checkbox" name="checkedItem" value="[id]"></td>';
// 	//将firstTd字符串中的[id]替换为一个具体指
// 	firstTd=firstTd.replace("[id]",result[i].id);
// 	tr.append(firstTd);
// 	tr.append("<td>"+result[i].code+"</td>");
// 	tr.append("<td>"+result[i].name+"</td>");
// 	tr.append("<td>"+result[i].beginDate+"</td>");
// 	tr.append("<td>"+result[i].endDate+"</td>");
// 	tr.append("<td>"+(result[i].valid?"启用":"禁用")+"</td>");
// 	tr.append("<td><button type='button' class='btn btn-default btn-update'>修改</button></td>");
// 	tBody.append(tr);//将每一行记录再追加到tbody中
// 	}
//
// }

