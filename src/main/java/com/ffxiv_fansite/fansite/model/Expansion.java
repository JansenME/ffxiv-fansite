package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum Expansion {
    A_REALM_REBORN("A Realm Reborn"),
    HEAVENSWARD("Heavensward"),
    STORMBLOOD("Stormblood"),
    SHADOWBRINGERS("Shadowbringers"),
    ENDWALKER("Endwalker"),
    DAWNTRAIL("Dawntrail");

    private final String name;

    Expansion(final String name) {
        this.name = name;
    }
}
