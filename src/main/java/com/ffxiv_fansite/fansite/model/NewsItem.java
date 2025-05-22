package com.ffxiv_fansite.fansite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsItem {
    private String id;
    private String url;
    private String title;
    private String time;
    private String image;
    private String description;
}
