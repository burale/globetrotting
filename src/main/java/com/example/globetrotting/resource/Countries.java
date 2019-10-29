package com.example.globetrotting.resource;

import com.example.globetrotting.model.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Countries {

    private static List<Country>countries=null;
    public static List<Country>getCountries(){
        if (countries == null){
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Country>> typeReference = new TypeReference<>() {};

            try {
                InputStream inputStream = readJsonFile();
                if (inputStream != null)
                    countries = mapper.readValue(inputStream, typeReference);
            } catch (IOException e){
                System.out.printf(e.getMessage());
            }
        }
        return countries;
    }

    public static InputStream readJsonFile(){
        return TypeReference.class.getResourceAsStream("/countries.json");
    }
}
