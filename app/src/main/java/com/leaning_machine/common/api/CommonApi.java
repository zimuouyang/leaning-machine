package com.leaning_machine.common.api;

import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.base.dto.TerminalLoginDto;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface CommonApi {

    @POST("terminal/login/token")
    Observable<TerminalAuthDto> terminalLogin(@Body TerminalLoginDto terminal);

    @POST("terminal/resources")
    Observable<ResourceDto> terminalResources();
}
