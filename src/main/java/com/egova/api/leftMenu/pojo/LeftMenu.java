package com.egova.api.leftMenu.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by taoran on 2018/11/19
 */
@Entity
@Data
public class LeftMenu {
   @Id
   @GeneratedValue
   private Long menuId;
   private String menuName;
   private int showFlag;
   private int displayOrder;
   private String remark;
}
