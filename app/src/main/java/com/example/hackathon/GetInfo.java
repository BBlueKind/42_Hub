package com.example.hackathon;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetInfo {
    public void reqUserInfo(TextView textViewResult) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequest service = retrofit.create(ApiRequest.class);
        Call<List<Repo>> repos = service.listRepos();
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, Response<List<Repo>> response)
            {
                List<Repo> test = response.body();
                textViewResult.setText(test.get(0).login);
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t)
            {

            }
        });
    }
}
