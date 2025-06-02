package com.ffxiv_fansite.fansite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Gatherer {
    private String baseClass;
    private String code;
    private String discipleOf;
    private Role role;
    private MajorCity guildLocation;
    private String goods;
    private String imageName;

    public static Gatherer mapCsvLineToGatherer(final String[] csvLine) {
        return new Gatherer(
                csvLine[0],
                csvLine[1],
                csvLine[2],
                Role.getRoleByName(csvLine[3]),
                MajorCity.getMajorCityByName(csvLine[4]),
                csvLine[5],
                csvLine[6]
                );
    }
}
