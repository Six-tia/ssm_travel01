package com.tiaedu.travel.product.service.impl;

import com.tiaedu.travel.common.web.PageObject;
import com.tiaedu.travel.product.dao.TeamDao;
import com.tiaedu.travel.product.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    /**
     * 1.获取当前页数据
     * 2.计算并封装分页信息
     * 3.封装当前页数据和分页信息
     * @param name
     * @param pageCurrent
     * @return
     */
    @Override
    public Map<String, Object> findPageTeams(String name, Integer pageCurrent) {
        int pageSize = 3;
        int startIndex = (pageCurrent-1)*pageSize;
        List<Map<String, Object>> list = teamDao.findPageTeams(name, startIndex, pageSize);
        PageObject pageObject = new PageObject();
        int rowCount = teamDao.getRowCount(name);
        pageObject.setRowCount(rowCount);
        pageObject.setStartIndex(startIndex);
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pageObject", pageObject);
        return map;
    }
}
