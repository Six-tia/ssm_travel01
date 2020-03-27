package product.service;

import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestProjectService {

    ClassPathXmlApplicationContext cpx;
//    @Before
//    public void init(){
//        cpx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
//    }

    @Test
    public void testFindProjects(){

        /**
         * 1.获得ProjectService对象
         * 2.执行ProjectService对象的findProjects方法
         * 3.验证结果是否正确
         * 4.输出执行结果
         */
        System.out.println("----------1");

        cpx = new ClassPathXmlApplicationContext("spring-mvc.xml","spring-mybatis.xml");
        System.out.println("----------1");
        //name中应为对应接口的实现类，因为@Service是加在实现类上的
        ProjectService projectService = cpx.getBean("projectServiceImpl", ProjectService.class);
        System.out.println("----------2");
        List<Project> projects = projectService.findProjects();
        Assert.assertNotEquals(0, projects.size());
        System.out.println(projects);
    }

//    @After
//    public void destroy(){
//        cpx.close();
//    }

}
