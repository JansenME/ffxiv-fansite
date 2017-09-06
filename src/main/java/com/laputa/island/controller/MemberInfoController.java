package com.laputa.island.controller;

import com.laputa.island.model.Member;
import com.laputa.island.repository.MemberRepository;
import com.laputa.island.service.MemberInfoService;
import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;

@SuppressWarnings("unchecked")
@Controller
public class MemberInfoController {
    private static final Logger logger = LoggerFactory.getLogger(MemberInfoController.class);

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "/member/{name}", method = RequestMethod.GET)
    public ModelAndView getMemberLevels(@PathVariable String name) throws IOException, FeedException, ParseException {
        logger.info("In method getMemberLevels...");

        Member member = memberRepository.findOneByName(name);

        MemberInfoService memberInfoService = new MemberInfoService();
        ModelAndView model = memberInfoService.getMemberPageInfo(name, member);

        return model;
    }
}
