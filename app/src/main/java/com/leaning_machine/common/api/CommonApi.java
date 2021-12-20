package com.leaning_machine.common.api;

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
import com.leaning_machine.common.CommonApiConstants;
import com.leaning_machine.model.App;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

    @GET("app/download")
    Observable<ResponseBody> downloadAppFile(@Query(value = CommonApiConstants.PARAM_FILE_NAME, encoded = true) String fileName);

    @POST("check/record")
    Observable<BaseDto> checkRecord(@Body CheckRecord checkRecord);

}
