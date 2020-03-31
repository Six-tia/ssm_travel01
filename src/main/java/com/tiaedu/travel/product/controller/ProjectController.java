package com.tiaedu.travel.product.controller;

import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
//将请求URL映射到整个类上或者一个方法上
@RequestMapping("/project/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**返回项目列表页面，此处可以写“listUI.do”也可以不写.do,因为在web.xml中配置了自动加.do*/
    //浏览器测试url：http://localhost/ssm_travel01_war_exploded/project/listUI.do
    @RequestMapping("listUI")
    public String listUI(){
        //此地址需要和@RequestMapping中的内容合起来看，即“/project/product/project_list”
        //若上面变为“/Project”，那么返回值变为"/product/project_list"
        //此处url没有写/WEB-INF/pages/...，因为在spring-mvc.xml中配置了前缀和后缀
        return "product/project_list";
    }
    @RequestMapping("doGetProjects")
    //Controller中和操作数据相关的方法，建议加上do前缀
    //@ResponseBody会将返回对象交给Spring转换为json格式数据
    //返回json而非jsp页面是因为很多应用的打开形式可能不只是浏览器，还可能是手机App，软件等等
    //Spring本身无法转换json，因此整合jackson的api
    //浏览器测试url：http://localhost/ssm_travel01_war_exploded/project/doGetProjects.do
    //测试结果发现无css样式，因为此jsp是index.jsp的一部分，css样式属于index.jsp
    @ResponseBody
    public List<Project> doGetProjects(){
        return projectService.findProjects();
    }
    /**
     * json格式:
     * [
     *  {"id":1,"name":"travel around",...}
     *  {"id":2,"name":"travel all the world",...}
     * ]
     */


}
