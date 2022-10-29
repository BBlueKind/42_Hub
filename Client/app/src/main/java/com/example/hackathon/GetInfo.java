package com.example.hackathon;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetInfo {
    public static String userName;

    public static void setUserName(String myString) {
        userName = myString;
    }

    public void reqUserInfo(String user1, String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://localhost:6000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequest service = retrofit.create(ApiRequest.class);
        Call<Auth> sendCode = service.sendCode();
        sendCode.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(@NonNull Call<Auth> call, Response<Auth> response)
            {
                if (!response.isSuccessful()) {
                    userName = "Error" + response.code();
                    //textViewResult.setText("Error" + response.code());
                    return ;
                }
                userName = response.body().token;
                //textViewResult.setText(response.body().get(0).login);
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t)
            {
                userName = t.getMessage();
                //textViewResult.setText(t.getMessage());
            }
        });
        System.out.println(userName);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + code)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://localhost:6000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequest service = retrofit.create(ApiRequest.class);
        Call<List<Repo>> repos = service.listRepos();
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, Response<List<Repo>> response)
            {
                if (!response.isSuccessful()) {
                    userName = "Error" + response.code();
                    //textViewResult.setText("Error" + response.code());
                    return ;
                }
                userName = response.body().get(0).login;
                //textViewResult.setText(response.body().get(0).login);
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t)
            {
                userName = t.getMessage();
                //textViewResult.setText(t.getMessage());
            }
        });
    }
}
