package com.ffxiv_fansite.fansite.controller;

import com.ffxiv_fansite.fansite.service.CommonsService;
import com.ffxiv_fansite.fansite.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    private final NewsService newsService;
    private final CommonsService commonsService;

    @Autowired
    public NewsController(final NewsService newsService, final CommonsService commonsService) {
        this.newsService = newsService;
        this.commonsService = commonsService;
    }

    @GetMapping("/news")
    public String news(Model model) {
        commonsService.fillModel(model);

        model.addAttribute("news", newsService.getNews());

        return "news";
    }
}
