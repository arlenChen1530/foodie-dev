package com.arlenchen.controller;

import com.arlenchen.appservice.StuAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author arlenchen
 */
@ApiIgnore
@RestController
public class StuFooController {
    private final StuAppService stuService;

    @Autowired
    public StuFooController(StuAppService stuService) {
        this.stuService = stuService;
    }

    @GetMapping("/getStu")
    public Object hello(int id) {

        return stuService.getStuInfo(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu() {
        stuService.saveStu();
        return "ok";
    }

    @PostMapping("/updateStu")
    public Object updateStu(int id) {
        stuService.updateStu(id);
        return "ok";
    }

    @PostMapping("/deleteStu")
    public Object deleteStu(int id) {
        stuService.deleteStu(id);
        return "ok";
    }
}
