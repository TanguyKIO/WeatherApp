package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("data/2.5/onecall?")
    Call<WeatherData> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("units") String units, @Query("exclude") String exclusion,  @Query("appid") String key);
}