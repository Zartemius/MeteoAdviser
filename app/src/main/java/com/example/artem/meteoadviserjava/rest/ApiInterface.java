package com.example.artem.meteoadviserjava.rest;

import com.example.artem.meteoadviserjava.data.WeatherDay;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather")
    Call<WeatherDay> getWeatherForCurrentLocation(@Query("lat") Double lat,
                                                  @Query("lon") Double lon,
                                                  @Query("units") String units,
                                                  @Query("appid") String appid);

    @GET("weather")
    Call<WeatherDay> getWeatherForCertainCity(@Query("id") int id,
                                              @Query("units") String units,
                                              @Query("appid") String appid);
}
