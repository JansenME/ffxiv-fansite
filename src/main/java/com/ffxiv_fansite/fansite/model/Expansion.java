package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum Expansion {
    A_REALM_REBORN("A Realm Reborn", "arealmreborn.png"),
    HEAVENSWARD("Heavensward", "heavensward.png"),
    STORMBLOOD("Stormblood", "stormblood.png"),
    SHADOWBRINGERS("Shadowbringers", "shadowbringers.png"),
    ENDWALKER("Endwalker", "endwalker.png"),
    DAWNTRAIL("Dawntrail", "dawntrail.png");

    private final String name;
    private final String imageName;

    Expansion(final String name, final String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public static Expansion getExpansionByName(final String name) {
        return switch (name) {
            case "ARealmReborn" -> Expansion.A_REALM_REBORN;
            case "Heavensward" -> Expansion.HEAVENSWARD;
            case "Stormblood" -> Expansion.STORMBLOOD;
            case "Shadowbringers" -> Expansion.SHADOWBRINGERS;
            case "Endwalker" -> Expansion.ENDWALKER;
            case "Dawntrail" -> Expansion.DAWNTRAIL;
            default -> null;
        };
    }
}
