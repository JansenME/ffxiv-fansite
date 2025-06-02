package com.ffxiv_fansite.fansite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Crafter {
    private String baseClass;
    private String code;
    private String discipleOf;
    private Role role;
    private MajorCity guildLocation;
    private String imageName;

    public static Crafter mapCsvLineToCrafter(final String[] csvLine) {
        return new Crafter(
                csvLine[0],
                csvLine[1],
                csvLine[2],
                Role.getRoleByName(csvLine[3]),
                MajorCity.getMajorCityByName(csvLine[4]),
                csvLine[5]
        );
    }
}
