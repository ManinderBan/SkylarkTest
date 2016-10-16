package com.maninder.skylarktest.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * Retrofit 2.0 to get data from Server
 */
public class RestClient {

    /**
     * This is our main backend/server URL.
     */
    public static final String REST_API_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/";


    private static Retrofit s_retrofit;

    /**
     * Set 200 second time outs, after that retrofit get an error.
     */
    static {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(200, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        s_retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return s_retrofit.create(serviceClass);
    }
}
