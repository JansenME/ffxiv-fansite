package com.runescape.wave.controller;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.runescape.wave.ItemPrice;
import com.runescape.wave.model.Items;
import com.runescape.wave.repository.ItemsRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
@Controller
public class ItemController {
    @Autowired
    private ItemsRepository itemsRepository;

    private String itemIconSmall = null;
    private String itemDescription = null;
    private String itemIconLarge = null;
    private String itemName = null;
    private String itemType = null;
    private String itemTodayTrend = null;
    private String itemTodayPrice = null;
    private String itemMembers = null;
    private String itemDay30Trend = null;
    private String itemDay30Change = null;
    private String itemDay90Trend = null;
    private String itemDay90Change = null;
    private String itemDay180Trend = null;
    private String itemDay180Change = null;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ModelAndView itemList() {
        List<Items> itemList = (itemsRepository).findAll();

        ModelAndView model = new ModelAndView("items");
        model.addObject("itemList", itemList);

        return model;
    }

    @RequestMapping(value = "/item/{ownItemID}", method = RequestMethod.GET)
    public ModelAndView showItem(@PathVariable Long ownItemID) throws Exception {

        Items item = itemsRepository.findOne(ownItemID);

        Long itemID = item.getRunescapeId();

        ModelAndView model = new ModelAndView("item");

        getInfoItem(itemID);

        DecimalFormat df = new DecimalFormat("#,###");
        String formattedPrice = df.format(ItemPrice.getItemPrice(itemID));

        boolean todayTrend = false, day30Trend = false, day90Trend = false, day180Trend = false;

        if (this.itemTodayTrend.equals("positive")) todayTrend = true;
        if (this.itemDay30Trend.equals("positive")) day30Trend = true;
        if (this.itemDay90Trend.equals("positive")) day90Trend = true;
        if (this.itemDay180Trend.equals("positive")) day180Trend = true;

        model.addObject("itemIconSmall", this.itemIconSmall);
        model.addObject("itemDescription", this.itemDescription);
        model.addObject("itemIconLarge", this.itemIconLarge);
        model.addObject("itemName", this.itemName);
        model.addObject("itemType", this.itemType);
        model.addObject("itemTodayTrend", todayTrend);
        model.addObject("itemTodayPrice", this.itemTodayPrice);
        model.addObject("itemMembers", this.itemMembers);
        model.addObject("itemDay30Trend", day30Trend);
        model.addObject("itemDay30Change", this.itemDay30Change);
        model.addObject("itemDay90Trend", day90Trend);
        model.addObject("itemDay90Change", this.itemDay90Change);
        model.addObject("itemDay180Trend", day180Trend);
        model.addObject("itemDay180Change", this.itemDay180Change);
        model.addObject("itemPrice", formattedPrice);
        model.addObject("itemExperience", item.getExperience());
        model.addObject("itemLevelNeeded", item.getLevelNeeded());
        model.addObject("itemSkill", item.getSkill());
        return model;
    }

    @RequestMapping(value = "/createItem", method = RequestMethod.GET)
    public String createItem(Model model) {
        model.addAttribute("itemsForm", new Items());

        return "createItem";
    }

    @RequestMapping(value = "/createItem", method = RequestMethod.POST)
    public String createItem(@ModelAttribute("itemsForm") Items itemForm, BindingResult bindingResult, Model model) throws Exception {
        getInfoItem(itemForm.getRunescapeId());

        String itemName = this.itemName;

        itemForm.setNameItem(itemName);
        itemsRepository.save(itemForm);

        return "redirect:/createItem";
    }

    private void getInfoItem(Long itemId) throws Exception {
        JSONParser parser = new JSONParser();

        URL link = new URL("http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=" + itemId);
        URLConnection conn = link.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;

        String fileName = "src/main/resources/json/" + itemId + ".json";
        File file = new File(fileName);

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        while ((inputLine = br.readLine()) != null) {
            bw.write(inputLine);
        }

        bw.close();
        br.close();

        Object obj = parser.parse(new FileReader("src/main/resources/json/" + itemId + ".json"));

        JSONObject jsonObject = (JSONObject) obj;

        this.itemIconSmall = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.icon");
        this.itemDescription = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.description");
        this.itemIconLarge = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.icon_large");
        this.itemName = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.name");
        this.itemType = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.type");
        this.itemTodayTrend = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.today.trend");
        this.itemTodayPrice = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.today.price");
        this.itemMembers = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.members");
        this.itemDay30Trend = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day30.trend");
        this.itemDay30Change = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day30.change");
        this.itemDay90Trend = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day90.trend");
        this.itemDay90Change = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day90.change");
        this.itemDay180Trend = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day180.trend");
        this.itemDay180Change = (String) JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day180.change");
    }
}
