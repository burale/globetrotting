package com.example.globetrotting;

import com.example.globetrotting.model.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class GlobetrottingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobetrottingApplication.class, args);
	}

}
