package com.egova.api.leftMenu.web;

import com.egova.api.base.ResultInfo;
import com.egova.api.leftMenu.service.LeftMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by taoran on 2018/11/19
 */
@RestController
public class LeftMenuController {

    @Autowired
    LeftMenuService leftMenuService;

    /**
     * 获取导航菜单栏
     * @return
     */
    @GetMapping(value = "/getMenuList")
    public ResultInfo findAllMenu(){
        return leftMenuService.findAllMenu();
    }

    @PostMapping(value = "/updateMenuName")
    public ResultInfo updateMenuName(@RequestParam Long menuId,@RequestParam String menuName){
        return leftMenuService.updateMenuName(menuId,menuName);
    }
}
