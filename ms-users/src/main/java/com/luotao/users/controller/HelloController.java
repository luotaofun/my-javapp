package com.luotao.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/5/28 19:44
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    @GetMapping
    public String hello(String name) {
        return "Hello" + name;
    }
}