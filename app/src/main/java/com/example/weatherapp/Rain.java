
package com.example.weatherapp;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain implements Serializable
{

    @SerializedName("1h")
    @Expose
    public Double _1h;
    private final static long serialVersionUID = 612115455554327991L;

}
