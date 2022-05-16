package com.clyde2034.retrofithttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String KEY="v0_BS-lG5yeP5dKsrDjLcLHkOqI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Header Authorization
        String APP_ID = "f0e004b33e2f4349916c2c09064323df";//Your APP_ID on PTX platform
        String APP_KEY = "v0_BS-lG5yeP5dKsrDjLcLHkOqI";//Your APP_KEY on PTX platform
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String now_time_format = dateFormat.format(calendar.getTime());
        String Signature="";
        try {
            Signature = HMAC_SHA1.Signature("x-date: " + now_time_format, APP_KEY);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        String sAuth = "hmac username=\"" + APP_ID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + Signature + "\"";
        //Header Authorization

        //Optional
        OkHttpClient okHttpClient=new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        //Step 1
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://ptx.transportdata.tw/MOTC/v2/Tourism/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)////Optional
                .build();

        //Step 2
        APIService apiService=retrofit.create(APIService.class);

        //Step 3
        Call<List<Viewpoint>> call=apiService.getViewpoints(5,"JSON",sAuth,now_time_format);

        //Step 4
        call.enqueue(new Callback<List<Viewpoint>>() {
            @Override
            public void onResponse(Call<List<Viewpoint>> call, Response<List<Viewpoint>> response) {
                for (Viewpoint viewpoint:response.body()){
                    Log.d("Result",viewpoint.getDescriptionDetail());
                }
            }

            @Override
            public void onFailure(Call<List<Viewpoint>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to Fetch Data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}