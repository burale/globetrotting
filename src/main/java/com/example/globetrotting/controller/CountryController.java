package com.example.globetrotting.controller;

import com.example.globetrotting.model.Country;
import com.example.globetrotting.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping("/all")
    public List<Country> getAllCities(){

        return countryService.findAll();
    }
    @RequestMapping("/all/city/{name}")
    public List<String>getReachableCapitalCities(@PathVariable String name){
        return countryService.findReachableCapitalCitiesByCountryName(name, 1000);
    }

}
