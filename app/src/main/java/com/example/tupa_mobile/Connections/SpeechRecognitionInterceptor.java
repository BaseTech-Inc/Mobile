package com.example.tupa_mobile.Connections;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

public class SpeechRecognitionInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Headers.Builder builder = new Headers.Builder();
        builder.add("Authorization", "5b3ce3597851110001cf6248c6376f7864c04686a9863d79cd62f1a6");
        builder.add("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8");
        builder.add("Content-Type", "application/json; charset=utf-8");
        return chain.proceed(
                chain.request().newBuilder()
                        .headers(builder.build())
                        .build()
        );
    }
}