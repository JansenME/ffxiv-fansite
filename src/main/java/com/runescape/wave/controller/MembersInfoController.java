package com.runescape.wave.controller;

import com.runescape.wave.model.AdventurersLogInList;
import com.runescape.wave.model.Member;
import com.runescape.wave.model.SkillsInList;
import com.runescape.wave.repository.MemberRepository;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
@Controller
public class MembersInfoController {
    private static final String UNKNOWN = "unknown";
    private static final String INVENTION = "invention";
    private static final String DUNGEONEERING = "dungeoneering";
    private static final String OVERALL = "overall";

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "/member/{name}", method = RequestMethod.GET)
    public ModelAndView getMemberLevels(@PathVariable String name) throws ParseException, IOException, FeedException {
        List<SkillsInList> memberLevelsList = getMemberLevelsList(name);
        List<AdventurersLogInList> adventurersLogList = getAdventurersLogList(name);

        ModelAndView model = new ModelAndView("member");
        model.addObject("listLevels", memberLevelsList);
        model.addObject("memberName", name);
        model.addObject("listAdventurersLog", adventurersLogList);

        Member member = memberRepository.findOneByName(name);

        if (member != null) {
            model.addObject("showTableMember", true);
            model.addObject("tableInfo", getMemberTableInfo(member));
        }

        return model;
    }

    private List<AdventurersLogInList> getAdventurersLogList(String name) throws IOException, FeedException {
        List <AdventurersLogInList> list = new ArrayList<>();

        final URL rssUrl = new URL("http://services.runescape.com/l=0/m=adventurers-log/rssfeed?searchName=" + name);
        final SyndFeedInput input = new SyndFeedInput();
        final SyndFeed feed = input.build(new XmlReader(rssUrl));

        for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
            LocalDate dateAsLocalDate = LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneOffset.UTC).toLocalDate();

            String formattedDate = dateAsLocalDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            String title = entry.getTitle();
            String description = entry.getDescription().getValue().trim().replaceAll("\\s+", " ").replaceAll("\\s,", ",");
            list.add(new AdventurersLogInList(formattedDate, title, description));
        }
        return list;
    }

    private Member getMemberTableInfo(Member member) throws ParseException {
        //Get correct lines for biography
        String biography;

        if (member.getBiography() == null) biography = "This member did not give us an amazing text about themself!";
        else biography = member.getBiography();

        //Capitalize gender
        String gender;

        if (member.getGender() == null) gender = UNKNOWN;
        else gender = member.getGender().substring(0, 1).toUpperCase() + member.getGender().substring(1);

        //Get the correct lines for the city
        member.setCityStateCountry(member.getCity(), member.getState(), member.getCountry());
        String cityStateCountry = member.getCityStateCountry();

        //Get the correct date format
        member.setDob(member.getDateOfBirth().toString());
        String dob = member.getDob();

        //Make the correct string for the ModelAndView
        return new Member(biography, gender, dob, cityStateCountry);
    }

    private List<SkillsInList> getMemberLevelsList(String name) {
        List<SkillsInList> list = new ArrayList<>();

        try {
            URL link = new URL("http://services.runescape.com/m=hiscore/index_lite.ws?player=" + name);
            BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));

            String inputLine;
            String skill;

            int counter = 0;

            while ((inputLine = br.readLine()) != null) {
                String[] array = inputLine.split(",");

                String rank = array[0];
                String level = array[1];
                String experience = array[2];

                skill = getCorrectSkillName(counter);

                Locale locale = new Locale("en", "EN");
                NumberFormat numberFormat = NumberFormat.getInstance(locale);

                int experienceAsInt = Integer.parseInt(experience);
                String experienceFormatted = numberFormat.format(experienceAsInt);

                int rankAsInt = Integer.parseInt(rank);
                String rankFormatted = numberFormat.format(rankAsInt);

                int levelAsInt = Integer.parseInt(level);

                if (levelAsInt == 0) level = "1";
                if (experienceAsInt == -1) experienceFormatted = "0";
                if (rankAsInt == -1) rankFormatted = "None";

                String correctVirtualLevel = setCorrectVirtualLevel(experienceAsInt, skill, level);
                int correctVirtualLevelAsInt = Integer.parseInt(correctVirtualLevel);

                String color = getTheCorrectColor(skill, experienceAsInt, correctVirtualLevelAsInt);

                String totalVirtualLevel = null;
                if (OVERALL.equals(skill)) totalVirtualLevel = Integer.toString(getTotalVirtualLevel(name));

                list.add(new SkillsInList(skill, correctVirtualLevel, experienceFormatted, rankFormatted, totalVirtualLevel, color));
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getTheCorrectColor(String skill, int experienceAsInt, int correctVirtualLevelAsInt) {
        final String colorRed = "red";
        final String colorLimegreen = "limegreen";
        final String colorBold = "bold";
        final String colorNormal = "normal";

        switch (skill) {
            case OVERALL:
                return colorNormal;

            case DUNGEONEERING:
                if (experienceAsInt == 200000000) return colorRed;
                else if (correctVirtualLevelAsInt == 120) return colorLimegreen;
                else return colorNormal;

            case INVENTION:
                if (experienceAsInt == 200000000) return colorRed;
                else if (correctVirtualLevelAsInt == 150) return colorLimegreen;
                else if (correctVirtualLevelAsInt >= 120) return colorBold;
                else return colorNormal;

            default:
                if (experienceAsInt == 200000000) return colorRed;
                else if (correctVirtualLevelAsInt == 120) return colorLimegreen;
                else if (correctVirtualLevelAsInt >= 99) return colorBold;
                else return colorNormal;
        }
    }

    private int getTotalVirtualLevel(String nameMember) {
        int totalLevel = 0;
        int totalVirtualLevel = 0;
        try {
            URL link = new URL("http://services.runescape.com/m=hiscore/index_lite.ws?player=" + nameMember);
            BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));

            String inputLine;
            String skill;
            int counter = 0;
            while ((inputLine = br.readLine()) != null) {
                String[] array = inputLine.split(",");

                skill = getCorrectSkillName(counter);

                int experienceLevel = Integer.parseInt(array[2]);

                int level = Integer.parseInt(array[1]);

                if (level == 0) {
                    array[1] = "1";
                }

                String correctVirtualLevel = setCorrectVirtualLevel(experienceLevel, skill, array[1]);

                totalLevel = Integer.parseInt(correctVirtualLevel);

                if (!skill.equals(OVERALL)) {
                    totalVirtualLevel += totalLevel;
                }
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalVirtualLevel;
    }

    private static String getCorrectSkillName(int counter) {
        switch (counter) {
            case 0: return OVERALL;
            case 1: return "attack";
            case 2: return "defence";
            case 3: return "strength";
            case 4: return "constitution";
            case 5: return "ranged";
            case 6: return "prayer";
            case 7: return "magic";
            case 8: return "cooking";
            case 9: return "woodcutting";
            case 10: return "fletching";
            case 11: return "fishing";
            case 12: return "firemaking";
            case 13: return "crafting";
            case 14: return "smithing";
            case 15: return "mining";
            case 16: return "herblore";
            case 17: return "agility";
            case 18: return "thieving";
            case 19: return "slayer";
            case 20: return "farming";
            case 21: return "runecrafting";
            case 22: return "hunter";
            case 23: return "construction";
            case 24: return "summoning";
            case 25: return DUNGEONEERING;
            case 26: return "divination";
            case 27: return INVENTION;
            default: return UNKNOWN;
        }
    }

    private static String setCorrectVirtualLevel(int totalExperience, String skill, String level) {
        //Set virtual levels
        if (totalExperience >= 104273167 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "120";
        if (totalExperience >= 94442737 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "119";
        if (totalExperience >= 85539082 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "118";
        if (totalExperience >= 77474828 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "117";
        if (totalExperience >= 70170840 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "116";
        if (totalExperience >= 63555443 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "115";
        if (totalExperience >= 57563718 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "114";
        if (totalExperience >= 52136869 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "113";
        if (totalExperience >= 47221641 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "112";
        if (totalExperience >= 42769801 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "111";
        if (totalExperience >= 38737661 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "110";
        if (totalExperience >= 35085654 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "109";
        if (totalExperience >= 31777943 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "108";
        if (totalExperience >= 28782069 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "107";
        if (totalExperience >= 26068632 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "106";
        if (totalExperience >= 23611006 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "105";
        if (totalExperience >= 21385073 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "104";
        if (totalExperience >= 19368992 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "103";
        if (totalExperience >= 17542976 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "102";
        if (totalExperience >= 15889109 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "101";
        if (totalExperience >= 14391160 && !skill.equals(OVERALL) && !skill.equals(INVENTION)) return "100";

        //Set virtual levels for Elite skills
        if (totalExperience >= 194927409 && skill.equals(INVENTION)) return "150";
        if (totalExperience >= 189921255 && skill.equals(INVENTION)) return "149";
        if (totalExperience >= 185007406 && skill.equals(INVENTION)) return "148";
        if (totalExperience >= 180184770 && skill.equals(INVENTION)) return "147";
        if (totalExperience >= 175452262 && skill.equals(INVENTION)) return "146";
        if (totalExperience >= 170808801 && skill.equals(INVENTION)) return "145";
        if (totalExperience >= 166253312 && skill.equals(INVENTION)) return "144";
        if (totalExperience >= 161784728 && skill.equals(INVENTION)) return "143";
        if (totalExperience >= 157401983 && skill.equals(INVENTION)) return "142";
        if (totalExperience >= 153104021 && skill.equals(INVENTION)) return "141";
        if (totalExperience >= 148889790 && skill.equals(INVENTION)) return "140";
        if (totalExperience >= 144758242 && skill.equals(INVENTION)) return "139";
        if (totalExperience >= 140708338 && skill.equals(INVENTION)) return "138";
        if (totalExperience >= 136739041 && skill.equals(INVENTION)) return "137";
        if (totalExperience >= 132849323 && skill.equals(INVENTION)) return "136";
        if (totalExperience >= 129038159 && skill.equals(INVENTION)) return "135";
        if (totalExperience >= 125304532 && skill.equals(INVENTION)) return "134";
        if (totalExperience >= 121647430 && skill.equals(INVENTION)) return "133";
        if (totalExperience >= 118065845 && skill.equals(INVENTION)) return "132";
        if (totalExperience >= 114558777 && skill.equals(INVENTION)) return "131";
        if (totalExperience >= 111125230 && skill.equals(INVENTION)) return "130";
        if (totalExperience >= 107764216 && skill.equals(INVENTION)) return "129";
        if (totalExperience >= 104474750 && skill.equals(INVENTION)) return "128";
        if (totalExperience >= 101255855 && skill.equals(INVENTION)) return "127";
        if (totalExperience >= 98106559 && skill.equals(INVENTION)) return "126";
        if (totalExperience >= 95025896 && skill.equals(INVENTION)) return "125";
        if (totalExperience >= 92012904 && skill.equals(INVENTION)) return "124";
        if (totalExperience >= 89066630 && skill.equals(INVENTION)) return "123";
        if (totalExperience >= 86186124 && skill.equals(INVENTION)) return "122";
        if (totalExperience >= 83370445 && skill.equals(INVENTION)) return "121";

        return level;
    }
}
