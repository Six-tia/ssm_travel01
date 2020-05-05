package product.service;

import com.tiaedu.travel.common.web.PageObject;
import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import com.tiaedu.travel.product.service.TeamService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestTeamService {
    ClassPathXmlApplicationContext cpx;
    @Before
    public void init(){
        cpx = new ClassPathXmlApplicationContext("spring-mvc.xml","spring-mybatis.xml");
    }

    @Test
    public void testFindPageTeams(){
        TeamService teamService = cpx.getBean("teamServiceImpl",
                TeamService.class);
        Map<String, Object> map = teamService.findPageTeams("环球",1);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
        PageObject pageObject = (PageObject) map.get("pageObject");
        System.out.println(list.size()); //2
        System.out.println(list.get(0)); //1
        System.out.println(list.get(1)); //1
        System.out.println(pageObject.getPageCount()); //1
    }

    @Test
    public void testValidById(){
        ProjectService projectService = cpx.getBean("projectServiceImpl",
                ProjectService.class);
        Integer valid = 1;
        String ids = "1,3,4";
        projectService.validById(valid, ids);
    }

    @Test
    public void testInsertProject(){
        ProjectService projectService = cpx.getBean("projectServiceImpl",
                ProjectService.class);
        Project entity = new Project();
        entity.setCode("20200427-CN-BJ");
        entity.setName("东欧游");
        entity.setBeginDate(new Date());
        entity.setEndDate(new Date());
        entity.setCreatedUser("tia");
        entity.setNote("......");
        entity.setValid(1);
        projectService.saveProject(entity);
    }

    @Test
    public void testUpdateProject(){
        ProjectService projectService = cpx.getBean("projectServiceImpl",
                ProjectService.class);
        Project entity = new Project();
        entity.setId(3);
        entity.setCode("20200427-CN-AU");
        entity.setName("火星游");
        entity.setBeginDate(new Date());
        entity.setEndDate(null);
        entity.setCreatedUser("tiaa");
        entity.setNote("......");
        entity.setValid(1);
        projectService.updateProject(entity);
    }

    @Test
    public void testFindProjectById(){
        ProjectService projectService = cpx.getBean("projectServiceImpl",
                ProjectService.class);
        Integer id = 4;
        Project project = projectService.findProjectById(id);
        System.out.println(project.getCode());
        System.out.println(project.getName());
    }

    @After
    public void destroy(){
        cpx.close();
    }
}
