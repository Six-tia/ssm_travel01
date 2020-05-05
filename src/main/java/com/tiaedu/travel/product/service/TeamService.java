package com.tiaedu.travel.product.service;

import java.util.Map;

public interface TeamService {
    /**
     *
     * @param name
     * @param pageCurrent
     * @return 包含当前页数据和分页信息
     */
    Map<String, Object> findPageTeams(
            String name, Integer pageCurrent);
}
