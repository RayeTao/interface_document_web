package com.egova.api.apiInfo.dao;

import com.egova.api.apiInfo.pojo.ApiParams;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taoran on 2018/11/23
 */
public interface ApiParamsDAO extends JpaRepository<ApiParams,Long> {
}
