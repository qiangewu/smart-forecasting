package com.touchspring.smartforecasting.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MesLoginController {

    @RequestMapping("mes-login")
    public String mesLogin(String userId) {

        return "redirect:http://baidu.com";
    }
}
