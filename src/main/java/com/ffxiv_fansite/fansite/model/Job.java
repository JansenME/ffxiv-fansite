package com.ffxiv_fansite.fansite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Job {
    private String baseClass;
    private String job;
    private String code;
    private Role role;
    private MajorCity startingLocation;
    private int startingLevel;
    private ArmorType armorType;
    private AccessoryType accessoryType;
    private Expansion expansion;
    private String baseClassImageName;
    private String jobImageName;
}
