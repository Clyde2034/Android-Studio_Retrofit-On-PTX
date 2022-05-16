package com.clyde2034.retrofithttp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIService {

    @GET("ScenicSpot")
    Call<List<Viewpoint>> getViewpoints(@Query("$top") int top,
                                        @Query("$format") String format,
                                        @Header("Authorization") String Authorization,
                                        @Header("x-date") String date);

    @GET("ScenicSpot")
    Call<Viewpoint> getViewpoint(@Query("$top") int top,
                                 @Query("$format") String format,
                                 @Header("Authorization") String Authorization,
                                 @Header("x-date") String date);
}
