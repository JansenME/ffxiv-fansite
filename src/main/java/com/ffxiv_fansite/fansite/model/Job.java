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

    public static Job mapCsvLineToJob(final String[] csvLine) {
        return new Job(
                csvLine[0],
                csvLine[1],
                csvLine[2],
                Role.getRoleByName(csvLine[3]),
                MajorCity.getMajorCityByName(csvLine[4]),
                Integer.parseInt(csvLine[5]),
                ArmorType.getArmorTypeByName(csvLine[6]),
                AccessoryType.getAccessoryTypeByName(csvLine[7]),
                Expansion.getExpansionByName(csvLine[8]),
                csvLine[9],
                csvLine[10]
        );
    }
}
