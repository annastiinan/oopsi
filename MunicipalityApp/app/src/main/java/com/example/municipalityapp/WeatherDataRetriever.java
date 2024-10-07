package com.example.municipalityapp;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class WeatherDataRetriever {

    private final String API_KEY = "xxx";

    private final String CONVERTER_BASE_URL ="https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s";

    private final String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";

    public WeatherData getCurrentWeather(String municipality)  {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode areas;

            try {
                areas = objectMapper.readTree(new URL(String.format(CONVERTER_BASE_URL, municipality, API_KEY)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d("wdr", areas.toPrettyString());

            String latitude = areas.get(0).get("lat").toString();
            String longitude = areas.get(0).get("lon").toString();

            JsonNode weatherData;
            try {
                weatherData = objectMapper.readTree(new URL(String.format(WEATHER_BASE_URL, latitude, longitude, API_KEY)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d("wdr", weatherData.toPrettyString());

            return new WeatherData(
                    weatherData.get("name").asText(),
                    weatherData.get("weather").get(0).get("main").asText(),
                    weatherData.get("weather").get(0).get("description").asText(),
                    weatherData.get("main").get("temp").asText(),
                    weatherData.get("wind").get("speed").asText()
            );
        } catch (Exception e) {
            return null;
        }
    }
}
