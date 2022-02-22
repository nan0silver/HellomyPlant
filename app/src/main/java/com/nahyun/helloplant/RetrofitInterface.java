package com.nahyun.helloplant;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("myplant/list")
    Call<RetrofitGetData> getFunc(@Query("email") String email);

    @FormUrlEncoded
    @POST("myplant")
    Call<RetrofitPostData> postFunc(/*@Header("Authorization") String token,*/
                              @Query("email") String email,
                              @FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @PUT("myplant")
    Call<RetrofitPutData> putFunc(@FieldMap Map<String, String> fields);

    //@DELETE("myplant")
    //Call<DataClass> deleteFunc()


}
