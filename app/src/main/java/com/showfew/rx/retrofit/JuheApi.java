package com.showfew.rx.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author steven
 * @Date 2017/6/12 18:13
 */
public interface JuheApi {
    @GET("citys")
    Observable<AllCity> getAllCity(@Query("key")String key);
}
