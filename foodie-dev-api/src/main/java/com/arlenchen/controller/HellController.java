package com.arlenchen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arlenchen
 */
@RestController
public class HellController {
    @GetMapping("/hello")
    public  Object hello(){
        return  "hello";
    }
}
