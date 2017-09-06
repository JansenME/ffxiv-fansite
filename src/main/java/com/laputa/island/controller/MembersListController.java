package com.laputa.island.controller;

import com.laputa.island.service.MembersListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
@Controller
public class MembersListController {
    private static final Logger logger = LoggerFactory.getLogger(MembersListController.class);

    @RequestMapping(value = "/members", method = RequestMethod.POST)
    public String goToMembers(Model model) {
        return "members";
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ModelAndView getMembers() throws IOException {
        logger.info("In method getMembers...");

        MembersListService membersListService = new MembersListService();
        ModelAndView model = membersListService.getMembers();

        return model;
    }
}
