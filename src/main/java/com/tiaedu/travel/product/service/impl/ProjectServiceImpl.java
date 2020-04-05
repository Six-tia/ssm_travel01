package com.tiaedu.travel.product.service.impl;

import com.tiaedu.travel.common.web.PageObject;
import com.tiaedu.travel.product.dao.ProjectDao;
import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> findProjects(){
        List<Project> list = projectDao.findProjects();
        return list;
    }

    @Override
    public Map<String, Object> findPageProjects(int pageCurrent){
        int pageSize = 2;  //一页显示3条
        int startIndex = (pageCurrent - 1) * pageSize;
        //获取当前页数据
        List<Project> list = projectDao.findPageProjects(startIndex, pageSize);
        //获取总记录数并封装分页信息
        int rowCount = projectDao.getRowCount();
        PageObject pageObject = new PageObject();
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setStartIndex(startIndex);
        //将当前页数据及分页信息封装到map
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("pageObject", pageObject);
        return map;
    }

}
