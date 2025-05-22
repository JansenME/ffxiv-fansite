package com.ffxiv_fansite.fansite.controller;

import com.ffxiv_fansite.fansite.service.NewsService;
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

    private final NewsService newsService;

    @Autowired
    public NewsController(final NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("versionNumber", version);
        model.addAttribute("currentYear", new SimpleDateFormat("yyyy").format(new Date()));

        model.addAttribute("news", newsService.getNews());

        return "news";
    }
}
