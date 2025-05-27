package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum MajorCity {
    GRIDANIA("Gridania", Expansion.A_REALM_REBORN),
    UL_DAH("Ul'dah", Expansion.A_REALM_REBORN),
    LIMSA_LOMINSA("Limsa Lominsa", Expansion.A_REALM_REBORN),
    ISHGARD("Ishgard", Expansion.HEAVENSWARD),
    KUGANE("Kugane", Expansion.STORMBLOOD),
    THE_CRYSTARIUM("The Crystarium", Expansion.SHADOWBRINGERS),
    OLD_SHARLAYAN("Old Sharlayan", Expansion.ENDWALKER),
    TULIYOLLAL("Tuliyollal", Expansion.DAWNTRAIL);

    private final String name;
    private final Expansion expansion;

    MajorCity(final String name, final Expansion expansion) {
        this.name = name;
        this.expansion = expansion;
    }
}
