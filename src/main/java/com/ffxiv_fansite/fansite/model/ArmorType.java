package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum ArmorType {
    FENDING("Fending"),
    HEALING("Healing"),
    AIMING("Aiming"),
    CASTING("Casting"),
    MAIMING("Maiming"),
    STRIKING("Striking"),
    SCOUTING("Scouting");

    private final String name;

    ArmorType(final String name) {
        this.name = name;
    }

    public static ArmorType getArmorTypeByName(final String name) {
        return switch (name) {
            case "Fending" -> ArmorType.FENDING;
            case "Healing" -> ArmorType.HEALING;
            case "Aiming" -> ArmorType.AIMING;
            case "Casting" -> ArmorType.CASTING;
            case "Maiming" -> ArmorType.MAIMING;
            case "Striking" -> ArmorType.STRIKING;
            case "Scouting" -> ArmorType.SCOUTING;
            default -> null;
        };
    }
}
