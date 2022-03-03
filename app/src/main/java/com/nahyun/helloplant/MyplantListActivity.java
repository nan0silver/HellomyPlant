package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

public class MyplantListActivity extends BottomNavigationActivity {

    public static ArrayList<MyplantListData> mp_arrayList;
    private MyplantListAdapter myplantListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplant_list);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        navigation_add.setSelectedItemId(R.id.action_home);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent ML_intent_camera = new Intent(MyplantListActivity.this, searchPlant.class);
                        //ML_intent_camera.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        break;
                    case R.id.action_talk:
                        Intent ML_intent_talk = new Intent(MyplantListActivity.this, NoticeBoardActivity.class);
                        //ML_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ML_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        mp_arrayList = new ArrayList<>();
        //====recycler View code====//
        recyclerView = (RecyclerView)findViewById(R.id.myplant_list_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        System.out.println("mp_arrayList first : " + mp_arrayList.size());

        myplantListAdapter = new MyplantListAdapter(mp_arrayList);
        myplantListAdapter.setOnItemClickListener(new MyplantListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String p = Integer.toString(position);
                //Toast.makeText(MyplantListActivity.this, p, Toast.LENGTH_SHORT).show();
                //여기서 Toast를 삭제하고 startActivity를 실행, activity 이름은 position기반

                //Bitmap image, String name, String water, String fertilizer, String id
                MyplantListData myplantListData_clicked = null;
                myplantListData_clicked = mp_arrayList.get(Integer.parseInt(p));
                String clicked_id =  myplantListData_clicked.getMyplant_list_id();
                Bitmap clicked_image = myplantListData_clicked.getMyplant_list_image();
                String clicked_nickname = myplantListData_clicked.getMyplant_list_name();
                String clicked_water_cycle = myplantListData_clicked.getMyplant_list_water();
                String clicked_fertilizer_cycle = myplantListData_clicked.getMyplant_list_fertilizer();
                String clicked_light = "중중";

                String clicked_water_drop = "";
                int water_cycle = Integer.parseInt(clicked_water_cycle);
                if (water_cycle >=0 && water_cycle < 7) clicked_water_drop = "4";
                else if (water_cycle >=7 && water_cycle < 14) clicked_water_drop = "3";
                else if (water_cycle >= 14) clicked_water_drop = "2";
                else clicked_water_drop = "2";

                Intent intent_goto_viewmyplant = new Intent(MyplantListActivity.this, ViewMyplantActivity.class);
                intent_goto_viewmyplant.putExtra("PlantNickName", clicked_nickname);
                intent_goto_viewmyplant.putExtra("WaterDrop", clicked_water_drop);
                intent_goto_viewmyplant.putExtra("WateringPeriod", clicked_water_cycle);
                intent_goto_viewmyplant.putExtra("FertilizingPeriod", clicked_fertilizer_cycle);
                intent_goto_viewmyplant.putExtra("light", clicked_light);
                intent_goto_viewmyplant.putExtra("PlantId", clicked_id);

                ByteArrayOutputStream stream_clicked = new ByteArrayOutputStream();
                clicked_image.compress(Bitmap.CompressFormat.JPEG, 100, stream_clicked);
                byte[] byteArray_clicked = stream_clicked.toByteArray();

                intent_goto_viewmyplant.putExtra("image_bitmap_to_viewmyplant", byteArray_clicked);

                JSONObject plantDetailData = new JSONObject();
                intent_goto_viewmyplant.putExtra("plantDetailData", plantDetailData.toString());

                startActivity(intent_goto_viewmyplant);
            }
        });
        recyclerView.setAdapter(myplantListAdapter);

        //====myplant server connection code ======//
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

        Call<RetrofitGetData> call_get = service.getFunc(email);
        System.out.println("myplantlist email = " + email);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MyplantListActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("잠시만 기다려주세요..!");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        progressDialog.show();

        call_get.enqueue(new Callback<RetrofitGetData>() {
            @Override
            public void onResponse(Call<RetrofitGetData> call, Response<RetrofitGetData> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    response.body();
                    List<MyPlant> plantList = response.body().getMyPlantList();
                    int list_count = plantList.size();
                    System.out.println("myplantlist count of plant = " +list_count);

                    for (MyPlant plant: plantList ) {
                        MyplantListData mld1 = null;
                        String after_id = plant.getId();
                        String after_image = plant.getImage();
                        String after_name = plant.getNickname();
                        String after_water_cycle = plant.getWaterCycle();
                        String after_fertilizer_cycle = plant.getFertilizerCycle();
                        String after_light = plant.getLight();

                        Bitmap after_image_bitmap = null;
                        try {
                            byte[] byte_array_image = Base64.decode(after_image, Base64.DEFAULT);
                            after_image_bitmap = BitmapFactory.decodeByteArray(byte_array_image, 0, byte_array_image.length);}
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        mld1 = new MyplantListData(after_image_bitmap, after_name, after_water_cycle, after_fertilizer_cycle, after_id, after_light);

                        add_arraylist(mld1);

                        System.out.println("goooood!!! \nafter_id = " + after_id + " after_image = " + after_image + " after_name = " + after_name
                        + " after_water_cycle = " + after_water_cycle + " after_fertilizer_cycle = " + after_fertilizer_cycle);
                    }

                    Log.v("MyplantListActivity", "code = " + String.valueOf(response.code()));
                    //Toast.makeText(MyplantListActivity.this, "code = " + String.valueOf(response.code()) + "\nmyplant list를 불러오는데 성공하였습니다.", Toast.LENGTH_SHORT).show();

                    myplantListAdapter.updateMyplantListItems(mp_arrayList);
                    System.out.println("update " + mp_arrayList);
                }
                else {
                    Log.v("MyplantListActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(MyplantListActivity.this,  "\n 내 식물 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitGetData> call, Throwable t) {
                progressDialog.dismiss();
                Log.v("MyplantListActivity", "Fail");
                Toast.makeText(MyplantListActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Plant> plantList = call_get.execute().body().getPlants();

                    int list_count = plantList.size();
                    System.out.println("myplantlist count of plant = " +list_count);

                    for (Plant plant: plantList ) {
                        MyplantListData mld1 = null;
                        String after_id = plant.getMyPlant().getId();
                        String after_image = plant.getMyPlant().getImage();
                        String after_name = plant.getMyPlant().getNickname();
                        String after_water_cycle = plant.getMyPlant().getWaterCycle();
                        String after_fertilizer_cycle = plant.getMyPlant().getFertilizerCycle();

                        Bitmap after_image_bitmap = null;
                        try {
                        byte[] byte_array_image = Base64.decode(after_image, Base64.DEFAULT);
                        after_image_bitmap = BitmapFactory.decodeByteArray(byte_array_image, 0, byte_array_image.length);}
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        mld1 = new MyplantListData(after_image_bitmap, after_name, after_water_cycle, after_fertilizer_cycle, after_id);

                        add_arraylist(mld1);

                        System.out.println("goooood!!! \nafter_id = " + after_id + " after_image = " + after_image + " after_name = " + after_name
                                + " after_water_cycle = " + after_water_cycle + " after_fertilizer_cycle = " + after_fertilizer_cycle);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //==== add list to recycler view =====//
        MyplantListData sample1 = null;

        Bitmap sample1_image = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test_image);
        //sample1 = new MyplantListData(sample1_image, "test", null, null, "123456");

        //mp_arrayList.add(sample1);
        System.out.println("mp_arrayList second : " + mp_arrayList.size());

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_myplant_list;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_home;
    }


    public static void add_arraylist(MyplantListData myplantListData) {

        mp_arrayList.add(myplantListData);
        System.out.println("mp_arrayList third : " + mp_arrayList.size());
        System.out.println("myplantListData = " + myplantListData);

    }
}