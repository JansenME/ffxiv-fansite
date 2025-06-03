package com.ffxiv_fansite.fansite.controller;

import com.ffxiv_fansite.fansite.service.BasicsService;
import com.ffxiv_fansite.fansite.service.CommonsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/basics")
public class BasicsController {
    private final BasicsService basicsService;
    private final CommonsService commonsService;

    @Autowired
    public BasicsController(final BasicsService basicsService, final CommonsService commonsService) {
        this.basicsService = basicsService;
        this.commonsService = commonsService;
    }

    @GetMapping({"", "/"})
    public String basics(Model model) {
        commonsService.fillModel(model);

        return "basics";
    }

    @GetMapping("jobs")
    public String basicsJobs(Model model) {
        commonsService.fillModel(model);

        model.addAttribute("jobs", basicsService.getJobs());
        model.addAttribute("crafters", basicsService.getCrafters());
        model.addAttribute("gatherers", basicsService.getGatherers());

        return "basics-jobs";
    }

    @GetMapping("cities")
    public String basicsCities(Model model) {
        commonsService.fillModel(model);

        model.addAttribute("expansions", basicsService.getExpansions());
        model.addAttribute("majorCities", basicsService.getMajorCities());
        model.addAttribute("minorCities", basicsService.getMinorCities());

        return "basics-cities";
    }

    @GetMapping("zones")
    public String basicsZones(Model model) {
        commonsService.fillModel(model);

        model.addAttribute("regions", basicsService.getRegions());
        model.addAttribute("zones", basicsService.getZones());

        return "basics-zones";
    }
}
