package com.leaning_machine.common.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.base.dto.TerminalLoginDto;
import com.leaning_machine.common.HttpClient;
import com.leaning_machine.common.api.CommonApi;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class CommonApiService {
    public static CommonApiService instance = getInstance();
    private String baseUrl;
    private volatile OkHttpClient okHttpClient;

    protected CommonApiService() {
    }

    static CommonApiService getInstance() {
        if (instance == null) {
            synchronized (CommonApiService.class) {
                if (instance == null) {
                    instance = new CommonApiService();
                }
            }
        }
        return instance;
    }


    public CommonApiService init(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    protected Gson getGson(){
        return new GsonBuilder()//
                .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
                .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                .registerTypeAdapter(Date.class, new DateSerializer())
                .serializeNulls().create();
    }

    public class DateSerializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }


    protected CommonApi getService(){
        if(okHttpClient == null){
            synchronized (this){
                if(okHttpClient == null) {
                    okHttpClient = HttpClient.instance.getDefaultInstance();
                }
            }
        }
        return new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build().create(CommonApi.class);
    }


    public Observable<TerminalAuthDto> terminalLogin(TerminalLoginDto terminal) {
        return getService().terminalLogin(terminal);
    }

    public Observable<List<ResourceDto>> terminalResources(int pageNo, int pageSize) {
        return getService().terminalResources( pageNo, pageSize);
    }
}
