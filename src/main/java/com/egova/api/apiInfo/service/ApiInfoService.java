package com.egova.api.apiInfo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egova.api.apiInfo.dao.ApiInfoDAO;
import com.egova.api.apiInfo.dao.ApiParamsDAO;
import com.egova.api.apiInfo.pojo.ApiInfo;
import com.egova.api.apiInfo.pojo.ApiParams;
import com.egova.api.base.Constant;
import com.egova.api.base.ResultInfo;
import com.egova.api.utils.HttpUtils;
import com.egova.api.utils.JsonFormatUtil;

import com.egova.api.utils.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by taoran on 2018/11/23
 */
@Service
public class ApiInfoService {
    private static final Logger logger = Logger.getLogger(ApiInfoService.class);

    @Autowired
    ApiInfoDAO apiInfoDAO;
    @Autowired
    ApiParamsDAO apiParamsDAO;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    RedisUtil redisUtil;

    public ResultInfo saveApiInfo(String apiInfo) {
        ResultInfo resultInfo = new ResultInfo(true);
        try {
            JSONObject json = JSONObject.parseObject(apiInfo);
            String data = json.getString("data");
            JSONObject jsonData = JSONObject.parseObject(data);
            String apiInfoForm = jsonData.getString("apiInfoForm");
            JSONObject jsonApiInfo = JSONObject.parseObject(apiInfoForm);
            String menuId = jsonApiInfo.getString("menuId");
            String userName = jsonApiInfo.getString("userName");
            String apiAddress = jsonApiInfo.getString("apiUrl");
            String apiName = jsonApiInfo.getString("apiName");
            String methodType = jsonApiInfo.getString("methodType");
            String requestParamsForm = jsonApiInfo.getString("requestList");
            String responseParamsForm = jsonApiInfo.getString("responseList");
            String requestParamsBody = jsonApiInfo.getString("requestParams");
            String responseParamsBody = jsonApiInfo.getString("responseParams");

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
            if (infoResult != null) {
                JSONArray requestParamsArray = JSONArray.parseArray(requestParamsForm);
                JSONArray responseParamsArray = JSONArray.parseArray(responseParamsForm);
                if (requestParamsArray != null && requestParamsArray.size() > 0) {
                    for (int i = 0; i < requestParamsArray.size(); i++) {
                        JSONObject jsonParams = JSONObject.parseObject(requestParamsArray.get(i).toString());
                        ApiParams apiParams = new ApiParams();
                        apiParams.setParamsType(0);
                        apiParams.setApiId(infoResult.getApiId());
                        apiParams.setParamsKey(jsonParams.getString("paramsKey"));
                        apiParams.setParamsValue(jsonParams.getString("paramsValue"));
                        apiParams.setDataType(jsonParams.getString("dataType"));
                        apiParams.setParamsDesc(jsonParams.getString("paramsDesc"));
                        apiParams.setRequired(jsonParams.getString("required"));
                        apiParamsDAO.save(apiParams);
                    }
                }
                if (responseParamsArray != null && responseParamsArray.size() > 0) {
                    for (int i = 0; i < responseParamsArray.size(); i++) {
                        JSONObject jsonParams = JSONObject.parseObject(responseParamsArray.get(i).toString());
                        ApiParams apiParams = new ApiParams();
                        apiParams.setParamsType(1);
                        apiParams.setApiId(infoResult.getApiId());
                        apiParams.setParamsKey(jsonParams.getString("paramsKey"));
                        apiParams.setParamsValue(jsonParams.getString("paramsValue"));
                        apiParams.setDataType(jsonParams.getString("dataType"));
                        apiParams.setParamsDesc(jsonParams.getString("paramsDesc"));
                        apiParams.setRequired(jsonParams.getString("required"));
                        apiParamsDAO.save(apiParams);
                    }
                }
            }
            resultInfo.setCode(Constant.SUCCESS_CODE);
            return resultInfo;
        } catch (NumberFormatException e) {
            resultInfo.setSuccess(false);
            resultInfo.setCode(Constant.FAIL_CODE);
            logger.error("添加接口信息失败:" + e.getMessage(), e);
        }
        return resultInfo;
    }

    public ResultInfo getApiList(Long menuId) {
        ResultInfo resultInfo = new ResultInfo(true);
        try {
            List<ApiInfo> list = apiInfoDAO.findByMenuId(menuId);
            if (list != null && list.size() > 0) {
                for (ApiInfo apiInfo : list) {
                    List<ApiParams> requestList = apiParamsDAO.findByApiIdAndParamsType(apiInfo.getApiId(), 0);
                    if (requestList != null && requestList.size() > 0) {
                        apiInfo.setRequestList(requestList);
                    }
                    List<ApiParams> responseList = apiParamsDAO.findByApiIdAndParamsType(apiInfo.getApiId(), 1);
                    if (responseList != null && responseList.size() > 0) {
                        apiInfo.setResponseList(responseList);
                    }
                }
            }
            resultInfo.setCode(Constant.SUCCESS_CODE);
            resultInfo.setData(list);
        } catch (Exception e) {
            resultInfo.setCode(Constant.FAIL_CODE);
            resultInfo.setSuccess(false);
            resultInfo.setMessage("获取数据失败");
            logger.error(e.getMessage(), e);
        }
        return resultInfo;
    }

