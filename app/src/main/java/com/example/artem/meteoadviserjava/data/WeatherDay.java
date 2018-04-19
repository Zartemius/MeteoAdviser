package com.example.artem.meteoadviserjava.data;

import com.google.gson.annotations.SerializedName;
import java.util.Calendar;
import java.util.List;

public class WeatherDay {

    public class WeatherTemp{
        Double temp;
        int humidity;
    }

    public class WeatherDescription{
        int id;
        String main;
    }

    public class WeatherWind{
        Double speed;
    }

    @SerializedName("main")
    private WeatherTemp mTemp;

    @SerializedName("weather")
    private List<WeatherDescription> mDescription;

    @SerializedName("name")
    private String mCity;

    @SerializedName("dt")
    private long mTimeStamp;

    @SerializedName("wind")
    private WeatherWind mWind;

    public WeatherDay(WeatherTemp temp, List<WeatherDescription> description,
                      WeatherWind wind, String city, long timeStamp){
        mTemp = temp;
        mDescription = description;
        mWind = wind;
        mCity = city;
        mTimeStamp = timeStamp;
    }

    public Calendar getDate(){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(mTimeStamp*1000);
        return date;
    }


    public String getmTempWithDegree(){
        return String.valueOf(mTemp.temp.intValue()) + "\u00B0";
    }

    public String getmCity(){
        return mCity;
    }

    public int getmIdOfWeather(){
        return mDescription.get(0).id;
    }

    public String getParameterOfWeather(){
        return mDescription.get(0).main;
    }

    public int getHumidity(){
        return mTemp.humidity;
    }

    public double getWindSpeed(){
        return mWind.speed;
    }
}
