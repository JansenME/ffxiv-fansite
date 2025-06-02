package com.ffxiv_fansite.fansite.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class IndexController {
    @Value("${app.version:unknown}")
    String version;

    @GetMapping(value={"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("versionNumber", version);
        model.addAttribute("currentYear", new SimpleDateFormat("yyyy").format(new Date()));

        return "index";
    }
}
