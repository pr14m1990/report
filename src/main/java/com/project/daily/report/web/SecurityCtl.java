package com.project.daily.report.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityCtl {

    @RequestMapping("/login")
    private String getLoginPage() {
        return "security/index";
    }

    @RequestMapping("/error-login")
    private String getErrorLoginPage(Model modal) {
        modal.addAttribute("error", "error");
        return "security/index";
    }
}
