package product.service;

import com.tiaedu.travel.common.web.PageObject;
import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class TestProjectService {

    ClassPathXmlApplicationContext cpx;
    @Before
    public void init(){
        cpx = new ClassPathXmlApplicationContext("spring-mvc.xml","spring-mybatis.xml");
    }

    @Test
    public void testFindProjects(){

        /**
         * 1.获得ProjectService对象
         * 2.执行ProjectService对象的findProjects方法
         * 3.验证结果是否正确
         * 4.输出执行结果
         */
        System.out.println("----------1");

        //cpx = new ClassPathXmlApplicationContext("spring-mvc.xml","spring-mybatis.xml");
        System.out.println("----------1");
        //name中应为对应接口的实现类，因为@Service是加在实现类上的
        ProjectService projectService = cpx.getBean("projectServiceImpl", ProjectService.class);
        System.out.println("----------2");
        List<Project> projects = projectService.findProjects();
        System.out.println("----------3");
        //Assert.assertNotEquals(0, projects.size());
        System.out.println(projects);
    }

    @Test
    public void testFindPageObjects(){
        ProjectService projectService = cpx.getBean("projectServiceImpl",
                ProjectService.class);
        Map<String, Object> map = projectService.findPageProjects(2);
        List<Project> list = (List<Project>) map.get("list");
        PageObject pageObject = (PageObject) map.get("pageObject");
        System.out.println(list.size()); //1
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, pageObject.getPageCount());
        System.out.println(pageObject.getPageCount()); //2
    }

    @After
    public void destroy(){
        cpx.close();
    }

}
