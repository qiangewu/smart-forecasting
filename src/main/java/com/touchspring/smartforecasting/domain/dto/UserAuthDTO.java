package com.touchspring.smartforecasting.domain.dto;

import com.touchspring.smartforecasting.domain.entity.sys.SysUser;
import lombok.Data;

@Data
public class UserAuthDTO {
    private String id;
    private String userName;
    private String loginName;

    public UserAuthDTO() {

    }

    public UserAuthDTO(SysUser sysUser) {
        this.id = sysUser.getId();
        this.userName = sysUser.getUserName();
        this.loginName = sysUser.getLoginName();
    }
}
