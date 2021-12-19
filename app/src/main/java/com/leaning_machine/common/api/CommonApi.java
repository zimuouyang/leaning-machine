package com.leaning_machine.common.api;

import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.CheckTask;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.base.dto.TerminalDetail;
import com.leaning_machine.base.dto.TerminalDetailDto;
import com.leaning_machine.base.dto.TerminalLoginDto;
import com.leaning_machine.common.CommonApiConstants;

import java.util.List;

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
    Observable<BaseDto> getApps(@Query(CommonApiConstants.PARAM_PAGE_NO) int pageNO,
                                                                 @Query(CommonApiConstants.PARAM_PAGE_SIZE) int pageSiz);

    @GET("terminal/detail")
    Observable<BaseDto<TerminalDetail>> getTerminalDetail();

    @GET("check/task/details")
    Observable<BaseDto<PageInfo<CheckTask>>> getCheckTasks(@Query(CommonApiConstants.PARAM_PAGE_NO) int pageNO,
                                                           @Query(CommonApiConstants.PARAM_PAGE_SIZE) int pageSiz);
}
