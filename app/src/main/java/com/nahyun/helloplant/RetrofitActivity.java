package com.nahyun.helloplant;

import android.util.Log;

import com.google.android.gms.common.util.JsonUtils;
import java.lang.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nahyun.helloplant.SignupActivity.email;

public class RetrofitActivity {
    String TAG = "RetrofitActivity";

    public void main() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://3.12.148.142/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);

        String post_url = email;

    }
    /*@Override
    public void onClick(View v) {

    } */

}
