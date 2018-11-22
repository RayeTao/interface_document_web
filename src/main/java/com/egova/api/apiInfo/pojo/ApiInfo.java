package com.egova.api.apiInfo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by taoran on 2018/11/19
 */
@Entity
@Data
public class ApiInfo {

    @Id
    @GeneratedValue
    private Long apiId;
    private Long menuId;
    private String apiUrl;
    private String apiName;
    private String methodType;
    @Column(length = 3000)
    private String params;
    @Column(length = 3000)
    private String result;
    private Date createTime;
    private Date updateTime;
    @Column(columnDefinition = "INT default 0",nullable = false)
    private int deleteFlag = 0;



}
