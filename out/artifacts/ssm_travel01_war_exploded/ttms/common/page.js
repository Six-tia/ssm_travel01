$(document).ready(function(){
	//JS里的元素只要包上$()就是jquery对象了
	//而jquery的对象只要加上[0]或者.get(0)，就是js元素了

	//在pageId对应的对象的相关元素上注册点击事件
	$("#pageId").on('click',
	'.pre,.next,.first,.last',jumpToPage);
});
//设置分页
function setPagination(pageObject){
	//jQuery html() 方法设置或返回被选元素的内容（innerHTML）。
	//当该方法用于返回内容时，则返回第一个匹配元素的内容。
	//当该方法用于设置内容时，则重写所有匹配元素的内容。
 //1.初始化总页数
 $(".pageCount").html("总页数("+pageObject.pageCount+")");
 //2.初始化当前页的页码
 $(".pageCurrent").html("当前页("+pageObject.pageCurrent+")");
 //3.在pageId对应的对象上绑定总页数
 //data函数用于以key/value的方式在对象上绑定数据
 $("#pageId").data("pageCount",pageObject.pageCount);
 //4.在pageId对象的对象上绑定当前页面值
 $("#pageId").data("pageCurrent",pageObject.pageCurrent);
}
//定义一个函数,通过此函数实现页面的跳转
function jumpToPage(){
	//以$赋值的变量都有data()函数（key-value）
	//获得点击对象上class属性对应的值,根据此值
	//判定具体点击的是哪个对象(例如上一页,下一页)
	// $(this)是jquery对象，this就是简单指当前元素。
	// jquery对象不能直接指定元素的属性（ele.style），
	// 需要get（index）或者直接（index）取得对象中元素才行
	debugger
	var clazz=$(this).attr("class");
	//获得pageId对象上绑定的pageCurrent对应的值
	var pageCurrent=$('#pageId').data("pageCurrent");
	//获得pageId对象上绑定的pageCount对应的值
	var pageCount=$('#pageId').data("pageCount")
	//根据class属性的值判断点击的是否是上一页
	if(clazz=='pre'&&pageCurrent>1){
		pageCurrent--;
	}
	//判断点击的是否是下一页
	if(clazz=="next"&&pageCurrent<pageCount){
		pageCurrent++;
	}
	//判断点击的对象是否是首页
	if(clazz=="first"){
		pageCurrent=1;
	}
	//判定点击的对象是否是尾页
	if(clazz=="last"){
		pageCurrent=pageCount;
	}
	//重写绑定pageCurrent的值,
	$('#pageId').data("pageCurrent",pageCurrent);
	//重新执行查询操作(根据pageCurrent的值)
	//此处调用的是project_list.jsp的方法，因为两个js文件都在同一个jsp文件中被
	//调用了，因此可以直接使用
	doGetObjects();
}









