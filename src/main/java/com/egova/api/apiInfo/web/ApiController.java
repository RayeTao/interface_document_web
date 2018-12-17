package com.egova.api.apiInfo.web;

import com.egova.api.apiInfo.pojo.ApiInfo;
import com.egova.api.apiInfo.service.ApiInfoService;
import com.egova.api.base.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by taoran on 2018/11/23
 * 接口信息Controller
 */
@RestController
public class ApiController {

    @Autowired
    ApiInfoService apiInfoService;

    /**
     * 保存接口信息
     * @param apiInfo
     * @return
     */
    @PostMapping(value = "/saveApiInfo")
    public ResultInfo saveApiInfo(@RequestBody String apiInfo){
        return apiInfoService.saveApiInfo(apiInfo);
    }

    /**
     * 获取接口信息列表
     * @return
     */
    @GetMapping(value = "/getApiList")
    public ResultInfo getApiList(@RequestParam Long menuId){
        return apiInfoService.getApiList(menuId);
    }

    /**
     * 获取接口信息详情
     * @param apiId
     * @return
     */
    @GetMapping(value = "/getApiInfoDetail")
    public ResultInfo getApiInfoDetail(@RequestParam Long apiId){
        return apiInfoService.getApiInfoDetail(apiId);
    }

    /**
     * 编辑接口信息
     * @param apiInfo
     * @return
     */
    @PostMapping(value = "/editApiInfo")
    public ResultInfo editApiInfo(@RequestBody String apiInfo){
        return apiInfoService.editApiInfo(apiInfo);
    }

    /**
     * 删除接口信息
     * @param apiId
     * @return
     */
    @GetMapping(value = "/deleteApiInfo")
    public ResultInfo deleteApiInfo(@RequestParam Long apiId){
        return apiInfoService.deleteApiInfo(apiId);
    }
}
