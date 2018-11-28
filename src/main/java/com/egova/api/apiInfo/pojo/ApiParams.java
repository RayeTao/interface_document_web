package com.egova.api.apiInfo.pojo;

import lombok.Data;

import javax.persistence.*;


/**
 * Created by taoran on 2018/11/23
 * 接口参数
 */
@Entity
@Data
public class ApiParams  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paramsId;
    private Long apiId;
    private int paramsType;
    private String paramsKey;
    private String paramsValue;
    private String dataType;
    private String required;
    private String paramsDesc;
    @Column(columnDefinition = "INT default 0",nullable = false)
    private int deleteFlag = 0;

}
