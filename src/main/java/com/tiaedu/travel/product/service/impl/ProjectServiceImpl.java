package com.tiaedu.travel.product.service.impl;

import com.tiaedu.travel.common.exception.ServiceException;
import com.tiaedu.travel.common.web.PageObject;
import com.tiaedu.travel.product.dao.ProjectDao;
import com.tiaedu.travel.product.entity.Project;
import com.tiaedu.travel.product.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Map<String, Object> findPageProjects(String name,
                                                Integer valid,
                                                int pageCurrent){
        int pageSize = 2;  //一页显示3条
        int startIndex = (pageCurrent - 1) * pageSize;
        //获取当前页数据
        List<Project> list = projectDao.findPageProjects(
                                name, valid, startIndex, pageSize);
        //获取总记录数并封装分页信息
        int rowCount = projectDao.getRowCount(name, valid);
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

    @Override
    public void validById(Integer valid, String ids){
        //1.业务验证
        if(valid != 0 && valid != 1)
            //common包中自定义的exception，每一层应该对应一层
            throw new ServiceException("valid value is invalid!");
        //if(ids == null || ids == "")
        if(StringUtils.isEmpty(ids))
            throw new ServiceException("ids cannot be null");
        //2.对参数数据进行处理
        String[] idArray = ids.split(",");
        //3.执行业务更新操作
        int row = projectDao.validById(valid, idArray);
        //4.对结果进行验证
        if(row == 0)
            throw new ServiceException("wrong change valid value");

    }

}
