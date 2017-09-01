package com.runescape.wave.service;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.runescape.wave.ItemPrice;
import com.runescape.wave.model.Items;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Martijn Jansen on 9/1/2017.
 */
public class ItemService {
    private static final String POSITIVE = "positive";

    private static String itemIconSmall;
    private static String itemDescription;
    private static String itemIconLarge;
    private static String itemName;
    private static String itemType;
    private static String itemTodayTrend;
    private static String itemTodayPrice;
    private static String itemMembers;
    private static String itemDay30Trend;
    private static String itemDay30Change;
    private static String itemDay90Trend;
    private static String itemDay90Change;
    private static String itemDay180Trend;
    private static String itemDay180Change;

    public static ModelAndView itemListService(List<Items> itemList) {
        Collections.sort(itemList, new Comparator<Items>() {
            @Override
            public int compare(final Items item1, final Items item2) {
                return item1.getNameItem().compareToIgnoreCase(item2.getNameItem());
            }
        });

        ModelAndView model = new ModelAndView("items");
        model.addObject("itemList", itemList);

        return model;
    }

    public static ModelAndView showItemService(final Items item) throws IOException {
        Long itemID = item.getRunescapeId();

        ModelAndView model = new ModelAndView("item");

        getInfoItemService(itemID);

        DecimalFormat df = new DecimalFormat("#,###");
        URL link = new URL("http://services.runescape.com/m=itemdb_rs/api/graph/" + itemID + ".json");
        String formattedPrice = df.format(ItemPrice.getItemPrice(link, itemID));

        boolean todayTrend = false;
        boolean day30Trend = false;
        boolean day90Trend = false;
        boolean day180Trend = false;

        if (itemTodayTrend.equals(POSITIVE)) todayTrend = true;
        if (itemDay30Trend.equals(POSITIVE)) day30Trend = true;
        if (itemDay90Trend.equals(POSITIVE)) day90Trend = true;
        if (itemDay180Trend.equals(POSITIVE)) day180Trend = true;

        String experience = item.getExperience().toString();

        if (experience.contains(".0")) {
            experience = experience.substring(0 , experience.length() - 2);
        }

        model.addObject("itemIconSmall", itemIconSmall);
        model.addObject("itemDescription", itemDescription);
        model.addObject("itemIconLarge", itemIconLarge);
        model.addObject("itemName", itemName);
        model.addObject("itemType", itemType);
        model.addObject("itemTodayTrend", todayTrend);
        model.addObject("itemTodayPrice", itemTodayPrice);
        model.addObject("itemMembers", itemMembers);
        model.addObject("itemDay30Trend", day30Trend);
        model.addObject("itemDay30Change", itemDay30Change);
        model.addObject("itemDay90Trend", day90Trend);
        model.addObject("itemDay90Change", itemDay90Change);
        model.addObject("itemDay180Trend", day180Trend);
        model.addObject("itemDay180Change", itemDay180Change);
        model.addObject("itemPrice", formattedPrice);
        model.addObject("itemExperience", experience);
        model.addObject("itemLevelNeeded", item.getLevelNeeded());
        model.addObject("itemSkill", item.getSkill());

        return model;
    }

    public static void getInfoItemService(Long itemId) throws IOException {
        URL link = new URL("http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=" + itemId);
        URLConnection conn = link.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(br);

        JsonObject jsonObject = root.getAsJsonObject();

        itemIconSmall = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.icon").toString();
        itemDescription = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.description").toString();
        itemIconLarge = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.icon_large").toString();
        itemName = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.name").toString();
        itemType = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.type").toString();
        itemTodayTrend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.today.trend").toString();
        itemTodayPrice = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.today.price").toString();
        itemMembers = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.members").toString();
        itemDay30Trend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day30.trend").toString();
        itemDay30Change = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day30.change").toString();
        itemDay90Trend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day90.trend").toString();
        itemDay90Change = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day90.change").toString();
        itemDay180Trend = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day180.trend").toString();
        itemDay180Change = JsonFlattener.flattenAsMap(jsonObject.toString()).get("item.day180.change").toString();
    }
}
