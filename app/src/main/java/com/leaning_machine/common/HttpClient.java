package com.leaning_machine.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClient {
    public static HttpClient instance = getInstance();
    private static final String SP_FILE_NAME = "client.config";

    private Context ctx;
    private SharedPreferences sp;
    private Gson gson;
    private Map<String, String> headerParams = new HashMap<>();
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public HttpClient init(Context context) {
        this.ctx = context;
        this.sp = ctx.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
        return this;
    }

    public boolean hasInit() {
        return ctx != null;
    }

    public OkHttpClient.Builder newClientBuilder(){
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return OK_HTTP_CLIENT.newBuilder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(mLoggingInterceptor)
                .readTimeout(20L, TimeUnit.SECONDS)
                .writeTimeout(20L,TimeUnit.SECONDS)
                .connectTimeout(20L, TimeUnit.SECONDS);
    }


    public OkHttpClient getDefaultInstance() {
        return newClientBuilder().build();
    }


    /**
     * Http client header interceptor
     */
    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder()
                    .addHeader("Content-Language", Locale.getDefault().toString());
            for (Map.Entry<String, String> headerEntry : headerParams.entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
            Request newRequest = requestBuilder.build();
            return chain.proceed(newRequest);
        }
    }

    public void addHeader(String name, String value){
        headerParams.put(name, value);
    }

    public void removeHeader(String name) {
        if(headerParams.containsKey(name)) {
            headerParams.remove(name);
        }
    }


}
