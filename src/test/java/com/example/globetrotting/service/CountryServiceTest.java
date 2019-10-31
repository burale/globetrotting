package com.example.globetrotting.service;

import com.example.globetrotting.model.Country;
import com.example.globetrotting.model.Region;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryServiceTest {
    private static Country country;
    private CountryService countryService = new CountryService();

    @BeforeAll
    static void initAll(){
        country = new Country("GBR", "London", "London", -0.126236, 51.5002);
    }


    @Test
    @DisplayName("Test calculateDistanceInKm method to check if it is calculating distance correctly for given two latitudes and two longitudes")
    public void testCalculateDistanceInKm(){
        assertEquals(343, countryService.calculateDistanceInKilometer(
                51.5002,
                -0.126236,
                48.8566,
                2.35097));
    }

    @Test
    @DisplayName("Test findCountryByCountryName method to check if a country object can be found by countryName ")
    public void testFindCountryByCountryName(){
        assertEquals(country, countryService.findCountryByCountryName("United Kingdom"));
    }

    @Test
    @DisplayName("Test findReachableCapitalCitiesByCountryAndLimit method make sure it returns number of capital cities that are reachable by country name and limit of 1000")
        public void testFindReachableCapitalCitiesByCountryAndLimit(){
        List<String> reachableCapitalCities = countryService.findReachableCapitalCitiesByGivenCountryAndLimit(country, 1000);
        assertTrue(reachableCapitalCities.size() > 0);
    }

    @Test
    @DisplayName("Test findReachableCountriesByCapitalCitiesAndLimit method make sure it returns all capital cities that are expected to be reachable by country name and limit")
    public void testFindReachableCapitalCityNamesByCountryAndLimit(){
        List<String>expectedCapitalCityNames = Arrays.asList("Brussels", "Amsterdam", "Luxembourg");
        List<String> reachableCapitalCities = countryService.findReachableCapitalCitiesByGivenCountryAndLimit(country, 1000);
        assertArrayEquals(expectedCapitalCityNames.toArray(), reachableCapitalCities.toArray());
    }

    @Test
    @DisplayName("Test findReachableCapitalCitiesByCountryAndLimit method make sure it returns number of capital cities that are reachable by country name and limit")
    public void testFindNoOfReachableCapitalCitiesByCountryAndLimit(){
        List<String> reachableCapitalCities = countryService.findReachableCapitalCitiesByGivenCountryAndLimit(country, 1000);
//        reachableCapitalCities.stream()
//                .forEach(c -> System.out.println(c));
        assertEquals(3, reachableCapitalCities.size());
    }


}