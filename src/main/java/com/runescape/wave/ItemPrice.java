package com.runescape.wave;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
public class ItemPrice {
    private ItemPrice() {
    }

    public static BigDecimal getItemPrice(Long itemID) throws IOException {
        URL link = new URL("http://services.runescape.com/m=itemdb_rs/api/graph/" + itemID + ".json");
        URLConnection conn = link.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(br);

        JsonObject jsonObject = (JsonObject) root;

        Map<String, Object> flattenedJsonMap = JsonFlattener.flattenAsMap(jsonObject.toString());
        SortedSet<String> keys = new TreeSet<>(flattenedJsonMap.keySet());
        return (BigDecimal) flattenedJsonMap.get(keys.last());
    }
}
