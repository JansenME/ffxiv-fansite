package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum AccessoryType {
    FENDING("Fending"),
    HEALING("Healing"),
    AIMING("Aiming"),
    CASTING("Casting"),
    SLAYING("Slaying");

    private final String name;

    AccessoryType(final String name) {
        this.name = name;
    }

    public static AccessoryType getAccessoryTypeByName(final String name) {
        return switch (name) {
            case "Fending" -> AccessoryType.FENDING;
            case "Healing" -> AccessoryType.HEALING;
            case "Aiming" -> AccessoryType.AIMING;
            case "Casting" -> AccessoryType.CASTING;
            case "Slaying" -> AccessoryType.SLAYING;
            default -> null;
        };
    }
}
