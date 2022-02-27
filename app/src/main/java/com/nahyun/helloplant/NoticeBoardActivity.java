package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nahyun.helloplant.MainActivity.email;

public class NoticeBoardActivity extends BottomNavigationActivity {

    public static ArrayList<NoticeBoardData> nb_arrayList;
    private NoticeBoardAdapter noticeBoardAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public String page = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        navigation_add.setOnItemSelectedListener(this);
        navigation_add.setSelectedItemId(R.id.action_talk);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent NB_intent_camera = new Intent(NoticeBoardActivity.this, searchPlant.class);
                        //NB_intent_camera.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NB_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        Intent NB_intent_home = new Intent(NoticeBoardActivity.this, MyplantListActivity.class);
                        //NB_intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NB_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        break;
                }
                return false;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.noticeboard_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        nb_arrayList = new ArrayList<>();
        noticeBoardAdapter = new NoticeBoardAdapter(nb_arrayList);
        noticeBoardAdapter.setOnItemClickListener(new NoticeBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String p = Integer.toString(position);
                //Toast.makeText(NoticeBoardActivity.this, p, Toast.LENGTH_SHORT).show();
                //여기서 Toast를 삭제하고 startActivity를 실행, activity 이름은 position기반
                Intent intent_goto_saveinformation = new Intent(NoticeBoardActivity.this, SaveInformationActivity.class);
                startActivity(intent_goto_saveinformation);
            }
        });
        recyclerView.setAdapter(noticeBoardAdapter);

        //=====recyclerView page maker=====//
        findViewById(R.id.noticeboard_page_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = "1";
            }
        });

        findViewById(R.id.noticeboard_page_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = "2";
            }
        });

        findViewById(R.id.noticeboard_page_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = "3";
            }
        });

        findViewById(R.id.noticeboard_page_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = "4";
            }
        });

        findViewById(R.id.noticeboard_page_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = "5";
            }
        });


        //====notice board server connection code ======//
        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println("Myplant list token = " + token);

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


        Call<Retrofit_infoplant_GetData> call_infoplant_get = service.get_infoplant_Func(page);
        System.out.println("infoplant page = " + page);

        call_infoplant_get.enqueue(new Callback<Retrofit_infoplant_GetData>() {
            @Override
            public void onResponse(Call<Retrofit_infoplant_GetData> call, Response<Retrofit_infoplant_GetData> response) {
                if (response.isSuccessful()) {
                    response.body();
                    String message = response.body().getMessage();

                    List<InfoPlant> plantList = response.body().getInfoPlantList();
                    int list_count = plantList.size();
                    System.out.println("notice board list count of plants = " +list_count);

                    for (InfoPlant plant: plantList ) {
                        NoticeBoardData nbd;
                        String after_id = plant.getId();
                        String after_image = plant.getImage();
                        String after_scientific_name = plant.getScientificName();
                        List<String> after_necessary = plant.getNecessary();

                        String after_family_name = ""; //1
                        if (!after_necessary.contains("family_name")) {after_family_name = plant.getFamilyName();}

                        String after_water_cycle = ""; //2
                        if (!after_necessary.contains("water_cycle")) {after_water_cycle = plant.getWaterCycle();}

                        String after_height = "";      //3
                        if (!after_necessary.contains("height")) {after_height = plant.getHeight();}

                        String after_place = "";       //4
                        if (!after_necessary.contains("place")) {after_place = plant.getPlace();}

                        String after_smell = "";       //5
                        if (!after_necessary.contains("smell")) {after_smell = plant.getSmell();}

                        String after_growth_speed = ""; //6
                        if (!after_necessary.contains("growth_speed")) {after_growth_speed = plant.getGrowthSpeed();}

                        String after_proper_temperature = ""; //7
                        if (!after_necessary.contains("proper_temperature")) {after_proper_temperature = plant.getProperTemperature();}

                        String after_pest = "";        //8
                        if (!after_necessary.contains("pest")) {after_pest = plant.getPest();}

                        String after_manage_level = ""; //9
                        if (!after_necessary.contains("manage_level")) {after_manage_level = plant.getManageLevel();}

                        String aftr_light = "";         //10
                        if (!after_necessary.contains("light")) {aftr_light = plant.getLight();}


                        Bitmap after_image_bitmap = null;
                        try {
                            byte[] byte_array_image = Base64.decode(after_image, Base64.DEFAULT);
                            after_image_bitmap = BitmapFactory.decodeByteArray(byte_array_image, 0, byte_array_image.length);}
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        nbd = new NoticeBoardData(after_image_bitmap, after_scientific_name);

                        add_arraylist(nbd);

                        System.out.println("goooood!!! \nafter_id = " + after_id
                                + " after_image = " + after_image
                                + " after_scientific_name = " + after_scientific_name
                                + " after_necessary = " + after_necessary);
                    }

                    Log.v("NoticeBoardActivity", "code = " + String.valueOf(response.code()));
                    Toast.makeText(NoticeBoardActivity.this, "code = " + String.valueOf(response.code()) + "\nmyplant list를 불러오는데 성공하였습니다.", Toast.LENGTH_SHORT).show();

                    noticeBoardAdapter.updateNoticeBoardItems(nb_arrayList);
                    System.out.println("update " + nb_arrayList);
                }
                else {
                    Log.v("MyplantListActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(NoticeBoardActivity.this, "error : " + String.valueOf(response.code()) + "\n 내 식물 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Retrofit_infoplant_GetData> call, Throwable t) {
                Log.v("MyplantListActivity", "Fail");
                Toast.makeText(NoticeBoardActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        NoticeBoardData sample1 = null;

        Bitmap sample1_image = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test_image);
        sample1 = new NoticeBoardData(sample1_image, "test");

        /*nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);
        nb_arrayList.add(sample1);*/

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_notice_board;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_talk;
    }

    public static void add_arraylist(NoticeBoardData noticeBoardData) {

        nb_arrayList.add(noticeBoardData);
        System.out.println("nb_arrayList subfunc : " + nb_arrayList.size());
        System.out.println("NoticeBoardData = " + noticeBoardData);

    }
}