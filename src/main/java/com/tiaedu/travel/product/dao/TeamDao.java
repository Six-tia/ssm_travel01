package com.tiaedu.travel.product.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeamDao {

    //在team的业务层发生跨表查询，可以进行
    // 1.多次查询，这样可以避免mapper上的表关联
    // 2.表关联

    /**
     * 实现团目信息的分页查询
     *
     * @param name       项目名称
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> findPageTeams(
            @Param("name") String name,
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize

    );

    /**查询分页信息
     * @Param (" name ") 查询时用户输入的团名
     * @Return 返回项目记录条数
     * 注：此处需要用到mybatis的动态sql，因为参数可能为空值，即无查询
     * */
    int getRowCount(
            @Param("name") String name
    );

}
