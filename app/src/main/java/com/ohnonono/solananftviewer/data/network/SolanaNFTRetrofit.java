package com.ohnonono.solananftviewer.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SolanaNFTRetrofit {
    public static SolanaNFTClient getRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://foghqik767.execute-api.us-east-1.amazonaws.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(SolanaNFTClient.class);
    }
}
