package com.ffxiv_fansite.fansite.model;

import lombok.Getter;

@Getter
public enum Region {
    LA_NOSCEA("La Noscea"),
    THE_BLACK_SHROUD("The Black Shroud"),
    THANALAN("Thanalan"),
    COERTHAS("Coerthas"),
    MOR_DHONA("Mor Dhona"),
    ABALATHIAS_SPINE("Abalathia's Spine"),
    DRAVANIA("Dravania"),
    GYR_ABANIA("Gyr Abania"),
    HINGASHI("Hingashi"),
    OTHARD("Othard"),
    NORVRANDT("Norvrandt"),
    THE_NORTHERN_EMPTY("The Northern Empty"),
    ILSABARD("Ilsabard"),
    THE_SEA_OF_STARS("The Sea Of Stars"),
    THE_WORLD_UNSUNDERED("The World Unsundered"),
    YOK_TURAL("Yok Tural"),
    XAK_TURAL("Xak Tural"),
    UNLOST_WORLD("Unlost World"),
    QUESTION_MARKS("???");

    private final String name;

    Region(final String name) {
        this.name = name;
    }
}
