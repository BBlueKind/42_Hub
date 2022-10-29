package com.example.hackathon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @GET("profile")
    Call<Repo> listRepos();

    @POST("oauth/token")
    Call<Auth> sendCode(@Body SendData data);
}

