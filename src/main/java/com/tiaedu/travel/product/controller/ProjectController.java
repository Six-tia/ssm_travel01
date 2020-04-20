package com.tiaedu.travel.product.controller;

import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

@Controller
//将请求URL映射到整个类上或者一个方法上
@RequestMapping("/project/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**返回项目列表页面，此处可以写“listUI.do”也可以不写.do,因为在web.xml中配置了自动加.do*/
    //浏览器测试url：http://localhost/ssm_travel01_war_exploded/project/listUI.do
    //此地址需要和@RequestMapping中的内容合起来看，即“/project/listUI.do”
    //若上面变为“/Project”，那么变为"/listUI"
    @RequestMapping("listUI")
    public String listUI(){
        //此处url没有写/WEB-INF/pages/...，因为在spring-mvc.xml中配置了前缀和后缀
        return "product/project_list";
    }

    @RequestMapping("doGetPageObjects")
    @ResponseBody
    public Map<String, Object> doGetPageObjects(String name, Integer valid, Integer pageCurrent){
        Map<String, Object> map = projectService.findPageProjects(name, valid, pageCurrent);
        return map;
        /**
         * {
         *      "list":[{id:1, name:"travel around", ...},{...}]
         *      "pageObject":{pageCount:2, pageCurrent:1,...}
         * }
         * */
    }


    //Controller中和操作数据相关的方法，建议加上do前缀
    //@ResponseBody会将返回对象交给Spring转换为json格式数据
    //Spring本身无法转换json，因此整合jackson的api
    //返回json而非jsp页面是因为很多应用的打开形式可能不只是浏览器，还可能是手机App，软件等等
    //浏览器测试url：http://localhost/ssm_travel01_war_exploded/project/doGetProjects.do
    //测试结果发现无css样式，因为此jsp是index.jsp的一部分，css样式属于index.jsp
    @RequestMapping("doGetProjects")
    @ResponseBody
    public List<Project> doGetProjects(){
        return projectService.findProjects();
    }
    /**
     * Spring发现控制层方法上有ResponseBody注解，会启用第三方API（例如Jackson，gson）
     * 将返回的对象转换为JSON字符串
     * json格式:
     * [
     *  {"id":1,"name":"travel around",...}
     *  {"id":2,"name":"travel all the world",...}
     * ]
     */

    /**
     * 禁用和启用项目
     * @param valid
     * @param ids
     * @return
     */
    @RequestMapping("doValidById")
    @ResponseBody
    public void doValidById(Integer valid, String ids){
        projectService.validById(valid, ids);
        //return "{}";
    }

}
