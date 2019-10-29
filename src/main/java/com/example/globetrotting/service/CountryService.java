package com.example.globetrotting.service;

import com.example.globetrotting.model.Country;
import com.example.globetrotting.resource.Countries;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CountryService {
    public List<Country> findAll() {
        return Countries.getCountries();
    }

    private List<Country>reachableCountries = new ArrayList<>();


    public List<String> findReachableCapitalCitiesByCountryName(String countryName) {
        Country country = findCountryByCountryName(countryName);
        List<String> reachableCapitalCities = findReachableCapitalCitiesByCountryAndLimit(country, 1000);
        return reachableCapitalCities;
    }

    public List<String> findReachableCapitalCitiesByCountryAndLimit(Country country, int limit) {
        List<String>reachableCapitalCities = new ArrayList<>();
        Countries.getCountries().stream()
                .forEach(c -> {
                    int distance = calculateDistanceInKilometer(country.getLatitude(),
                            country.getLongitude(), c.getLatitude(), c.getLongitude());
                    if (distance < limit){
                        if (country.getLatitude() != c.getLatitude() && country.getLongitude() != c.getLatitude())
                            reachableCapitalCities.add(c.getCapitalCity());
                    }
                });
        return reachableCapitalCities;

    }

    public List<Country> findReachableCapitalCountriesByCountryAndLimit(Country country, int limit) {
        List<Country>reachableCapitalCities = new ArrayList<>();
        Countries.getCountries().stream()
                .forEach(c -> {
                    int distance = calculateDistanceInKilometer(country.getLatitude(),
                            country.getLongitude(), c.getLatitude(), c.getLongitude());
                    if (distance < limit){
                        if (country.getLatitude() != c.getLatitude() && country.getLongitude() != c.getLatitude()) {
                            c.setDistance(distance);
                            reachableCapitalCities.add(c);
                        }
                    }
                });
        if (reachableCapitalCities.size()>0){
            Collections.sort(reachableCapitalCities, new Country());
        }
        


        return reachableCapitalCities;

    }

    public Country findCountryByCountryName(String countryName) {
        List<Country> countries = Countries.getCountries().stream()
                .filter(c -> c.getName().equalsIgnoreCase(countryName))
                .collect(Collectors.toList());
        return countries.get(0);
    }

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public int calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }
}
