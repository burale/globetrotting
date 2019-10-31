package com.example.globetrotting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Comparator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country implements Comparable<Country>, Comparator<Country> {

    private String id;
    private String name;
    private String capitalCity;
    private double longitude;
    private double latitude;
    private int distance;

    public Country(String id, String name, String capitalCity, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.capitalCity = capitalCity;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Country() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Country c) {
        if (this.distance == c.distance)
            return 1;
        return 0;
    }

    @Override
    public int compare(Country o1, Country o2) {
        return o1.distance - o2.distance;
    }
    @Override
    public boolean equals(Object o) {
        if(super.equals(o)) {
            return true;
        }
        if(!(o instanceof Country)) {
            return false;
        }
        Country otherCountry = (Country) o;
        return latitude == otherCountry.latitude && longitude == otherCountry.longitude;
    }


}
