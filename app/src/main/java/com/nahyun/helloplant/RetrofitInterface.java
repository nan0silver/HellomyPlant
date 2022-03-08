package com.nahyun.helloplant;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
    Call<RetrofitPostData> postFunc(@Query("email") String email,
                              @FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @PUT("myplant")
    Call<RetrofitPutData> putFunc(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path="myplant", hasBody = true)
    Call<ResponseBody> deleteFunc(@FieldMap Map<String, String> options);

    @GET("infoplant/list")
    Call<Retrofit_infoplant_GetData> get_infoplant_Func(@Query("page") String page);

    @FormUrlEncoded
    @POST("infoplant")
    Call<Retrofit_infoplant_PostData> post_infoplant_Func(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @PUT("infoplant")
    Call<Retrofit_infoplant_PutData> put_infoplant_Func(@FieldMap Map<String, String> fields);

    @GET("plant")
    Call<Retrofit_plant_GetData> get_plant_Func(@Query("scientific_name") String scientific_name);

    @GET("plant")
    Call<Retrofit_plant_GetData> get_plant_myplantinfo_Func(@Query("scientific_name") String scientific_name);

}
