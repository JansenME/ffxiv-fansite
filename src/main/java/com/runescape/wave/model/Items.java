package com.runescape.wave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
@Entity
public class Items {
    private Long id;
    private Long runescapeId;

    private String nameItem;

    private Long experience;
    private Long levelNeeded;
    private String skill;

    @Override
    public String toString() {
        return "<tr><td><a href='/item/" + id + "'>" + nameItem + "</a></td></tr>";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunescapeId() {
        return runescapeId;
    }

    public void setRunescapeId(Long runescapeId) {
        this.runescapeId = runescapeId;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Long getLevelNeeded() {
        return levelNeeded;
    }

    public void setLevelNeeded(Long levelNeeded) {
        this.levelNeeded = levelNeeded;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
