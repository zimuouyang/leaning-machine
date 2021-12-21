package com.leaning_machine.common.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.leaning_machine.base.dto.Announcement;
import com.leaning_machine.base.dto.AppDto;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.CheckRecord;
import com.leaning_machine.base.dto.CheckTask;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.base.dto.TerminalDetail;
import com.leaning_machine.base.dto.TerminalDetailDto;
import com.leaning_machine.base.dto.TerminalLoginDto;
import com.leaning_machine.base.dto.TopListResultModel;
import com.leaning_machine.common.HttpClient;
import com.leaning_machine.common.api.CommonApi;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
               return simpleDateFormat.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
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


    public Observable<BaseDto<String>> terminalLogin(TerminalLoginDto terminal) {
        return getService().terminalLogin(terminal);
    }

    public Observable<BaseDto<PageInfo<ResourceDto>>> terminalResources(int pageNo, int pageSize) {
        return getService().terminalResources( pageNo, pageSize);
    }

    public Observable<BaseDto<PageInfo<AppDto>>> getApps(int pageNo, int pageSize) {
        return getService().getApps( pageNo, pageSize);
    }

    public Observable<BaseDto<TerminalDetail>> getTerminalDetail() {
        return getService().getTerminalDetail();
    }

    public Observable<BaseDto<PageInfo<CheckTask>>> getCheckTasks(int pageNo, int pageSize) {
        return getService().getCheckTasks(pageNo, pageSize);
    }

    public Observable<ResponseBody> downloadAppFile(String fileName) {
        return getService().downloadAppFile(fileName);
    }

    public Observable<BaseDto> addRecord(CheckRecord checkRecord) {
        return getService().checkRecord(checkRecord);
    }

    public Observable<BaseDto<TopListResultModel>> getTopList() {
        return getService().getTopList();
    }

    public Observable<BaseDto<Announcement>> getAnnouncement() {
        return getService().getAnnouncement();
    }

    public Observable<BaseDto> logOut() {
        return getService().terminalLogout();
    }

    public Observable<BaseDto> sendSms(String phoneNumber) {
        return getService().sendSms(phoneNumber).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseDto<String>> checkCode(String phoneNumber, String code) {
        return getService().checkCode(phoneNumber, code).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
