package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum MinorCity {
    MOR_DHONA_REVENANTS_TOLL("Mor Dhona - Revenant's Toll", Expansion.A_REALM_REBORN),
    IDYLLSHIRE("Idyllshire", Expansion.HEAVENSWARD),
    RHALGRS_REACH("Rhalgr's Reach", Expansion.STORMBLOOD),
    EULMORE("Eulmore", Expansion.SHADOWBRINGERS),
    RADZ_AT_HAN("Radz-at-Han", Expansion.ENDWALKER),
    SOLUTION_NINE("Solution Nine", Expansion.DAWNTRAIL);

    private final String name;
    private final Expansion expansion;

    MinorCity(final String name, final Expansion expansion) {
        this.name = name;
        this.expansion = expansion;
    }
}
