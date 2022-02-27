package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nahyun.helloplant.MainActivity.email;

public class NoPlantinformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_plantinformation);

        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.action_camera);
        navigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        break;
                    case R.id.action_home:
                        Intent NP_intent_home = new Intent(NoPlantinformationActivity.this, MyplantListActivity.class);
                        //NP_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NP_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent NP_intent_talk = new Intent(NoPlantinformationActivity.this, NoticeBoardActivity.class);
                        //NP_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NP_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        Intent intent_comefrom_searchPlant = getIntent();
        String scientific_name = intent_comefrom_searchPlant.getExtras().getString("ScientificName");

        TextView searching_noinfo_plant_name = (TextView)findViewById(R.id.searching_noinfo_plant_name);
        searching_noinfo_plant_name.setText(scientific_name);

        ImageView plant_ImageView = (ImageView)findViewById(R.id.searching_noinfo_plant_ImageView);
        byte[] byteArray_imageBitmap = getIntent().getByteArrayExtra("PlantImage");
        Bitmap get_image;
        get_image = BitmapFactory.decodeByteArray(byteArray_imageBitmap, 0, byteArray_imageBitmap.length);
        plant_ImageView.setImageBitmap(get_image);

        String image_string = Base64.encodeToString(byteArray_imageBitmap, Base64.DEFAULT);

        JSONObject plantDetailData = new JSONObject();
        try {
            plantDetailData.put("name", scientific_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button addmyplantButton = (Button)findViewById(R.id.addmyplantButton);
        addmyplantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_addmyplant = new Intent(NoPlantinformationActivity.this, AddMyplantActivity.class);

                intent_goto_addmyplant.putExtra("plantDetailData", plantDetailData.toString());
                intent_goto_addmyplant.putExtra("image_bitmap_to_addmyplant", byteArray_imageBitmap);
                startActivity(intent_goto_addmyplant);
            }
        });

        //plant name copy code
        ImageButton copy_Button = (ImageButton)findViewById(R.id.searching_plant_name_copy_Button);
        copy_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("plant_name",scientific_name);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(NoPlantinformationActivity.this, "식물 이름이 복사되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.asking_plantinformation_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNoticeboard_post(image_string, scientific_name);
            }
        });
    }

    public void AddNoticeboard_post(String image, String scientific_name) {

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
        map.put("image", image);
        map.put("scientific_name", scientific_name);

        System.out.println("image" + image + "scientific_name = " + scientific_name);


        Call<Retrofit_infoplant_PostData> call_infoplant_post = service.post_infoplant_Func(map);
        System.out.println("addmyplant email = " + email);
        call_infoplant_post.enqueue(new Callback<Retrofit_infoplant_PostData>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<Retrofit_infoplant_PostData> call, Response<Retrofit_infoplant_PostData> response) {
                if(response.isSuccessful()) {

                    response.body();
                    String after_message = response.body().getMessage();
                    String after_image = response.body().getInfoPlant().getImage();
                    String after_scientific_name = response.body().getInfoPlant().getScientificName();
                    List<String> after_necessary = response.body().getInfoPlant().getNecessary();
                    String after_createdAt = response.body().getInfoPlant().getCreatedAt();
                    String after_updatedAt = response.body().getInfoPlant().getUpdatedAt();

                    Log.v("AddMyplantActivity", "message = " + after_message
                            + "\nscientific_name = " + after_scientific_name
                            + "\nimage = " + after_image
                            + "\nnecessary = " + after_necessary
                            +"\ncreatedAt = " + after_createdAt
                            +"\nupdatedAt = " + after_updatedAt
                            + "\ncode = " + String.valueOf(response.code()));

                    Toast.makeText(NoPlantinformationActivity.this, "내 식물이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.v("NoPlantinformationActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(NoPlantinformationActivity.this, "error : " + String.valueOf(response.code()) + "\n 식물 정보 요청에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<Retrofit_infoplant_PostData> call, Throwable t) {
                Log.v("NoPlantinformationActivity", "Fail");
                Toast.makeText(NoPlantinformationActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}