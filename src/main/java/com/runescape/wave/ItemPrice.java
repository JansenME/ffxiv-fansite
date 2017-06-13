package com.runescape.wave;

import com.github.wnameless.json.flattener.JsonFlattener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
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

    public static BigDecimal getItemPrice(Long itemID) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        URL link = new URL("http://services.runescape.com/m=itemdb_rs/api/graph/" + itemID + ".json");
        URLConnection conn = link.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;

        String fileName = "src/main/resources/json/" + itemID + "-price.json";
        File file = new File(fileName);

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        Object obj;
        JSONObject jsonObject;
        Map<String, Object> flattenedJsonMap;
        SortedSet<String> keys;
        BigDecimal price;
        try (BufferedWriter bw = new BufferedWriter(fw)) {

            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine);
            }

            bw.close();
        }
        br.close();

        obj = parser.parse(new FileReader("src/main/resources/json/" + itemID + "-price.json"));

        jsonObject = (JSONObject) obj;

        flattenedJsonMap = JsonFlattener.flattenAsMap(jsonObject.toString());

        keys = new TreeSet<>(flattenedJsonMap.keySet());

        price = (BigDecimal) flattenedJsonMap.get(keys.last());

        return price;
    }
}
