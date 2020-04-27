package com.tiaedu.travel.product.service;

import com.tiaedu.travel.product.entity.Project;

import java.util.List;
import java.util.Map;

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

    /**查询分页项目信息
     * 1.获取当前页数据
     * 2.获取分页信息（总记录数，总页数，...）
     * 因为项目信息包含多种因此使用map类型
     * */
    Map<String, Object> findPageProjects(String name,
                                         Integer valid,
                                         int pageCurrent);

    /**
     *
     * @param valid 状态
     * @param id 由多个id构成的字符串，因为页面传来的不会自动变成数组
     */
    void validById(Integer valid, String id);

    /**
     *
     * @param entity 新增项目
     */
    void saveProject(Project entity);

    void updateProject(Project entity);

    Project findProjectById(Integer id);
}
