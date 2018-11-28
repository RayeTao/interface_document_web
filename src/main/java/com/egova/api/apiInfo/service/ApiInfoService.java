package com.egova.api.apiInfo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egova.api.apiInfo.dao.ApiInfoDAO;
import com.egova.api.apiInfo.dao.ApiParamsDAO;
import com.egova.api.apiInfo.pojo.ApiInfo;
import com.egova.api.apiInfo.pojo.ApiParams;
import com.egova.api.base.ResultInfo;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by taoran on 2018/11/23
 */
@Service
public class ApiInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ApiInfoService.class);

    @Autowired
    ApiInfoDAO apiInfoDAO;
    @Autowired
    ApiParamsDAO apiParamsDAO;

    public ResultInfo saveApiInfo(String apiInfo){
       ResultInfo resultInfo = new ResultInfo(true);
       JSONObject json = JSONObject.parseObject(apiInfo);
       String data = json.getString("data");
       JSONObject jsonData = JSONObject.parseObject(data);
       String apiInfoForm = jsonData.getString("apiInfoForm");
       String menuId = jsonData.getString("menuId");
       String userName = jsonData.getString("userName");
       JSONObject jsonApiInfo = JSONObject.parseObject(apiInfoForm);
        String apiAddress = jsonApiInfo.getString("apiAddress");
        String apiName = jsonApiInfo.getString("apiName");
        String methodType = jsonApiInfo.getString("methodType");
        String requestParamsForm = jsonApiInfo.getString("requestParamsForm");
        String responseParamsForm = jsonApiInfo.getString("responseParamsForm");
        String requestParamsBody = jsonApiInfo.getString("requestParamsBody");
        String responseParamsBody = jsonApiInfo.getString("responseParamsBody");

        ApiInfo info = new ApiInfo();
        info.setApiName(apiName);
        info.setApiUrl(apiAddress);
        info.setCreateTime(new Date());
        info.setMethodType(methodType);
        info.setRequestParams(requestParamsBody);
        info.setResponseParams(responseParamsBody);
        info.setUpdateTime(new Date());
        info.setCreatorName(userName);
        info.setMenuId(Long.valueOf(menuId));
        ApiInfo infoResult = apiInfoDAO.save(info);
        if(infoResult != null){
            JSONArray requestParamsArray = JSONArray.parseArray(requestParamsForm);
            JSONArray responseParamsArray = JSONArray.parseArray(responseParamsForm);
            if(requestParamsArray != null && requestParamsArray.size() >0){
                for (int i = 0; i <requestParamsArray.size() ; i++) {
                    JSONObject jsonParams = JSONObject.parseObject(requestParamsArray.get(0).toString());
                    ApiParams apiParams = new ApiParams();
                    apiParams.setParamsType(0);
                    apiParams.setApiId(infoResult.getApiId());
                    apiParams.setParamsKey(jsonParams.getString("requestParamsKey"));
                    apiParams.setParamsValue(jsonParams.getString("requestParamsValue"));
                    apiParams.setDataType(jsonParams.getString("requestParamsDataType"));
                    apiParams.setParamsDesc(jsonParams.getString("requestParamsDesc"));
                    apiParams.setRequired(jsonParams.getString("requestParamsRequired"));
                    apiParamsDAO.save(apiParams);
                }
            }
            if(responseParamsArray != null && responseParamsArray.size() >0){
                for (int i = 0; i <responseParamsArray.size() ; i++) {
                    JSONObject jsonParams = JSONObject.parseObject(responseParamsArray.get(0).toString());
                    ApiParams apiParams = new ApiParams();
                    apiParams.setParamsType(1);
                    apiParams.setApiId(infoResult.getApiId());
                    apiParams.setParamsKey(jsonParams.getString("responseParamsKey"));
                    apiParams.setParamsValue(jsonParams.getString("responseParamsValue"));
                    apiParams.setDataType(jsonParams.getString("responseParamsDataType"));
                    apiParams.setParamsDesc(jsonParams.getString("responseParamsDesc"));
                    apiParams.setRequired(jsonParams.getString("responseParamsRequired"));
                    apiParamsDAO.save(apiParams);
                }
            }

        }

       return resultInfo;
    }
}
