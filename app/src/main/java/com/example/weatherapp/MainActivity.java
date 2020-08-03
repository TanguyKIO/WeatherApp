package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static String url = "https://api.openweathermap.org";
    public static String key = "31821c1f9bc48389f4dac02cf1829e32";
    public static String lat = "45.75";
    public static String lon = "5.85";
    public static String units = "metric";
    public static String exclusion = "minutes,hourly";
    public static TextView temperature;
    public static TextView refresh_txt;
    public static TextView[] forecast = new TextView[4];
    public static ImageView weatherImg;
    public static ImageView[] weatherImgs = new ImageView[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On associe ces views aux views que j'ai configuré dans activity_main.xml
        temperature = findViewById(R.id.current_temp);
        forecast[0] = findViewById(R.id.temp1);
        forecast[1] = findViewById(R.id.temp2);
        forecast[2] = findViewById(R.id.temp3);
        forecast[3] = findViewById(R.id.temp4);
        weatherImg = findViewById(R.id.current_weather);
        weatherImgs[0] = findViewById(R.id.weather1);
        weatherImgs[1] = findViewById(R.id.weather2);
        weatherImgs[2] = findViewById(R.id.weather3);
        weatherImgs[3] = findViewById(R.id.weather4);
        refresh_txt = findViewById(R.id.refresh_txt);
        getWeather(); //Fonction récupérant et affichant les données météo
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather();
            }
        }); //Permet d'actualiser la météo en appuyant sur le bouton refresh

    }

    void getWeather() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String actualisation = "Dernière actualisation : "+sdf.format(new Date());
        refresh_txt.setText(actualisation);
        Retrofit retrofit = new Retrofit.Builder() //Je construis ce qui me servira à appeler l'api
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherAPI weatherapi = retrofit.create(WeatherAPI.class);

        Call<WeatherData> call = weatherapi.getCurrentWeatherData(lat, lon, units, exclusion, key); //J'effectue un appel de l'api avec les paramètres que je souhaite
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) { //Méthode en cas de réponse de l'api
                SimpleDateFormat datef = new SimpleDateFormat("EEE d MM");
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                if (response.code() == 200) {
                    WeatherData weatherData = response.body();
                    assert weatherData != null;
                    String weather =  weatherData.current.weather.get(0).main; //Je récupère le temps courant
                    String tempe = weatherData.current.temp + "°C"; //je récupère la température courante
                    String prevision;
                    switch(weather){ //Selon le temps je change l'image à afficher
                        case "Clear":
                            weatherImg.setImageResource(R.drawable.clear_sky);
                            break;
                        case "Atmoshpere":
                            weatherImg.setImageResource(R.drawable.mist);
                            break;
                        case "Clouds":
                            weatherImg.setImageResource(R.drawable.broken_cloud);
                            break;
                        case "Snow":
                            weatherImg.setImageResource(R.drawable.snow);
                            break;
                        case "Thunderstorm":
                            weatherImg.setImageResource(R.drawable.thunderstrom);
                            break;
                        case "Rain":
                            weatherImg.setImageResource(R.drawable.rain);
                            break;
                        default:
                            break;
                    }
                    temperature.setText(tempe);
                    for(int i=1;i<=4;i++){ //Je recupère les données méteorologiues des 4 prochains jours
                        c.add(Calendar.DATE, 1);
                        weather=weatherData.daily.get(i).weather.get(0).main;
                        switch(weather){
                            case "Clear":
                                weatherImgs[i-1].setImageResource(R.drawable.clear_sky);
                                break;
                            case "Atmosphere":
                                weatherImgs[i-1].setImageResource(R.drawable.mist);
                                break;
                            case "broken clouds":
                                weatherImgs[i-1].setImageResource(R.drawable.broken_cloud);
                                break;
                            case "few clouds":
                                weatherImgs[i-1].setImageResource(R.drawable.few_clouds);
                                break;
                            case "Clouds":
                                weatherImgs[i-1].setImageResource(R.drawable.scattered_clouds);
                                break;
                            case "Snow":
                                weatherImgs[i-1].setImageResource(R.drawable.snow);
                                break;
                            case "Thunderstorm":
                                weatherImgs[i-1].setImageResource(R.drawable.thunderstrom);
                                break;
                            case "Drizzle":
                                weatherImgs[i-1].setImageResource(R.drawable.shower_rain);
                                break;
                            case "Rain":
                                weatherImgs[i-1].setImageResource(R.drawable.rain);
                                break;
                            default:
                                break;
                        }
                        prevision = datef.format(c.getTime()) +"     "+ weatherData.daily.get(i).temp.max + "°C";
                        forecast[i-1].setText(prevision);
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) { //méthode si l'appel de l'api échoue
                temperature.setText(t.getMessage());
            }
        });
    }

}  