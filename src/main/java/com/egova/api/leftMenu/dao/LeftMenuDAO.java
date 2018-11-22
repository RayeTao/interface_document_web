package com.egova.api.leftMenu.dao;

import com.egova.api.leftMenu.pojo.LeftMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by taoran on 2018/11/19
 */
public interface LeftMenuDAO extends JpaRepository<LeftMenu,Long> {
     List findAllByShowFlagOrderByDisplayOrderAsc(int showFlag);

     @Modifying
     @Query("update LeftMenu m set m.menuName=?1 where m.menuId=?2")
     int updateMenuNameByMenuId(String menuName,Long menuId);
}
