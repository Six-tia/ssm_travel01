package com.tiaedu.travel.product.controller;

import com.tiaedu.travel.common.web.JsonResult;
import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import com.tiaedu.travel.common.exception.*;

/**
 * 控制层处理各种异常的统一方法：
 * 1.实现一个处理类，并且加上@ControllerAdvice注解
 * 2.实现一个控制层的父类(baseController)，父类里面处理了各种异常，让各个控制层继承父类
 */
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
    public JsonResult doGetPageObjects(String name, Integer valid, Integer pageCurrent){
        Map<String, Object> map = projectService.findPageProjects(name, valid, pageCurrent);
        //return map;
        return new JsonResult(map);
        /**
         * map内容：
         * {
         *      "list":[{id:1, name:"travel around", ...},{...}]
         *      "pageObject":{pageCount:2, pageCurrent:1,...}
         * }
         *
         * {
         *     state:1,
         *     message:"ok"
         *     data:{
         *         [map内容]
         *     }
         * }
         * */
    }


    /**
     * Controller中和操作数据相关的方法，建议加上do前缀
     * @ResponseBody 会将返回对象交给Spring转换为json字符串
     * 传到客户端后又会转化为json对象
     * 若不加@ResponseBody，则会默认转为model and view(页面)
     * Spring本身无法转换json，因此整合jackson的api
     * 返回json而非jsp页面是因为很多应用的打开形式可能不只是浏览器，还可能是手机App，软件等等
     * 浏览器测试url：http://localhost/ssm_travel01_war_exploded/project/doGetProjects.do
     * 测试结果发现无css样式，因为此jsp是index.jsp的一部分，css样式属于index.jsp
     * */
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
     * {
     *     state:1,
     *     message:"setting valid/invalid"
     *     data:{}
     * }
     * @param valid
     * @param ids
     * @return
     */
    //虽然函数类型为void，但是@ResponseBody必须要加
    //因为控制层为Model and View，view对应了具体的页面，
    //若不加此注解，则会出现404找不到页面的错误
    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer valid, String ids){
        projectService.validById(valid, ids);
        return new JsonResult(valid==1?"setting valid":"setting invalid");
    }

    /**
     * Spring获得参数数据后会对数据解析然后调用
     * peoject的setter方法将数据存储到project对象
     * @param entity
     * @return
     */
    @RequestMapping("doSaveProject")
    @ResponseBody
    public JsonResult doSaveProject(Project entity){
        projectService.saveProject(entity);
        return new JsonResult("insert ok");
    }

    /**
     * 返回项目编辑页面
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(){
        return "product/project_edit";
    }

}
