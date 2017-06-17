package com.runescape.wave.controller;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.gson.*;
import com.runescape.wave.ItemPrice;
import com.runescape.wave.model.Items;
import com.runescape.wave.repository.ItemsRepository;

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

    private static final String POSITIVE = "positive";

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
    public ModelAndView showItem(@PathVariable Long ownItemID) throws IOException {

        Items item = itemsRepository.findOne(ownItemID);

        Long itemID = item.getRunescapeId();

        ModelAndView model = new ModelAndView("item");

        getInfoItem(itemID);

        DecimalFormat df = new DecimalFormat("#,###");
        String formattedPrice = df.format(ItemPrice.getItemPrice(itemID));

        boolean todayTrend = false;
        boolean day30Trend = false;
        boolean day90Trend = false;
        boolean day180Trend = false;

        if (this.itemTodayTrend.equals(POSITIVE)) todayTrend = true;
        if (this.itemDay30Trend.equals(POSITIVE)) day30Trend = true;
        if (this.itemDay90Trend.equals(POSITIVE)) day90Trend = true;
        if (this.itemDay180Trend.equals(POSITIVE)) day180Trend = true;

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
    public String createItem(@ModelAttribute("itemsForm") Items itemForm, BindingResult bindingResult, Model model) throws IOException {
        getInfoItem(itemForm.getRunescapeId());

        itemForm.setNameItem(this.itemName);
        itemsRepository.save(itemForm);

        return "redirect:/createItem";
    }

    private void getInfoItem(Long itemId) throws IOException {
        URL link = new URL("http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=" + itemId);
        URLConnection conn = link.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(br);

        JsonObject jsonObject = root.getAsJsonObject();

        this.itemIconSmall = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.icon").toString();
        this.itemDescription = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.description").toString();
        this.itemIconLarge = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.icon_large").toString();
        this.itemName = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.name").toString();
        this.itemType = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.type").toString();
        this.itemTodayTrend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.today.trend").toString();
        this.itemTodayPrice = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.today.price").toString();
        this.itemMembers = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.members").toString();
        this.itemDay30Trend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day30.trend").toString();
        this.itemDay30Change = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day30.change").toString();
        this.itemDay90Trend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day90.trend").toString();
        this.itemDay90Change = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day90.change").toString();
        this.itemDay180Trend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day180.trend").toString();
        this.itemDay180Change = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day180.change").toString();
    }
}
