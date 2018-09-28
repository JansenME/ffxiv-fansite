package com.laputa.island.service;

import com.laputa.island.model.MembersInList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MembersListService {
    private static final Logger logger = LoggerFactory.getLogger(MembersListService.class);

    private Long totalExperienceClan = 0L;

    public ModelAndView getMembers() throws IOException {
        logger.info("Creating the list of members.");

        List<MembersInList> list = setMembersList();

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

    private List<MembersInList> setMembersList() throws IOException {
        List<MembersInList> list = new ArrayList<>();

        URL link = new URL("http://services.runescape.com/m=clan-hiscores/members_lite.ws?clanName=Laputa Island");
        BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));

        String inputLine;

        int counter = 0;

        while ((inputLine = br.readLine()) != null) {
            if (counter != 0) {
                String[] array = inputLine.split(",");
                String name = array[0].replaceAll("�", " ");
                String rank = array[1];
                String experience = array[2];
                String kills = array[3];

                int experienceAsInt = Integer.parseInt(experience);
                int killsAsInt = Integer.parseInt(kills);

                this.totalExperienceClan += experienceAsInt;

                list.add(new MembersInList(name, rank, experienceAsInt, killsAsInt));

            }
            counter++;
        }

        return list;
    }
}
