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

    private List<Country> countriesList;
    private int newLimit;
    private Country originCountry;


    public List<String> findReachableCapitalCitiesByCountryName(String countryName, int limit) {
        Country country = findCountryByCountryName(countryName);
        System.out.println("FOUND Country = " + country.getCapitalCity() + ", Longitude = " + country.getLongitude()  + ", Latitude = " + country.getLatitude());
        List<String> reachableCapitalCities = findReachableCapitalCitiesByGivenCountryAndLimit(country, limit);
        return reachableCapitalCities;
    }

    public List<String> findReachableCapitalCitiesByGivenCountryAndLimit(Country country, int limit){
        countriesList = new ArrayList<>();
        originCountry = country;
        newLimit = limit; int i = 0;
        do {
            i++;
            country = findTheCountryOfClosestReachableCapitalCityByCountryAndLimit(country, newLimit);
        }while(countriesList.size()==i);

        List<String>reachableCities = new ArrayList<>();
        countriesList.stream()
                .forEach(x-> {
                    reachableCities.add(x.getCapitalCity());
                });

        return reachableCities;
    }

    public Country findTheCountryOfClosestReachableCapitalCityByCountryAndLimit(Country country, int limit) {
        Country closestCountry = null;
        List<Country>reachableCapitalCities = new ArrayList<>();
        Countries.getCountries().stream()
                .forEach(c -> {
                    int distance = calculateDistanceInKilometer(country.getLatitude(),
                            country.getLongitude(), c.getLatitude(), c.getLongitude());
                    if (distance < limit){
                        if (! country.equals(c) && ! originCountry.equals(c) && ! countriesList.contains(c)) {
                            c.setDistance(distance);
                            reachableCapitalCities.add(c);
                        }
                    }
                });
        if (reachableCapitalCities.size()>0){ 
            Collections.sort(reachableCapitalCities, new Country());
            closestCountry = reachableCapitalCities.get(0);
            countriesList.add(closestCountry);
            newLimit = limit - closestCountry.getDistance();
            reachableCapitalCities.stream()
                    .forEach(x -> x.setDistance(0));

        }



        return closestCountry;

    }

    public Country findCountryByCountryName(String countryName) {
        List<Country> countries = Countries.getCountries().stream()
                .filter(c -> c.getName().equalsIgnoreCase(countryName))
                .collect(Collectors.toList());
        return countries.get(0);
    }

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public int calculateDistanceInKilometer(double originLatitude, double originLongitude,
                                            double destinationLatitude, double destinationLongitude) {

        double latDistance = Math.toRadians(originLatitude - destinationLatitude);
        double lngDistance = Math.toRadians(originLongitude - destinationLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(originLatitude)) * Math.cos(Math.toRadians(destinationLatitude))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }
}
