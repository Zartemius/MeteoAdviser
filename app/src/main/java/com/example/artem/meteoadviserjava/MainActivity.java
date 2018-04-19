package com.example.artem.meteoadviserjava;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.artem.meteoadviserjava.data.WeatherDay;
import com.example.artem.meteoadviserjava.rest.ApiInterface;
import com.example.artem.meteoadviserjava.rest.WeatherAPI;
import com.example.artem.meteoadviserjava.tracker.GPSTracker;
import java.text.SimpleDateFormat;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private String TAG = "WEATHER";
    private TextView temperature;
    private ApiInterface api;
    private GPSTracker gps;
    private TextView humidity;
    private TextView nameOfLocation;
    private TextView descriptionOfWeather;
    private TextView wind;
    private TextView date;
    private ImageView buttonCurrentLocation;
    private ImageView buttonCityLondon;
    private ImageView buttonCityParis;
    private ImageView buttonCityNewYork;
    private ImageView buttonCityTokyo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >=21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.teal));
        }

        nameOfLocation = findViewById(R.id.activity_main_name_of_location);
        temperature = findViewById(R.id.activity_main_temperature);
        humidity = findViewById(R.id.activity_main_humidity);
        descriptionOfWeather = findViewById(R.id.activity_main_description_of_weather);
        wind = findViewById(R.id.activity_main_wind);
        buttonCurrentLocation = findViewById(R.id.activity_main_button_current_location);
        date = findViewById(R.id.activity_main_date);
        buttonCityLondon = findViewById(R.id.activity_main_button_london);
        buttonCityParis = findViewById(R.id.activity_main_button_paris);
        buttonCityNewYork = findViewById(R.id.activity_main_button_new_york);
        buttonCityTokyo = findViewById(R.id.activity_main_button_tokyo);

        api = WeatherAPI.getClient().create(ApiInterface.class);

        buttonCurrentLocation.setOnClickListener(new OnButtonCurrentLocationClicked());
        buttonCityLondon.setOnClickListener(new OnButtonLondonClicked());
        buttonCityParis.setOnClickListener(new OnButtonParisClicked());
        buttonCityNewYork.setOnClickListener(new OnButtonNewYorkClicked());
        buttonCityTokyo.setOnClickListener(new OnButtonTokyoClicked());

        getWeatherBasedOnCurrentLocation();

        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getWeatherBasedOnCurrentLocation();
                }
                return;
            }
        }
    }

    public void getWeatherBasedOnCurrentLocation(){

        double latitude = 0 ;
        double longitude = 0;

        gps = new GPSTracker(MainActivity.this);

        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }

        String units = "metric";
        String key = WeatherAPI.KEY;
        Log.d(TAG, "OK");

        Call<WeatherDay> callToday = api.getWeatherForCurrentLocation(latitude,longitude,units,key);

        callToday.enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {

                Log.i("works", "OnResponse");
                WeatherDay data = response.body();

                if(response.isSuccessful()){
                    setViews(data);
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                   Log.e(TAG, "OnFailure");
                   Log.e(TAG,t.toString());
            }
        });
    }

    public void getWeatherBasedOnIdOfCity(int idOfCity){

        String units = "metric";
        String key = WeatherAPI.KEY;
        Log.d(TAG, "OK");

        Call<WeatherDay> callToday = api.getWeatherForCertainCity(idOfCity,units,key);

        callToday.enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {

                Log.i("works", "OnResponse");
                WeatherDay data = response.body();

                if(response.isSuccessful()){
                    setViews(data);
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(TAG, "OnFailure");
                Log.e(TAG,t.toString());
            }
        });

    }


    public void setViews(WeatherDay data){
        nameOfLocation.setText(data.getmCity());
        temperature.setText(data.getmTempWithDegree());
        humidity.setText(data.getHumidity() + "%");
        descriptionOfWeather.setText(data.getParameterOfWeather());
        wind.setText(data.getWindSpeed() + " m/sec");
        SimpleDateFormat formatDataOfWeek = new SimpleDateFormat("EEEE dd MMMM", Locale.ENGLISH);
        String dayOfWeek = formatDataOfWeek.format(data.getDate().getTime());
        date.setText(dayOfWeek);
    }

    private class OnButtonCurrentLocationClicked implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            getWeatherBasedOnCurrentLocation();
        }
    }

    private class OnButtonLondonClicked implements View.OnClickListener{

        private final static int CITY_LONDON_ID = 2643741;

        @Override
        public void onClick(View view) {
            getWeatherBasedOnIdOfCity(CITY_LONDON_ID);
        }
    }

    private class OnButtonParisClicked implements View.OnClickListener{

        private final static int CITY_PARIS_ID = 2988507;

        @Override
        public void onClick(View view) {
            getWeatherBasedOnIdOfCity(CITY_PARIS_ID);
        }
    }

    private class OnButtonNewYorkClicked implements View.OnClickListener{

        private final static int CITY_NEW_YORK_ID = 5128581;

        @Override
        public void onClick(View view) {
            getWeatherBasedOnIdOfCity(CITY_NEW_YORK_ID);
        }
    }

    private class OnButtonTokyoClicked implements View.OnClickListener{

        private final static int CITY_TOKYO_ID = 1850147;

        @Override
        public void onClick(View view) {
            getWeatherBasedOnIdOfCity(CITY_TOKYO_ID);
        }
    }
}
