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
@Table(name = "sys_i18n_detail")
@SQLDelete(sql = "update sys_i18n_detail  set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0") 
public class SysI18NDetail extends BaseIdEntity{

  @Column(name = "language_code")
  private String languageCode;

  @Column(name = "i18n_code")
  private String i18nCode;

  @Column(name = "value")
  private String value;


}
