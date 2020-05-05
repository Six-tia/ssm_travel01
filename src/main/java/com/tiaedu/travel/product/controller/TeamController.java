package com.tiaedu.travel.product.controller;

import com.tiaedu.travel.common.web.JsonResult;
import com.tiaedu.travel.product.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/team/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping("listUI")
    public String listUI(){
        return "product/team_list";
    }

    @RequestMapping("doFindPageTeams")
    public JsonResult doFindPageTeams(
            String name,
            Integer pageCurrent
    ){
        return new JsonResult(teamService.findPageTeams(name, pageCurrent));
    }

}
