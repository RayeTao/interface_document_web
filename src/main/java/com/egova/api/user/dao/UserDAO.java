package com.egova.api.user.dao;

import com.egova.api.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taoran on 2018/11/19
 */
public interface UserDAO  extends JpaRepository<User,Long> {
    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName,String password);
}
