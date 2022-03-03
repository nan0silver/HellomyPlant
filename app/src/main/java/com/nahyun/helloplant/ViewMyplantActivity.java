package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nahyun.helloplant.MainActivity.email;

public class ViewMyplantActivity extends BottomNavigationActivity {

    TextView PlantNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_myplant);

        Intent intent_comefrom_addmyplant_page = getIntent();

        JSONObject plantDetailData = new JSONObject();
        String jsonString =
                intent_comefrom_addmyplant_page.getExtras().getString("plantDetailData");
        if (jsonString != null) {
            try {
                plantDetailData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String PlantNickName_string = intent_comefrom_addmyplant_page.getExtras().getString("PlantNickName");
        String WaterDrop_string = intent_comefrom_addmyplant_page.getExtras().getString("WaterDrop");
        String WaterPeriod_String = intent_comefrom_addmyplant_page.getExtras().getString("WateringPeriod");
        String FertilizingPeriod_String = intent_comefrom_addmyplant_page.getExtras().getString("FertilizingPeriod");
        String light_string = intent_comefrom_addmyplant_page.getExtras().getString("light");
        String PlantId_string = intent_comefrom_addmyplant_page.getExtras().getString("PlantId");

        /*String PlantNickName_string = null;
        String WaterDrop_string = null;
        String WaterPeriod_String = null;
        String FertilizingPeriod_String = null;

        try {
            PlantNickName_string = plantDetailData.getString("plantNickname");
            WaterDrop_string = plantDetailData.getString("waterDrop");
            WaterPeriod_String = plantDetailData.getString("wateringPeriod");
            FertilizingPeriod_String = plantDetailData.getString("fertilizingPeriod");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        PlantNickName = (TextView)findViewById(R.id.myplant_nickname_TextView);
        PlantNickName.setText(PlantNickName_string);

        ImageView waterdrop_ImageView = (ImageView)findViewById(R.id.waterdrop_ImageView);
        if(WaterDrop_string.equals("4")) {
            waterdrop_ImageView.setImageResource(R.drawable.water_drop_four);
        }
        else if (WaterDrop_string.equals("3")) {
            waterdrop_ImageView.setImageResource(R.drawable.water_drop_three);
        }
        else {
            waterdrop_ImageView.setImageResource(R.drawable.water_drop_two);
        }

        ImageView light_ImageView = (ImageView)findViewById(R.id.light_ImageView);
        if (light_string.charAt(0) == '낮') {
            light_ImageView.setImageResource(R.drawable.light_two);
        }
        else if (light_string.charAt(0) == '중') {
            light_ImageView.setImageResource(R.drawable.light_three);
        }
        else if (light_string.charAt(0) == '높') {
            light_ImageView.setImageResource(R.drawable.light_two);
        }
        else {
            light_ImageView.setImageResource(R.drawable.light_three);
        }

        TextView wateringperiod_TextView = (TextView)findViewById(R.id.view_wateringperiod_TextView);
        wateringperiod_TextView.setText(WaterPeriod_String);

        TextView fertilizingperiod_TextView = (TextView)findViewById(R.id.view_fertilizingperiod_TextView);
        fertilizingperiod_TextView.setText(FertilizingPeriod_String);

        ImageView plant_ImageView = (ImageView)findViewById(R.id.searching_plant_ImageView);
        byte[] byteArray_imageBitmap_viewmyplant = getIntent().getByteArrayExtra("image_bitmap_to_viewmyplant");
        Bitmap get_image_viewmyplant;
        get_image_viewmyplant = BitmapFactory.decodeByteArray(byteArray_imageBitmap_viewmyplant, 0, byteArray_imageBitmap_viewmyplant.length);
        plant_ImageView.setImageBitmap(get_image_viewmyplant);

        findViewById(R.id.modify_viewmyplant_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_modify_page = new Intent(ViewMyplantActivity.this, ModifyMyplantActivity.class);
                intent_modify_page.putExtra("plantDetailData", jsonString);
                intent_modify_page.putExtra("image_bitmap_to_addmyplant", byteArray_imageBitmap_viewmyplant);

                intent_modify_page.putExtra("PlantNickName", PlantNickName_string);
                intent_modify_page.putExtra("WaterDrop", WaterDrop_string);
                intent_modify_page.putExtra("WateringPeriod", WaterPeriod_String);
                intent_modify_page.putExtra("FertilizingPeriod", FertilizingPeriod_String);
                intent_modify_page.putExtra("light", light_string);
                intent_modify_page.putExtra("PlantId", PlantId_string);
                startActivity(intent_modify_page);
            }
        });

        findViewById(R.id.delete_viewmyplant_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Viewmyplant_delete(PlantId_string, email);
                System.out.println("delete viewmyplant email : " + email);
            }
        });

        findViewById(R.id.goto_viewmyplant_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_myplantlist_from_viewmyplant = new Intent(ViewMyplantActivity.this, MyplantListActivity.class);
                startActivity(intent_goto_myplantlist_from_viewmyplant);
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_search_plant;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_camera;
    }

    public void Viewmyplant_delete(String plantId, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println(token);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.@NotNull Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token).build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .client(client)
                .baseUrl("http://18.116.203.236:1234/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);

        Map<String, String> map = new HashMap<>();
        map.put("plantId", plantId);
        map.put("email", email);

        System.out.println("plantId = " + plantId + " email = " + email);

        Call<ResponseBody> call_delete = service.deleteFunc(map);
        call_delete.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println(String.valueOf(response.code()));

                    Toast.makeText(ViewMyplantActivity.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent_after_success_delete = new Intent(ViewMyplantActivity.this, MyplantListActivity.class);
                    startActivity(intent_after_success_delete);
                }
                else {
                    Log.v("ViewMyplantActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(ViewMyplantActivity.this, "code : " + String.valueOf(response.code()) + "\n 내 식물 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("ViewMyplantActivity", "Fail");
                Toast.makeText(ViewMyplantActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}