package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum Zone {
    LIMSA_LOMINSA_UPPER_DECKS("Limsa Lominsa Upper Decks", Region.LA_NOSCEA),
    LIMSA_LOMINSA_LOWER_DECKS("Limsa Lominsa Lower Decks", Region.LA_NOSCEA),
    MIST("Mist", Region.LA_NOSCEA),
    WOLVES_DEN_PIER("Wolves' Den Pier", Region.LA_NOSCEA),
    MIDDLE_LA_NOSCEA("Middle La Noscea", Region.LA_NOSCEA),
    LOWER_LA_NOSCEA("Lower La Noscea", Region.LA_NOSCEA),
    EASTERN_LA_NOSCEA("Eastern La Noscea", Region.LA_NOSCEA),
    WESTERN_LA_NOSCEA("Western La Noscea", Region.LA_NOSCEA),
    UPPER_LA_NOSCEA("Upper La Noscea", Region.LA_NOSCEA),
    OUTER_LA_NOSCEA("Outer La Noscea", Region.LA_NOSCEA),
    NEW_GRIDANIA("New Gridania", Region.THE_BLACK_SHROUD),
    OLD_GRIDANIA("Old Gridania", Region.THE_BLACK_SHROUD),
    THE_LAVENDER_BEDS("The Lavender Beds", Region.THE_BLACK_SHROUD),
    CENTRAL_SHROUD("Central Shroud", Region.THE_BLACK_SHROUD),
    EAST_SHROUD("East Shroud", Region.THE_BLACK_SHROUD),
    SOUTH_SHROUD("South Shroud", Region.THE_BLACK_SHROUD),
    NORTH_SHROUD("North Shroud", Region.THE_BLACK_SHROUD),
    ULDAH_STEPS_OF_NALD("Ul'dah - Steps of Nald", Region.THANALAN),
    ULDAH_STEPS_OF_THAL("Ul'dah - Steps of Thal", Region.THANALAN),
    THE_GOBLET("The Goblet", Region.THANALAN),
    THE_GOLD_SAUCER("The Gold Saucer", Region.THANALAN),
    WESTERN_THANALAN("Western Thanalan", Region.THANALAN),
    CENTRAL_THANALAN("Central Thanalan", Region.THANALAN),
    EASTERN_THANALAN("Eastern Thanalan", Region.THANALAN),
    SOUTHERN_THANALAN("Southern Thanalan", Region.THANALAN),
    NORTHERN_THANALAN("Northern Thanalan", Region.THANALAN),
    FOUNDATION("Foundation", Region.COERTHAS),
    THE_PILLARS("The Pillars", Region.COERTHAS),
    THE_FIRMAMENT("The Firmament", Region.COERTHAS),
    EMPYREUM("Empyreum", Region.COERTHAS),
    COERTHAS_CENTRAL_HIGHLANDS("Coerthas Central Highlands", Region.COERTHAS),
    COERTHAS_WESTERN_HIGHLANDS("Coerthas Western Highlands", Region.COERTHAS),
    MOR_DHONA("Mor Dhona", Region.MOR_DHONA),
    THE_SEA_OF_CLOUDS("The Sea of Clouds", Region.ABALATHIAS_SPINE),
    AZYS_LLA("Azys Lla", Region.ABALATHIAS_SPINE),
    IDYLLSHIRE("Idyllshire", Region.DRAVANIA),
    THE_DRAVANIAN_FORELANDS("The Dravanian Forelands", Region.DRAVANIA),
    THE_DRAVANIAN_HINTERLANDS("The Dravanian Hinterlands", Region.DRAVANIA),
    THE_CHURNING_MISTS("The Churning Mists", Region.DRAVANIA),
    RHALGRS_REACH("Rhalgr's Reach", Region.GYR_ABANIA),
    THE_FRINGES("The Fringes", Region.GYR_ABANIA),
    THE_PEAKS("The Peaks", Region.GYR_ABANIA),
    THE_LOCHS("The Lochs", Region.GYR_ABANIA),
    KUGANE("Kugane", Region.HINGASHI),
    SHIROGANE("Shirogane", Region.HINGASHI),
    THE_RUBY_SEA("The Ruby Sea", Region.OTHARD),
    YANXIA("Yanxia", Region.OTHARD),
    THE_DOMAN_ENCLAVE("The Doman Enclave", Region.OTHARD),
    THE_AZIM_STEPPE("The Azim Steppe", Region.OTHARD),
    THE_CRYSTARIUM("The Crystarium", Region.NORVRANDT),
    LAKELAND("Lakeland", Region.NORVRANDT),
    EULMORE("Eulmore", Region.NORVRANDT),
    KHOLUSIA("Kholusia", Region.NORVRANDT),
    AMH_ARAENG("Amh Araeng", Region.NORVRANDT),
    IL_MHEG("Il Mheg", Region.NORVRANDT),
    THE_RAKTIKA_GREATWOOD("The Rak'tika Greatwood", Region.NORVRANDT),
    THE_TEMPEST("The Tempest", Region.NORVRANDT),
    OLD_SHARLAYAN("Old Sharlayan", Region.THE_NORTHERN_EMPTY),
    LABYRINTHOS("Labyrinthos", Region.THE_NORTHERN_EMPTY),
    GARLEMALD("Garlemald", Region.ILSABARD),
    RADZ_AT_HAN("Radz-at-Han", Region.ILSABARD),
    THAVNAIR("Thavnair", Region.ILSABARD),
    MARE_LAMENTORUM("Mare Lamentorum", Region.THE_SEA_OF_STARS),
    ULTIMA_THULE("Ultima Thule", Region.THE_SEA_OF_STARS),
    ELPIS("Elpis", Region.THE_WORLD_UNSUNDERED),
    TULIYOLLAL("Tuliyollal", Region.YOK_TURAL),
    URQOPACHA("Urqopacha", Region.YOK_TURAL),
    KOZAMAUKA("Kozama'uka", Region.YOK_TURAL),
    YAK_TEL("Yak T'el", Region.YOK_TURAL),
    SOLUTION_NINE("Solution Nine", Region.XAK_TURAL),
    SHAALOANI("Shaaloani", Region.XAK_TURAL),
    HERITAGE_FOUND("Heritage Found", Region.XAK_TURAL),
    LIVING_MEMORY("Living Memory", Region.UNLOST_WORLD),
    GANGOS("Gangos", Region.QUESTION_MARKS),
    THE_OMPHALOS("The Omphalos", Region.QUESTION_MARKS),
    UNNAMED_ISLAND("Unnamed Island", Region.QUESTION_MARKS);

    private final String name;
    private final Region region;

    Zone(final String name, final Region region) {
        this.name = name;
        this.region = region;
    }
}
