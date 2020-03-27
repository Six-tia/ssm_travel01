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

    /**返回项目列表页面*/
    @RequestMapping("listUI")
    public String listUI(){
        //此地址需要和@RequestMapping中的内容合起来看，即“/project/product/project_list”
        //若上面变为“/Project”，那么返回值变为"/product/project_list"
        return "product/project_list";
    }
    @RequestMapping("doGetObjects")
    //@ResponseBody会将返回对象交给Spring转换为json格式数据
    //返回json而非jsp页面是因为很多应用的打开形式可能不只是浏览器，还可能是手机App，软件等等
    @ResponseBody
    public List<Project> doGetObjects(){
        return projectService.findProjects();
    }
    /**
     * json:
     * [
     *  {"id":1,"name":"travel around",...}
     *  {"id":2,"name":"travel all the world",...}
     * ]
     */


}
