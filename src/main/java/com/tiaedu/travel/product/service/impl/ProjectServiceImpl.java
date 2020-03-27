package com.tiaedu.travel.product.service.impl;

import com.tiaedu.travel.product.dao.ProjectDao;
import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> findProjects(){
        List<Project> list = projectDao.findProjects();
        return list;
    }

}
