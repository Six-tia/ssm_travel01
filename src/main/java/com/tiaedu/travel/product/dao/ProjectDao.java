package com.tiaedu.travel.product.dao;

import com.tiaedu.travel.product.entity.Project;

import java.util.List;

/**项目模块持久层对象*/

/**接口会由Spring底层自动创建实现类对象*/
public interface ProjectDao {
    /**查询所有项目信息*/
    List<Project> findProjects();


}
