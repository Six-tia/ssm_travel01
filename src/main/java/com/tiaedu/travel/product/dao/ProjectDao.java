package com.tiaedu.travel.product.dao;

import com.tiaedu.travel.product.entity.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**项目模块持久层对象*/

/**接口会由Spring底层自动创建实现类对象*/
public interface ProjectDao {
    /**查询所有项目信息*/
    List<Project> findProjects();

    /**
    *使用注解@Param可以注解：
     * 1.单一属性
     * 2.JavaBean对象
     * 使用@Param注解时，sql语句使用#{},${}两者均可（否则只能用#{}）
     * 不使用@Param注解时，参数只能有一个，并且是Javabean。在SQL语句里可以
     * 引用JavaBean的属性，而且只能引用JavaBean的属性。
    */
    /**查询分页项目信息
     *  @Param ("startIndex") 分页查询的起始位置
     *  @Param ("pageSize") 每页的最大显示数据
     *  @return 当前页数据
     * */
    List<Project> findPageProjects(
            @Param("startIndex") int offset,
            @Param("pageSize") int pageSize
    );

    /**查询分页信息
     * 返回项目记录条数
     * */
    int getRowCount();

}
