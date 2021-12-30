package com.leaning_machine.common.api;

import com.leaning_machine.base.dto.Announcement;
import com.leaning_machine.base.dto.AppDto;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.CheckRecord;
import com.leaning_machine.base.dto.CheckTask;
import com.leaning_machine.base.dto.DownloadHistory;
import com.leaning_machine.base.dto.LearnTime;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.base.dto.TerminalDetail;
import com.leaning_machine.base.dto.TerminalDetailDto;
import com.leaning_machine.base.dto.TerminalLoginDto;
import com.leaning_machine.base.dto.TerminalSign;
import com.leaning_machine.base.dto.TopListResultModel;
import com.leaning_machine.common.CommonApiConstants;
import com.leaning_machine.model.App;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

public interface CommonApi {

    @POST("terminal/login")
    Observable<BaseDto<String>> terminalLogin(@Body TerminalLoginDto terminal);

    @GET("terminal/resources")
    Observable<BaseDto<PageInfo<ResourceDto>>> terminalResources(@Query(CommonApiConstants.PARAM_PAGE_NO) int pageNO,
                                                    @Query(CommonApiConstants.PARAM_PAGE_SIZE) int pageSiz);
    @GET("apps")
    Observable<BaseDto<PageInfo<AppDto>>> getApps(@Query(CommonApiConstants.PARAM_PAGE_NO) int pageNO,
                                                  @Query(CommonApiConstants.PARAM_PAGE_SIZE) int pageSiz);

    @GET("terminal/detail")
    Observable<BaseDto<TerminalDetail>> getTerminalDetail();

    @GET("check/task/details")
    Observable<BaseDto<PageInfo<CheckTask>>> getCheckTasks(@Query(CommonApiConstants.PARAM_PAGE_NO) int pageNO,
                                                           @Query(CommonApiConstants.PARAM_PAGE_SIZE) int pageSiz);

    @Streaming
    @POST("app/download")
    Call<ResponseBody> downloadAppFile(@Query(value = CommonApiConstants.PARAM_FILE_NAME, encoded = true) String fileName);

    @POST("check/record")
    Observable<BaseDto> checkRecord(@Body CheckRecord checkRecord);

    @GET("top/list")
    Observable<BaseDto<TopListResultModel>> getTopList();

    @GET("announcement/latest")
    Observable<BaseDto<Announcement>> getAnnouncement();

    @GET("terminal/signOut")
    Observable<BaseDto> terminalLogout();

    @GET("terminal/sendSms")
    Observable<BaseDto> sendSms(@Query(value = CommonApiConstants.PARAM_PHONE_NUM, encoded = true) String phoneNum);

    @GET("checkCode")
    Observable<BaseDto<String>> checkCode(@Query(value = CommonApiConstants.PARAM_PHONE_NUM) String phoneNum, @Query(value = CommonApiConstants.PARAM_CODE) String code);

    /**
     * 增加学习时长
     * @param learnTimes
     * @return
     */
    @POST("learn/time")
    Observable<BaseDto> addLearnTime(@Body List<LearnTime> learnTimes);

    @GET("learn/time")
    Observable<BaseDto<LearnTime>> getLearnTime(@Query(value = CommonApiConstants.PARAM_TIME) String learnTime);


    @GET("terminal/sign/latest")
    Observable<BaseDto<TerminalSign>> getLatestSign();

    @GET("download/history/{downloadId}")
    Observable<BaseDto> saveDownloadHistory(@Path("downloadId") long downloadId);

    @GET("download/histories")
    Observable<BaseDto<List<DownloadHistory>>> getDownloadHistories();
}
