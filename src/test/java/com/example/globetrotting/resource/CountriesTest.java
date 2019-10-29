package com.example.globetrotting.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CountriesTest {

    @Test
    @DisplayName("This is to test if json file can be read")
    public void readJsonFile(){
        Countries countries = new Countries();
        assertNotEquals(null, countries.readJsonFile());
    }

    @Test
    @DisplayName("This is to test if data is loaded to the list from json file")
    public void getCountries(){
        assertTrue(Countries.getCountries().size() > 0);
    }


}