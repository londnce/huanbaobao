package com.wsf.huanbaobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectController {

    @GetMapping("/{url}")
    public String url(@PathVariable("url") String url){
        return url;
    }
}
