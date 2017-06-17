package com.runescape.wave.model;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
@Entity
public class Member {
    @Transient
    private static final String UNKNOWN = "unknown";

    private Long id;
    private String name;

    @Column(length = 5000)
    private String biography;

    private String country;
    private String state;
    private String city;
    private Date dateOfBirth;
    private String gender;

    @Transient
    private String dob;
    @Transient
    private String cityStateCountry;

    public Member() {

    }

    public Member (String biography, String gender, String dob, String cityStateCountry) {
        this.biography = biography;
        this.gender = gender;
        this.dob = dob;
        this.cityStateCountry = cityStateCountry;
    }

    @Transient
    public String getDob() {
        return dob;
    }

    @Transient
    public void setDob(String dob) throws ParseException {
        String newDob = dob;

        if (dateOfBirth == null) newDob = UNKNOWN;
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateOfBirth.toString();
            java.util.Date d = dateFormat.parse(date);

            dateFormat.applyPattern("MM/dd/yyy");
            LocalDate now = LocalDate.now();
            Period age = Period.between(dateOfBirth.toLocalDate(), now);
            newDob = dateFormat.format(d) + " (" + age.getYears() + ")";
        }

        this.dob = newDob;
    }

    @Transient
    public String getCityStateCountry() {
        return cityStateCountry;
    }

    @Transient
    public void setCityStateCountry(String city, String state, String country) {
        String cityStateCountry;

        String newCity;
        String newState;
        String newCountry;

        if (city == null) newCity = UNKNOWN;
        else newCity = city;

        if (state == null) newState = UNKNOWN;
        else newState = state;

        if (country == null) newCountry = UNKNOWN;
        else newCountry = country;

        if (city == null && state == null && country == null) cityStateCountry = UNKNOWN;
        else cityStateCountry = city + ", " + state + ", " + country;

        this.cityStateCountry = cityStateCountry;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
