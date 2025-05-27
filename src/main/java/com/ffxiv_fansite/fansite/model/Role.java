package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum Role {
    TANK("Tank"),
    HEALER("Healer"),
    MELEE_DPS("Melee DPS"),
    PHYSICAL_RANGED_DPS("Physical Ranged DPS"),
    MAGICAL_RANGED_DPS("Magical Ranged DPS");

    private final String name;

    Role(final String name) {
        this.name = name;
    }
}
