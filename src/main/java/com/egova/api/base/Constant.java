package com.egova.api.base;

/**
 * Created by taoran on 2018/11/19
 */
public class Constant {

    /**标准的成功返回*/
    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_MESSAGE = "成功";

    /**标准的失败返回*/
    public static final int FAIL_CODE = -1;
    public static final String FAIL_MESSAGE = "失败";

    /**登录成功的返回*/
    public static final String LOGIN_SUCCESS_MESSAGE = "登录成功";
    /**登录失败的返回*/
    public static final String LOGIN_NAME_FAIL_MESSAGE = "用户名不存在";
    public static final String LOGIN_PASSWORD_FAIL_MESSAGE = "密码错误";

    /**注册成功的返回*/
    public static final String REGISTER_SUCCESS_MESSAGE = "注册成功";
    /**注册失败的返回*/
    public static final String REGISTER_NAME_FAIL_MESSAGE = "用户名已存在";
    public static final String REGISTER_FAIL_MESSAGE = "注册失败";

}
