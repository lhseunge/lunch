package com.fun.lunch.global.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
