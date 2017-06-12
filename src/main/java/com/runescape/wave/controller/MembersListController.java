package com.runescape.wave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
@Controller
public class MembersListController {
    private Long totalExperienceClan = 0L;

    @RequestMapping(value = "/members", method = RequestMethod.POST)
    public String goToMembers(Model model) {
        return "members";
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ModelAndView getMembers() {

        List<String> list = setMembersList();

        Long amountMembers = (long) list.size();

        ModelAndView model = new ModelAndView("members");
        model.addObject("list", list);
        model.addObject("amountMembers", amountMembers);

        Locale locale = new Locale("en", "EN");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        String experienceFormatted = numberFormat.format(totalExperienceClan);

        model.addObject("totalExperience", experienceFormatted);
        totalExperienceClan = 0L;

        return model;
    }

    private List<String> setMembersList() {
        List<String> list = new ArrayList<>();

        try {
            URL link = new URL("http://services.runescape.com/m=clan-hiscores/members_lite.ws?clanName=Wave");
            BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));

            String inputLine;

            int counter = 0;

            while ((inputLine = br.readLine()) != null) {
                String[] array = inputLine.split(",");
                String name = array[0].replaceAll("�", " ");
                String rank = array[1];
                String experience = array[2];
                String kills = array[3];

                int experienceAsInt = 0;
                int killsAsInt = 0;
                String killsFormatted = "";

                Locale locale = new Locale("en", "EN");
                NumberFormat numberFormat = NumberFormat.getInstance(locale);

                if (counter != 0) {
                    experienceAsInt = Integer.parseInt(experience);
                }

                String experienceFormatted = numberFormat.format(experienceAsInt);

                if (counter != 0) {
                    killsAsInt = Integer.parseInt(kills);
                    killsFormatted = numberFormat.format(killsAsInt);
                }
                this.totalExperienceClan += experienceAsInt;

                list.add("<tr><td><a href='member/" + name + "'>" + name + "</a></td><td>" + rank + "</td><td>" + experienceFormatted + "</td><td>" + killsFormatted + "</td></tr>");
                counter++;
            }
        } catch (Exception e) {
            list.add("Something went wrong with connecting to the API of RuneScape.");
        }
        list.remove(0);

        return list;
    }
}
