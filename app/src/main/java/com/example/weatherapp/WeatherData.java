
package com.example.weatherapp;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//J'ai utilisé le site http://www.jsonschema2pojo.org avec le fichier json obtenu avec l'api openweathermap pour créer automaituqment mes classes pour récupérer les données météo
public class WeatherData implements Serializable
{

    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("timezone")
    @Expose
    public String timezone;
    @SerializedName("timezone_offset")
    @Expose
    public Integer timezoneOffset;
    @SerializedName("current")
    @Expose
    public Current current;
    @SerializedName("daily")
    @Expose
    public List<Daily> daily = null;
    private final static long serialVersionUID = -5439024665842519231L;

}
