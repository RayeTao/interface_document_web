package com.egova.api.leftMenu.service;

import com.egova.api.base.Constant;
import com.egova.api.base.ResultInfo;
import com.egova.api.leftMenu.dao.LeftMenuDAO;
import com.egova.api.leftMenu.pojo.LeftMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created by taoran on 2018/11/19
 */
@Service
public class LeftMenuService {
    private static final Logger logger = LoggerFactory.getLogger(LeftMenuService.class);

    @Autowired
    LeftMenuDAO leftMenuDAO;

    public ResultInfo findAllMenu(){
        ResultInfo resultInfo = new ResultInfo();
        try {
            List<LeftMenu> list = leftMenuDAO.findAllByShowFlagOrderByDisplayOrderAsc(1);
            if(list != null && list.size()>0){
                resultInfo.setCode(Constant.SUCCESS_CODE);
                resultInfo.setSuccess(true);
                resultInfo.setMessage(Constant.SUCCESS_MESSAGE);
                resultInfo.setData(list);
            }else{
                resultInfo.setCode(Constant.FAIL_CODE);
                resultInfo.setSuccess(false);
                resultInfo.setMessage(Constant.FAIL_MESSAGE);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return resultInfo;
    }
    @Transactional
    public ResultInfo updateMenuName(Long menuId,String menuName){
        ResultInfo resultInfo = new ResultInfo();
        try {
            int result  =  leftMenuDAO.updateMenuNameByMenuId(menuName,menuId);
            if(result != 0){
                resultInfo.setSuccess(true);
                resultInfo.setCode(Constant.SUCCESS_CODE);
                resultInfo.setMessage(Constant.SUCCESS_MESSAGE);
            }else {
                resultInfo.setSuccess(false);
                resultInfo.setCode(Constant.FAIL_CODE);
                resultInfo.setMessage(Constant.FAIL_MESSAGE);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return resultInfo;
    }
}
