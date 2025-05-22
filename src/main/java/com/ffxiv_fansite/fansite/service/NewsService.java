package com.ffxiv_fansite.fansite.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ffxiv_fansite.fansite.model.NewsItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    private final ConnectionService connectionService;

    @Autowired
    public NewsService(final ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public List<NewsItem> getNews() {
        return mapJsonNodeToNews(connectionService.getJsonNodeForNews());
    }

    private List<NewsItem> mapJsonNodeToNews(final JsonNode jsonNodeNews) {
        List<NewsItem> news = new ArrayList<>();

        if(jsonNodeNews != null && jsonNodeNews.isArray()) {
            for (JsonNode jsonNode : jsonNodeNews) {
                news.add(mapJsonnodeToNewsItem(jsonNode));
            }
        }

        return news;
    }

    private NewsItem mapJsonnodeToNewsItem(final JsonNode jsonNode) {
        return new NewsItem(
                jsonNode.get("id").asText(),
                jsonNode.get("url").asText(),
                jsonNode.get("title").asText(),
                mapDateToCorrectFormat(jsonNode.get("time").asText()),
                jsonNode.get("image").asText(),
                jsonNode.get("description").asText()
        );
    }

    private String mapDateToCorrectFormat(final String dateFromApi) {
        LocalDateTime dateTime = LocalDateTime.parse(StringUtils.chop(dateFromApi));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");

        return dateTime.format(formatter);
    }
}
