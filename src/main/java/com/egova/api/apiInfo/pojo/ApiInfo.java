package com.egova.api.apiInfo.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by taoran on 2018/11/19
 * 接口信息
 */
@Entity
@Data
public class ApiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apiId;
    private Long menuId;
    private String apiUrl;
    private String apiName;
    private String methodType;
    @Column(length = 3000)
    private String requestParams;
    @Column(length = 3000)
    private String responseParams;
    private Date createTime;
    private Date updateTime;
    @Column(columnDefinition = "INT default 0",nullable = false)
    private int deleteFlag = 0;
    private String creatorName;

    @Transient
    private List requestList;
    @Transient
    private List responseList;
}
