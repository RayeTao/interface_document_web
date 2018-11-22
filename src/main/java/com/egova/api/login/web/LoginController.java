package com.egova.api.login.web;

import com.egova.api.base.ResultInfo;
import com.egova.api.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by taoran on 2018/11/19
 */
@RestController
public class LoginController {

  @Autowired
    LoginService loginService;

    /**
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @return
     */
  @GetMapping(value = "/login")
  public ResultInfo login(@RequestParam String userName,@RequestParam String password){
      return loginService.login(userName,password);
  }

    /**
     * 注册
     * @param userName 用户名
     * @param password 密码
     * @return
     */
  @PostMapping(value = "/register")
  public ResultInfo register(@RequestParam String userName,@RequestParam String password){
      return loginService.register(userName,password);
    }
}
