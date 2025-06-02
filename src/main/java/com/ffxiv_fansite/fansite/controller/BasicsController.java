package com.ffxiv_fansite.fansite.controller;

import com.ffxiv_fansite.fansite.service.BasicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BasicsController {
    @Value("${app.version:unknown}")
    String version;

    private final BasicsService basicsService;

    @Autowired
    public BasicsController(final BasicsService basicsService) {
        this.basicsService = basicsService;
    }

    @GetMapping("/basics")
    public String basics(Model model) {
        fillModel(model);

        return "basics";
    }

    @GetMapping("basics-jobs")
    public String basicsJobs(Model model) {
        fillModel(model);

        model.addAttribute("jobs", basicsService.getJobs());
        model.addAttribute("crafters", basicsService.getCrafters());
        model.addAttribute("gatherers", basicsService.getGatherers());

        return "basics-jobs";
    }

    @GetMapping("basics-cities")
    public String basicsCities(Model model) {
        fillModel(model);

        model.addAttribute("expansionList", basicsService.getExpansions());
        model.addAttribute("majorCitiesList", basicsService.getMajorCities());
        model.addAttribute("minorCitiesList", basicsService.getMinorCities());

        return "basics-cities";
    }

    private void fillModel(Model model) {
        model.addAttribute("versionNumber", version);
        model.addAttribute("currentYear", new SimpleDateFormat("yyyy").format(new Date()));
    }
}
