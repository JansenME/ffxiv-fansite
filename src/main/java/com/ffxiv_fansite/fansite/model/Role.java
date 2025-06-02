package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum Role {
    TANK("Tank", "tank.png", "Tank"),
    HEALER("Healer", "healer.png", "Healer"),
    MELEE_DPS("Melee DPS", "meleedps.png", "Melee_DPS"),
    PHYSICAL_RANGED_DPS("Physical Ranged DPS", "physicalrangeddps.png", "Physical_Ranged_DPS"),
    MAGICAL_RANGED_DPS("Magical Ranged DPS", "magicalrangeddps.png", "Magical_Ranged_DPS");

    private final String name;
    private final String imageName;
    private final String frontendTitle;

    Role(final String name, final String imageName, final String frontendTitle) {
        this.name = name;
        this.imageName = imageName;
        this.frontendTitle = frontendTitle;
    }

    public static Role getRoleByName(final String name) {
        return switch (name) {
            case "Tank" -> Role.TANK;
            case "Healer" -> Role.HEALER;
            case "MeleeDPS" -> Role.MELEE_DPS;
            case "PhysicalRangedDPS" -> Role.PHYSICAL_RANGED_DPS;
            case "MagicalRangedDPS" -> Role.MAGICAL_RANGED_DPS;
            default -> null;
        };
    }
}
