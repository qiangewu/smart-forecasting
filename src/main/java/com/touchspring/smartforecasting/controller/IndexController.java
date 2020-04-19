package com.touchspring.smartforecasting.controller;

import com.touchspring.smartforecasting.repository.sys.SysUserRepository;
import org.springframework.web.bind.annotation.*;


@RestController
public class IndexController {

    private final SysUserRepository sysUserRepository;

    public IndexController(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }




}
