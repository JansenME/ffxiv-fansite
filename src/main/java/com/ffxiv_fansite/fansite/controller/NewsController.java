package com.ffxiv_fansite.fansite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NewsController {
    @Value("${app.version:unknown}")
    String version;

    @Autowired
    public NewsController() {

    }

    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("versionNumber", version);
        model.addAttribute("currentYear", new SimpleDateFormat("yyyy").format(new Date()));

        return "news";
    }
}
