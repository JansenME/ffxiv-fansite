package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum MajorCity {
    GRIDANIA("Gridania", Expansion.A_REALM_REBORN, "gridania.png"),
    UL_DAH("Ul'dah", Expansion.A_REALM_REBORN, "uldah.png"),
    LIMSA_LOMINSA("Limsa Lominsa", Expansion.A_REALM_REBORN, "limsalominsa.png"),
    ISHGARD("Ishgard", Expansion.HEAVENSWARD, "ishgard.png"),
    KUGANE("Kugane", Expansion.STORMBLOOD, "kugane.png"),
    THE_CRYSTARIUM("The Crystarium", Expansion.SHADOWBRINGERS, "thecrystarium.png"),
    OLD_SHARLAYAN("Old Sharlayan", Expansion.ENDWALKER, "oldsharlayan.png"),
    TULIYOLLAL("Tuliyollal", Expansion.DAWNTRAIL, "tuliyollal.png");

    private final String name;
    private final Expansion expansion;
    private final String imageName;

    MajorCity(final String name, final Expansion expansion, final String imageName) {
        this.name = name;
        this.expansion = expansion;
        this.imageName = imageName;
    }

    public static MajorCity getMajorCityByName(final String name) {
        return switch (name) {
            case "Gridania" -> MajorCity.GRIDANIA;
            case "Ul'dah" -> MajorCity.UL_DAH;
            case "LimsaLominsa" -> MajorCity.LIMSA_LOMINSA;
            case "Ishgard" -> MajorCity.ISHGARD;
            case "Kugane" -> MajorCity.KUGANE;
            case "TheCrystarium" -> MajorCity.THE_CRYSTARIUM;
            case "OldSharlayan" -> MajorCity.OLD_SHARLAYAN;
            case "Tuliyollal" -> MajorCity.TULIYOLLAL;
            default -> null;
        };
    }
}
