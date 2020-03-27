package com.tiaedu.travel.product.service;

import com.tiaedu.travel.product.entity.Project;

import java.util.List;

/**
 * 项目管理业务层接口：
 * 1）负责业务验证
 * 2）负责事务处理
 * 3）负责日志处理
 * 4）负责缓存处理
 * 5）负责权限处理
 */

public interface ProjectService {

    /**查询所有项目信息*/
    List<Project> findProjects();

}
