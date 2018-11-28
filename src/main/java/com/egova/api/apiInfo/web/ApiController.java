package com.egova.api.apiInfo.web;

import com.egova.api.apiInfo.service.ApiInfoService;
import com.egova.api.base.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by taoran on 2018/11/23
 * 接口信息Controller
 */
@RestController
public class ApiController {

    @Autowired
    ApiInfoService apiInfoService;

    @PostMapping(value = "saveApiInfo")
    public ResultInfo saveApiInfo(@RequestBody String apiInfo){
        return apiInfoService.saveApiInfo(apiInfo);
    }
}
