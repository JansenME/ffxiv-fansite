package com.runescape.wave.controller;

import com.runescape.wave.model.Member;
import com.runescape.wave.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
@Controller
public class MembersInfoController {
    private int totalVirtualLevel = 0;

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "/member/{name}", method = RequestMethod.GET)
    public ModelAndView getMemberLevels(@PathVariable String name) {
        List<String> memberLevelsList = getMemberLevelsList(name);
        List<String> adventurersLogList = getAdventurersLogList(name);

        ModelAndView model = new ModelAndView("member");
        model.addObject("listLevels", memberLevelsList);
        model.addObject("memberName", name);
        model.addObject("listAdventurersLog", adventurersLogList);

        Member member = memberRepository.findOneByName(name);

        if (member != null) {
            model.addObject("tableInfo", getMemberTableInfo(member));
        }

        return model;
    }

    private List<String> getAdventurersLogList(String name) {
        List <String> list = new ArrayList<>();
        try {
            URL rssUrl = new URL("http://services.runescape.com/l=0/m=adventurers-log/rssfeed?searchName=" + name);
            BufferedReader br = new BufferedReader(new InputStreamReader(rssUrl.openStream()));

            String line;

            while ((line = br.readLine()) != null) {
                String adventurersLogTitle;
                if (line.contains("<title>")) {
                    int firstPos = line.indexOf("<title>");
                    adventurersLogTitle = line.substring(firstPos);
                    adventurersLogTitle = adventurersLogTitle.replace("<title>", "");
                    int lastPos = adventurersLogTitle.indexOf("</title>");
                    adventurersLogTitle = adventurersLogTitle.substring(0, lastPos);
                    list.add(adventurersLogTitle);
                }

            }
            list.remove(0);
            list.remove(0);
            br.close();

            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            list.add("Something went wrong. Probably this member of the clan is not a member on Runescape. Just so you know, this is not my fault. I'm still awesome...");
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            list.add("Something went wrong");
            return list;
        }
    }

    private String getMemberTableInfo(Member member) {
        //Get correct lines for biography
        String biography;

        if (member.getBiography() == null) {
            biography = "This member did not give us an amazing text about themself!";
        }
        else {
            biography = member.getBiography();
        }

        //Capitalize gender
        String gender;

        if (member.getGender() == null) {
            gender = "Unknown";
        }
        else {
            gender = member.getGender().substring(0, 1).toUpperCase() + member.getGender().substring(1);
        }

        //Get the correct lines for the city
        String cityStateCountry;

        String city, state, country;
        if (member.getCity() == null) {
            city = "Unknown";
        }
        else {
            city = member.getCity();
        }

        if (member.getState() == null) {
            state = "Unknown";
        }
        else {
            state = member.getState();
        }

        if (member.getCountry() == null) {
            country = "Unknown";
        }
        else {
            country = member.getCountry();
        }

        if (member.getCity() == null && member.getState() == null && member.getCountry() == null) {
            cityStateCountry = "Unknown";
        }
        else {
            cityStateCountry = city + ", " + state + ", " + country;
        }

        //Get the correct date format
        String dob;
        if (member.getDateOfBirth() == null) {
            dob = "Unknown";
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = "" + member.getDateOfBirth();
            Date d = null;
            try {
                d = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateFormat.applyPattern("MM/dd/yyy");

            LocalDate now = LocalDate.now();

            Period age = Period.between(member.getDateOfBirth().toLocalDate(), now);

            dob = dateFormat.format(d) + " (" + age.getYears() + ")";
        }

        //Make the correct string for the ModelAndView
        return "<table style='width:100%;'>"
                + "<tr>"
                + "<td style='width:60%; padding:5px; text-align:justify; text-align-last:center; vertical-align: text-top;'>" + biography + "</td>"
                + "<td>"
                + "<table>"
                + "<tr><td style='padding:5px; text-align:right;'><strong>Gender: </strong></td><td style='padding:5px;'>" + gender + "</td></tr>"
                + "<tr><td style='padding:5px; text-align:right;'><strong>Date of Birth: </strong></td><td style='padding:5px;'>" + dob + "</td></tr>"
                + "<tr><td style='padding:5px; text-align:right;'><strong>City: </strong></td><td style='padding:5px;'>" + cityStateCountry + "</td></tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</table>";
    }

    private List<String> getMemberLevelsList(String name) {
        List<String> list = new ArrayList<>();

        try {
            URL link = new URL("http://services.runescape.com/m=hiscore/index_lite.ws?player=" + name);
            BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));

            String inputLine;
            String skill;
            int counter = 0;
            while ((inputLine = br.readLine()) != null) {
                String[] array = inputLine.split(",");
                switch (counter) {
                    case 0: skill = "overall"; break;
                    case 1: skill = "attack"; break;
                    case 2: skill = "defence"; break;
                    case 3: skill = "strength"; break;
                    case 4: skill = "constitution"; break;
                    case 5: skill = "ranged"; break;
                    case 6: skill = "prayer"; break;
                    case 7: skill = "magic"; break;
                    case 8: skill = "cooking"; break;
                    case 9: skill = "woodcutting"; break;
                    case 10: skill = "fletching"; break;
                    case 11: skill = "fishing"; break;
                    case 12: skill = "firemaking"; break;
                    case 13: skill = "crafting"; break;
                    case 14: skill = "smithing"; break;
                    case 15: skill = "mining"; break;
                    case 16: skill = "herblore"; break;
                    case 17: skill = "agility"; break;
                    case 18: skill = "thieving"; break;
                    case 19: skill = "slayer"; break;
                    case 20: skill = "farming"; break;
                    case 21: skill = "runecrafting"; break;
                    case 22: skill = "hunter"; break;
                    case 23: skill = "construction"; break;
                    case 24: skill = "summoning"; break;
                    case 25: skill = "dungeoneering"; break;
                    case 26: skill = "divination"; break;
                    case 27: skill = "invention"; break;
                    default: skill = "unknown"; break;
                }

                String skillUpperCase = skill.substring(0, 1).toUpperCase() + skill.substring(1);

                Locale locale = new Locale("en", "EN");
                NumberFormat numberFormat = NumberFormat.getInstance(locale);

                int array2Int = Integer.parseInt(array[2]);
                String array2Formatted = numberFormat.format(array2Int);

                int array0Int = Integer.parseInt(array[0]);
                String array0Formatted = numberFormat.format(array0Int);

                int array1Int = Integer.parseInt(array[1]);

                if (array1Int == 0) {
                    array[1] = "1";
                }

                if (array2Int == -1) {
                    array2Formatted = "0";
                }

                if (array0Int == -1) {
                    array0Formatted = "None";
                }

                //Set virtual levels
                if (array2Int >= 14391160 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "100";
                if (array2Int >= 15889109 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "101";
                if (array2Int >= 17542976 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "102";
                if (array2Int >= 19368992 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "103";
                if (array2Int >= 21385073 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "104";
                if (array2Int >= 23611006 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "105";
                if (array2Int >= 26068632 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "106";
                if (array2Int >= 28782069 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "107";
                if (array2Int >= 31777943 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "108";
                if (array2Int >= 35085654 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "109";
                if (array2Int >= 38737661 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "110";
                if (array2Int >= 42769801 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "111";
                if (array2Int >= 47221641 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "112";
                if (array2Int >= 52136869 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "113";
                if (array2Int >= 57563718 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "114";
                if (array2Int >= 63555443 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "115";
                if (array2Int >= 70170840 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "116";
                if (array2Int >= 77474828 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "117";
                if (array2Int >= 85539082 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "118";
                if (array2Int >= 94442737 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "119";
                if (array2Int >= 104273167 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "120";

                //Set virtual levels for Elite skills
                if (array2Int >= 83370445 && skill.equals("invention")) array[1] = "121";
                if (array2Int >= 86186124 && skill.equals("invention")) array[1] = "122";
                if (array2Int >= 89066630 && skill.equals("invention")) array[1] = "123";
                if (array2Int >= 92012904 && skill.equals("invention")) array[1] = "124";
                if (array2Int >= 95025896 && skill.equals("invention")) array[1] = "125";
                if (array2Int >= 98106559 && skill.equals("invention")) array[1] = "126";
                if (array2Int >= 101255855 && skill.equals("invention")) array[1] = "127";
                if (array2Int >= 104474750 && skill.equals("invention")) array[1] = "128";
                if (array2Int >= 107764216 && skill.equals("invention")) array[1] = "129";
                if (array2Int >= 111125230 && skill.equals("invention")) array[1] = "130";
                if (array2Int >= 114558777 && skill.equals("invention")) array[1] = "131";
                if (array2Int >= 118065845 && skill.equals("invention")) array[1] = "132";
                if (array2Int >= 121647430 && skill.equals("invention")) array[1] = "133";
                if (array2Int >= 125304532 && skill.equals("invention")) array[1] = "134";
                if (array2Int >= 129038159 && skill.equals("invention")) array[1] = "135";
                if (array2Int >= 132849323 && skill.equals("invention")) array[1] = "136";
                if (array2Int >= 136739041 && skill.equals("invention")) array[1] = "137";
                if (array2Int >= 140708338 && skill.equals("invention")) array[1] = "138";
                if (array2Int >= 144758242 && skill.equals("invention")) array[1] = "139";
                if (array2Int >= 148889790 && skill.equals("invention")) array[1] = "140";
                if (array2Int >= 153104021 && skill.equals("invention")) array[1] = "141";
                if (array2Int >= 157401983 && skill.equals("invention")) array[1] = "142";
                if (array2Int >= 161784728 && skill.equals("invention")) array[1] = "143";
                if (array2Int >= 166253312 && skill.equals("invention")) array[1] = "144";
                if (array2Int >= 170808801 && skill.equals("invention")) array[1] = "145";
                if (array2Int >= 175452262 && skill.equals("invention")) array[1] = "146";
                if (array2Int >= 180184770 && skill.equals("invention")) array[1] = "147";
                if (array2Int >= 185007406 && skill.equals("invention")) array[1] = "148";
                if (array2Int >= 189921255 && skill.equals("invention")) array[1] = "149";
                if (array2Int >= 194927409 && skill.equals("invention")) array[1] = "150";
                if (array2Int >= 200000000 && skill.equals("invention")) array[1] = array2Formatted;

                int levelAsInt = Integer.parseInt(array[1]);

                if (skill.equals("overall")) {
                    list.add("<tr><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + " (" + getTotalVirtualLevel(name) + ")</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                }
                else {
                    if (skill.equals("dungeoneering")) {
                        if (array2Int == 200000000) {
                            list.add("<tr style='color:red; font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else if (levelAsInt == 120) {
                            list.add("<tr style='color:limegreen; font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else {
                            list.add("<tr><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                    }
                    else if (skill.equals("invention")) {
                        if (array2Int == 200000000) {
                            list.add("<tr style='color:red; font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else if (levelAsInt == 150) {
                            list.add("<tr style='color:limegreen; font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else if (levelAsInt >= 120) {
                            list.add("<tr style='font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else {
                            list.add("<tr><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                    }
                    else {
                        if (array2Int == 200000000) {
                            list.add("<tr style='color:red; font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else if (levelAsInt == 120) {
                            list.add("<tr style='color:limegreen; font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else if (levelAsInt >= 99) {
                            list.add("<tr style='font-weight:bold;'><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                        else {
                            list.add("<tr><td><img style='width:20px;' src='http://www.insiteweb.nl/wave-runescape/images/" + skill + ".png' /></td><td>" + skillUpperCase + "</td><td>" + array[1] + "</td><td align='right'>" + array2Formatted + "</td><td align='right'>" + array0Formatted + "</td></tr>");
                        }
                    }
                }
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        totalVirtualLevel = 0;
        return list;
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
                switch (counter) {
                    case 0: skill = "overall"; break;
                    case 1: skill = "attack"; break;
                    case 2: skill = "defence"; break;
                    case 3: skill = "strength"; break;
                    case 4: skill = "constitution"; break;
                    case 5: skill = "ranged"; break;
                    case 6: skill = "prayer"; break;
                    case 7: skill = "magic"; break;
                    case 8: skill = "cooking"; break;
                    case 9: skill = "woodcutting"; break;
                    case 10: skill = "fletching"; break;
                    case 11: skill = "fishing"; break;
                    case 12: skill = "firemaking"; break;
                    case 13: skill = "crafting"; break;
                    case 14: skill = "smithing"; break;
                    case 15: skill = "mining"; break;
                    case 16: skill = "herblore"; break;
                    case 17: skill = "agility"; break;
                    case 18: skill = "thieving"; break;
                    case 19: skill = "slayer"; break;
                    case 20: skill = "farming"; break;
                    case 21: skill = "runecrafting"; break;
                    case 22: skill = "hunter"; break;
                    case 23: skill = "construction"; break;
                    case 24: skill = "summoning"; break;
                    case 25: skill = "dungeoneering"; break;
                    case 26: skill = "divination"; break;
                    case 27: skill = "invention"; break;
                    default: skill = "unknown"; break;
                }

                Locale locale = new Locale("en", "EN");
                NumberFormat numberFormat = NumberFormat.getInstance(locale);

                int array2Int = Integer.parseInt(array[2]);
                String array2Formatted = numberFormat.format(array2Int);

                int array1Int = Integer.parseInt(array[1]);

                if (array1Int == 0) {
                    array[1] = "1";
                }

                if (array2Int == -1) {
                    array2Formatted = "0";
                }

                //Set virtual levels
                if (array2Int >= 14391160 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "100";
                if (array2Int >= 15889109 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "101";
                if (array2Int >= 17542976 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "102";
                if (array2Int >= 19368992 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "103";
                if (array2Int >= 21385073 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "104";
                if (array2Int >= 23611006 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "105";
                if (array2Int >= 26068632 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "106";
                if (array2Int >= 28782069 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "107";
                if (array2Int >= 31777943 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "108";
                if (array2Int >= 35085654 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "109";
                if (array2Int >= 38737661 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "110";
                if (array2Int >= 42769801 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "111";
                if (array2Int >= 47221641 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "112";
                if (array2Int >= 52136869 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "113";
                if (array2Int >= 57563718 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "114";
                if (array2Int >= 63555443 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "115";
                if (array2Int >= 70170840 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "116";
                if (array2Int >= 77474828 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "117";
                if (array2Int >= 85539082 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "118";
                if (array2Int >= 94442737 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "119";
                if (array2Int >= 104273167 && !skill.equals("overall") && !skill.equals("invention")) array[1] = "120";

                //Set virtual levels for Elite skills
                if (array2Int >= 83370445 && skill.equals("invention")) array[1] = "121";
                if (array2Int >= 86186124 && skill.equals("invention")) array[1] = "122";
                if (array2Int >= 89066630 && skill.equals("invention")) array[1] = "123";
                if (array2Int >= 92012904 && skill.equals("invention")) array[1] = "124";
                if (array2Int >= 95025896 && skill.equals("invention")) array[1] = "125";
                if (array2Int >= 98106559 && skill.equals("invention")) array[1] = "126";
                if (array2Int >= 101255855 && skill.equals("invention")) array[1] = "127";
                if (array2Int >= 104474750 && skill.equals("invention")) array[1] = "128";
                if (array2Int >= 107764216 && skill.equals("invention")) array[1] = "129";
                if (array2Int >= 111125230 && skill.equals("invention")) array[1] = "130";
                if (array2Int >= 114558777 && skill.equals("invention")) array[1] = "131";
                if (array2Int >= 118065845 && skill.equals("invention")) array[1] = "132";
                if (array2Int >= 121647430 && skill.equals("invention")) array[1] = "133";
                if (array2Int >= 125304532 && skill.equals("invention")) array[1] = "134";
                if (array2Int >= 129038159 && skill.equals("invention")) array[1] = "135";
                if (array2Int >= 132849323 && skill.equals("invention")) array[1] = "136";
                if (array2Int >= 136739041 && skill.equals("invention")) array[1] = "137";
                if (array2Int >= 140708338 && skill.equals("invention")) array[1] = "138";
                if (array2Int >= 144758242 && skill.equals("invention")) array[1] = "139";
                if (array2Int >= 148889790 && skill.equals("invention")) array[1] = "140";
                if (array2Int >= 153104021 && skill.equals("invention")) array[1] = "141";
                if (array2Int >= 157401983 && skill.equals("invention")) array[1] = "142";
                if (array2Int >= 161784728 && skill.equals("invention")) array[1] = "143";
                if (array2Int >= 166253312 && skill.equals("invention")) array[1] = "144";
                if (array2Int >= 170808801 && skill.equals("invention")) array[1] = "145";
                if (array2Int >= 175452262 && skill.equals("invention")) array[1] = "146";
                if (array2Int >= 180184770 && skill.equals("invention")) array[1] = "147";
                if (array2Int >= 185007406 && skill.equals("invention")) array[1] = "148";
                if (array2Int >= 189921255 && skill.equals("invention")) array[1] = "149";
                if (array2Int >= 194927409 && skill.equals("invention")) array[1] = "150";
                if (array2Int >= 200000000 && skill.equals("invention")) array[1] = array2Formatted;

                totalLevel = Integer.parseInt(array[1]);

                if (!skill.equals("overall")) {
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
}
