package com.leaning_machine.common.api;

import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
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
    Observable<TerminalAuthDto> terminalLogin(@Body TerminalLoginDto terminal);

    @GET("terminal/resources")
    Observable<List<ResourceDto>> terminalResources(@Query(CommonApiConstants.PARAM_PAGE_NO) int pageNO,
                                                    @Query(CommonApiConstants.PARAM_PAGE_SIZE) int pageSiz);
}
