package com.egova.api.apiInfo.dao;

import com.egova.api.apiInfo.pojo.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taoran on 2018/11/23
 */
public interface ApiInfoDAO  extends JpaRepository<ApiInfo,Long> {
}
