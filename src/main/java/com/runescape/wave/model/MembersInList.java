package com.runescape.wave.model;

/**
 * Created by Martijn Jansen on 6/14/2017.
 */
public class MembersInList {
    private String name;
    private String rank;
    private int experience;
    private int kills;

    public MembersInList(String name, String rank, int experience, int kills){
        this.name = name;
        this.rank = rank;
        this.experience = experience;
        this.kills = kills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
