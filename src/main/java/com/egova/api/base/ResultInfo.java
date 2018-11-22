package com.egova.api.base;

import lombok.Data;

import java.util.List;

/**
 * Created by taoran on 2018/11/19
 */
@Data
public class ResultInfo {
    private int code;
    private String message;
    private boolean success;
    private Object data;

}
