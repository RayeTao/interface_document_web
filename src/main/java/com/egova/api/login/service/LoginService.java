package com.egova.api.login.service;

import com.egova.api.base.Constant;
import com.egova.api.base.ResultInfo;
import com.egova.api.user.dao.UserDAO;
import com.egova.api.user.pojo.User;
import com.egova.api.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by taoran on 2018/11/19
 */
@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    UserDAO userDAO;

    public ResultInfo login(String userName,String password){
        ResultInfo resultInfo = new ResultInfo();
        User userByName = userDAO.findByUserName(userName);
        if(userByName != null){
            User userByPassword = userDAO.findByUserNameAndPassword(userName, MD5Util.MD5LowerCase(password));
            if(userByPassword !=null){
                resultInfo.setSuccess(true);
                resultInfo.setCode(Constant.SUCCESS_CODE);
                resultInfo.setMessage(Constant.LOGIN_SUCCESS_MESSAGE);
            }else{
                resultInfo.setSuccess(false);
                resultInfo.setCode(Constant.FAIL_CODE);
                resultInfo.setMessage(Constant.LOGIN_PASSWORD_FAIL_MESSAGE);
            }
        }else{
            resultInfo.setSuccess(false);
            resultInfo.setCode(Constant.FAIL_CODE);
            resultInfo.setMessage(Constant.LOGIN_NAME_FAIL_MESSAGE);
        }
        return  resultInfo;
    }


    public ResultInfo register(@RequestParam String userName,@RequestParam String password){
        ResultInfo resultInfo = new ResultInfo();
        //判断用户是否存在
        User userByName = userDAO.findByUserName(userName);
        if(userByName == null){
            User user = new User();
            user.setUserName(userName);
            user.setPassword(MD5Util.MD5LowerCase(password));
            user.setCreateTime(new Date());
            User result =  userDAO.save(user);
            if(result != null){
                resultInfo.setCode(Constant.SUCCESS_CODE);
                resultInfo.setSuccess(true);
                resultInfo.setMessage(Constant.REGISTER_SUCCESS_MESSAGE);
            }else{
                resultInfo.setCode(Constant.FAIL_CODE);
                resultInfo.setSuccess(false);
                resultInfo.setMessage(Constant.REGISTER_FAIL_MESSAGE);
            }
        }else{
            resultInfo.setCode(Constant.FAIL_CODE);
            resultInfo.setSuccess(false);
            resultInfo.setMessage(Constant.REGISTER_NAME_FAIL_MESSAGE);
        }

        return resultInfo;
    }
}
