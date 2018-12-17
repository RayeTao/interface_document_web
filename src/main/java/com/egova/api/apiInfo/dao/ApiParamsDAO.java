package com.egova.api.apiInfo.dao;

import com.egova.api.apiInfo.pojo.ApiParams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by taoran on 2018/11/23
 */
public interface ApiParamsDAO extends JpaRepository<ApiParams,Long> {

    List<ApiParams> findByApiIdAndParamsType(Long apiId, int paramsType);

    void deleteByApiId(Long apiId);
}
