# Android-Studio_RetrofitOnPTX
This project is fetch data from Taiwan PTX with API using Retrofit

>API Require & Library
>
>>okhttp  
>>Retrofit  document:https://square.github.io/retrofit/  
>>PTX html verification:https://motc-ptx-api-documentation.gitbook.io/motc-ptx-api-documentation/api-shi-yong/hmac  

>UI and Java Code
>>UI：activity_main.xml  
>>Java：MainActivity.java、HMAC_SHA1.java（used to encode the signature）,Viewpoint.java(Json object),APIService.java(Interface)  

![image](https://user-images.githubusercontent.com/41913354/168557860-85aac20c-7421-49b4-bc77-294fa17ea99e.png)

## AndroidManifest.xml
```
    //...
    <uses-permission android:name="android.permission.INTERNET"/>
    //...
-------------------------------------------------------------------------------------------
dependencies {
    //...
    //Okhttp
    implementation 'com.squareup.okhttp3:okhttp:4.7.2'
    implementation 'com.squareup.okhttp3:okhttp-urlconnection:4.7.2'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.7.2'

    //Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'

    //Retrofit-Gson
    implementation 'com.squareup.retrofit2:converter-gson:2.7.2'
    //...
}  
```

## MainActivity.java
```
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
```

## APIService.java(Interface)
```
public interface APIService {

    //Return Multiple Viewpoints
    @GET("ScenicSpot")
    Call<List<Viewpoint>> getViewpoints(@Query("$top") int top,
                                        @Query("$format") String format,
                                        @Header("Authorization") String Authorization,
                                        @Header("x-date") String date);
                                        
    //Return a Viewpoint
    @GET("ScenicSpot")
    Call<Viewpoint> getViewpoint(@Query("$top") int top,
                                 @Query("$format") String format,
                                 @Header("Authorization") String Authorization,
                                 @Header("x-date") String date);
}

```
