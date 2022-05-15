package com.ohnonono.solananftviewer.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class S3Retrofit {
    public static S3Client getRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://localhost/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(S3Client.class);
    }
}
