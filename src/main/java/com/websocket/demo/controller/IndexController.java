package com.websocket.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {

    @RequestMapping({"/","/index"})
    public String index(ModelMap mmap){
        mmap.put("demo","测试websocket");
        return "index";
    }
}
