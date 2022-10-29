package com.example.hackathon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("users")
    Call<List<Repo>> listRepos();
}

