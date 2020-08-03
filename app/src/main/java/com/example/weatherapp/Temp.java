
package com.example.weatherapp;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp implements Serializable
{

    @SerializedName("day")
    @Expose
    public Double day;
    @SerializedName("min")
    @Expose
    public Double min;
    @SerializedName("max")
    @Expose
    public Double max;
    @SerializedName("night")
    @Expose
    public Double night;
    @SerializedName("eve")
    @Expose
    public Double eve;
    @SerializedName("morn")
    @Expose
    public Double morn;
    private final static long serialVersionUID = -2213650473180929253L;

}
