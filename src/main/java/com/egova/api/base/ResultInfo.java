package com.egova.api.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by taoran on 2018/11/19
 */
@Data
@NoArgsConstructor
public class ResultInfo {
    private int code;
    private String message;
    private boolean success;
    private Object data;

    public ResultInfo(boolean success){
        this.success = success;
    }
}
