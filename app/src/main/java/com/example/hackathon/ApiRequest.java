package com.example.hackathon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @GET("users")
    Call<List<Repo>> listRepos();

    @POST("auth")
    Call<Auth> sendCode();
}