    public ResultInfo getApiInfoDetail(Long apiId) {
        ResultInfo resultInfo = new ResultInfo(true);
        try {
            ApiInfo apiInfo = apiInfoDAO.findByApiId(apiId);
            List<ApiParams> requestList = apiParamsDAO.findByApiIdAndParamsType(apiId, 0);
            if (requestList != null && requestList.size() > 0) {
                apiInfo.setRequestList(requestList);
                Map<String, Object> requestMap = new HashMap<>();
                for (ApiParams apiParams : requestList) {
                    requestMap.put(apiParams.getParamsKey(), apiParams.getParamsValue());
                }
                JSONObject jsonObject = new JSONObject(requestMap);
                String requestJson = jsonObject.toString();
                if (apiInfo.getRequestParams() == null || apiInfo.getRequestParams().equals("")) {
                    apiInfo.setRequestParams(JsonFormatUtil.formatJson(requestJson));
                } else {
                    apiInfo.setRequestParams(JsonFormatUtil.formatJson(apiInfo.getRequestParams()));
                }

            }
            List<ApiParams> responseList = apiParamsDAO.findByApiIdAndParamsType(apiId, 1);
            if (responseList != null && responseList.size() > 0) {
                apiInfo.setResponseList(responseList);
                Map<String, Object> responseMap = new HashMap<>();
                for (ApiParams apiParams : responseList) {
                    responseMap.put(apiParams.getParamsKey(), apiParams.getParamsValue());
                }
                JSONObject jsonObject = new JSONObject(responseMap);
                String responseJson = jsonObject.toString();
                if (apiInfo.getResponseParams() == null || apiInfo.getResponseParams().equals("")) {
                    apiInfo.setResponseParams(JsonFormatUtil.formatJson(responseJson));
                } else {
                    apiInfo.setResponseParams(JsonFormatUtil.formatJson(apiInfo.getResponseParams()));
                }
            }
            resultInfo.setCode(Constant.SUCCESS_CODE);
            resultInfo.setData(apiInfo);
            return resultInfo;
        } catch (Exception e) {
            resultInfo.setSuccess(false);
            resultInfo.setCode(Constant.FAIL_CODE);
            resultInfo.setMessage(Constant.FAIL_MESSAGE);
            logger.error("获取接口信息详情失败：" + e.getMessage(), e);
        }
        return resultInfo;
    }

    public ResultInfo editApiInfo(String apiInfo) {
        ResultInfo resultInfo = new ResultInfo(true);
        return resultInfo;
    }

    public ResultInfo deleteApiInfo(Long apiId) {
        ResultInfo resultInfo = new ResultInfo(true);
        try {
            apiInfoDAO.deleteById(apiId);
            apiParamsDAO.deleteByApiId(apiId);
            resultInfo.setCode(Constant.SUCCESS_CODE);
            resultInfo.setMessage(Constant.SUCCESS_MESSAGE);
            return resultInfo;
        } catch (Exception e) {
            resultInfo.setSuccess(false);
            resultInfo.setCode(Constant.FAIL_CODE);
            logger.error("删除接口信息失败:" + e.getMessage(), e);
        }
        return resultInfo;
    }

    public ResultInfo getParamsByUrl(String apiInfo) {
        ResultInfo resultInfo = new ResultInfo(true);
        JSONObject json = JSONObject.parseObject(apiInfo);
        String data = json.getString("data");
        JSONObject jsonData = JSONObject.parseObject(data);
        String apiInfoForm = jsonData.getString("apiInfoForm");
        JSONObject jsonApiInfo = JSONObject.parseObject(apiInfoForm);
        String serverAddress = jsonApiInfo.getString("serverAddress");
        String apiUrl = jsonApiInfo.getString("apiUrl");
        String methodType = jsonApiInfo.getString("methodType");
        String requestParamsForm = jsonApiInfo.getString("requestList");
        String requestParamsBody = jsonApiInfo.getString("requestParams");
        String requestParams = "";
        String result = "";
        if (methodType != null && (methodType.equals("GET") || methodType.equals("ALL"))) {
            result = HttpUtils.sendGet(serverAddress + apiUrl, requestParamsForm);
        }

        return resultInfo;
    }
}
