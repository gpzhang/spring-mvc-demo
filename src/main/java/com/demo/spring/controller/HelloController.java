package com.demo.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haishen
 * @date 2019/3/25
 */
@Controller
@RequestMapping("/home")
public class HelloController {

    @RequestMapping("/index")
    public String index() {
        System.out.println("正常访问");
        return "index";
    }

}
