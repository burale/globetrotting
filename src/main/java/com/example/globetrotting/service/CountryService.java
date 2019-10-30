package com.example.globetrotting.service;

import com.example.globetrotting.model.Country;
import com.example.globetrotting.resource.Countries;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {
    public List<Country> findAll() {
        return Countries.getCountries();
    }

    private LinkedHashSet<Country>countriesHashSet = new LinkedHashSet<>();
    private int newLimit;
    private Country originalCountry;


    public List<String> findReachableCapitalCitiesByCountryName(String countryName, int limit) {
        Country country = findCountryByCountryName(countryName);
        System.out.println("FOUND Country = " + country.getCapitalCity() + ", Longitude = " + country.getLongitude()  + ", Latitude = " + country.getLatitude());
        List<String> reachableCapitalCities = findReachableCapitalCitiesByGivenCountryAndLimit(country, limit);
        return reachableCapitalCities;
    }

    public List<String> findReachableCapitalCitiesByGivenCountryAndLimit(Country country, int limit){
        originalCountry = country;
        newLimit = limit; int i = 0;
        do {
            i++;
            country = findTheCountryOfClosestReachableCapitalCityByCountryAndLimit(country, newLimit);
        }while(countriesHashSet.size()==i);

        List<String>reachableCities = new ArrayList<>();
        countriesHashSet.stream()
                .forEach(x-> {
                    reachableCities.add(x.getCapitalCity());
                    System.out.println("CITY = " + x.getCapitalCity());
                });

        return reachableCities;
    }

    public Country findTheCountryOfClosestReachableCapitalCityByCountryAndLimit(Country country, int limit) {
        Country theCountry = null;
        List<Country>reachableCapitalCities = new ArrayList<>();
        Countries.getCountries().stream()
                .forEach(c -> {
                    int distance = calculateDistanceInKilometer(country.getLatitude(),
                            country.getLongitude(), c.getLatitude(), c.getLongitude());
                    if (distance < limit){
                        if (! country.equals(c) && ! originalCountry.equals(c) && ! countriesHashSet.contains(c)) {
                            c.setDistance(distance);
                            reachableCapitalCities.add(c);
                        }
                    }
                });
        if (reachableCapitalCities.size()>0){
            Collections.sort(reachableCapitalCities, new Country());
            theCountry = reachableCapitalCities.get(0);
            countriesHashSet.add(theCountry);
            newLimit = limit - theCountry.getDistance();
//            System.out.println("******************************************************");
//            System.out.println();
//            reachableCapitalCities.stream()
//                    .forEach(x -> System.out.println("CITY = " + x.getCapitalCity() + ", DISTANCE = " + x.getDistance()));
            reachableCapitalCities.stream()
                    .forEach(x -> x.setDistance(0));

        }



        return theCountry;

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
