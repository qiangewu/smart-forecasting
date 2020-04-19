package com.touchspring.smartforecasting.domain.entity.sys;

import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_language")
@SQLDelete(sql = "update sys_language  set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0") 
public class SysLanguage extends BaseIdEntity{

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "is_active")
  private String isActive;

  @Column(name = "icon")
  private String icon;


}
