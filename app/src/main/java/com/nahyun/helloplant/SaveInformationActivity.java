package com.nahyun.helloplant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaveInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_information);

        Intent intent_comefrom_noticeboard_page = getIntent();

        ImageView saveinformation_image = (ImageView)findViewById(R.id.saveinformation_image);
        byte[] byteArray_imageBitmap = getIntent().getByteArrayExtra("image");
        Bitmap get_image;
        get_image = BitmapFactory.decodeByteArray(byteArray_imageBitmap, 0, byteArray_imageBitmap.length);
        saveinformation_image.setImageBitmap(get_image);

        String scientific_name = intent_comefrom_noticeboard_page.getExtras().getString("scientific_name");
        TextView saveinformation_scientific_name_TextView = (TextView)findViewById(R.id.saveinformation_scientific_name_TextView);
        saveinformation_scientific_name_TextView.setText(scientific_name);

        String before_family_name = intent_comefrom_noticeboard_page.getExtras().getString("family_name");
        String before_water_cycle = intent_comefrom_noticeboard_page.getExtras().getString("water_cycle");
        String before_height = intent_comefrom_noticeboard_page.getExtras().getString("height");
        String before_place = intent_comefrom_noticeboard_page.getExtras().getString("place");
        String before_smell = intent_comefrom_noticeboard_page.getExtras().getString("smell");
        String before_growth_speed = intent_comefrom_noticeboard_page.getExtras().getString("growth_speed");
        String before_proper_temperature = intent_comefrom_noticeboard_page.getExtras().getString("proper_temperature");
        String before_pest = intent_comefrom_noticeboard_page.getExtras().getString("pest");
        String before_manage_level = intent_comefrom_noticeboard_page.getExtras().getString("manage_level");
        String before_light = intent_comefrom_noticeboard_page.getExtras().getString("light");

        if (before_family_name != "") {
            EditText family_name_edittext = (EditText) findViewById(R.id.saveinformation_family_name_EditText);
            family_name_edittext.setText(before_family_name);
        }

        if (before_height != "") {
            EditText height_edittext = (EditText) findViewById(R.id.saveinformation_height_EditText);
            height_edittext.setText(before_height);
        }

        if (before_place != "") {
            EditText place_edittext = (EditText) findViewById(R.id.saveinformation_place_EditText);
            place_edittext.setText(before_place);
        }

        if (before_pest != "") {
            EditText pest_edittext = (EditText) findViewById(R.id.saveinformation_pest_EditText);
            pest_edittext.setText(before_pest);
        }

        //bytearray to String (image)
        String image_string = android.util.Base64.encodeToString(byteArray_imageBitmap, Base64.DEFAULT);


        //====SPINNER CODE====//
        //---water cycle spinner---//
        Spinner saveinformation_water_cycle_Spinner = findViewById(R.id.saveinformation_water_cycle_Spinner);

        ArrayAdapter arrayAdapter_water_cycle = ArrayAdapter.createFromResource(this, R.array.saveinformation_water_cycle_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_water_cycle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saveinformation_water_cycle_Spinner.setAdapter(arrayAdapter_water_cycle);

        if (before_water_cycle != "") {
            int spinner_position_water_cycle = arrayAdapter_water_cycle.getPosition(before_water_cycle);
            saveinformation_water_cycle_Spinner.setSelection(spinner_position_water_cycle);
        }
        saveinformation_water_cycle_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---smell spinner---//
        Spinner saveinformation_smell_Spinner = findViewById(R.id.saveinformation_smell_Spinner);

        ArrayAdapter arrayAdapter_smell = ArrayAdapter.createFromResource(this, R.array.saveinformation_smell_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_smell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saveinformation_smell_Spinner.setAdapter(arrayAdapter_smell);

        if (before_smell != "") {
            int spinner_position_smell = arrayAdapter_smell.getPosition(before_smell);
            saveinformation_smell_Spinner.setSelection(spinner_position_smell);
        }
        saveinformation_smell_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---growth speed spinner---//
        Spinner saveinformation_growth_speed_Spinner = findViewById(R.id.saveinformation_growth_speed_Spinner);

        ArrayAdapter arrayAdapter_growth_speed = ArrayAdapter.createFromResource(this, R.array.saveinformation_growth_speed_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_growth_speed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saveinformation_growth_speed_Spinner.setAdapter(arrayAdapter_growth_speed);

        if (before_growth_speed != "") {
            int spinner_position_growth_speed = arrayAdapter_growth_speed.getPosition(before_growth_speed);
            saveinformation_growth_speed_Spinner.setSelection(spinner_position_growth_speed);
        }
        saveinformation_growth_speed_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---proper temperature spinner---//
        Spinner saveinformation_proper_temperature_Spinner = findViewById(R.id.saveinformation_proper_temperature_Spinner);

        ArrayAdapter arrayAdapter_proper_temperature = ArrayAdapter.createFromResource(this, R.array.saveinformation_proper_temperature_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_proper_temperature.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saveinformation_proper_temperature_Spinner.setAdapter(arrayAdapter_proper_temperature);

        if (before_proper_temperature != "") {
            int spinner_position_proper_temperature = arrayAdapter_proper_temperature.getPosition(before_proper_temperature);
            saveinformation_proper_temperature_Spinner.setSelection(spinner_position_proper_temperature);
        }
        saveinformation_proper_temperature_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---manage level spinner---//
        Spinner saveinformation_manage_level_Spinner = findViewById(R.id.saveinformation_manage_level_Spinner);

        ArrayAdapter arrayAdapter_manage_level = ArrayAdapter.createFromResource(this, R.array.saveinformation_manage_level_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_manage_level.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saveinformation_manage_level_Spinner.setAdapter(arrayAdapter_manage_level);

        if (before_manage_level != "") {
            int spinner_position_manage_level = arrayAdapter_manage_level.getPosition(before_manage_level);
            saveinformation_manage_level_Spinner.setSelection(spinner_position_manage_level);
        }
        saveinformation_manage_level_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---manage level spinner---//
        Spinner saveinformation_light_Spinner = findViewById(R.id.saveinformation_light_Spinner);

        ArrayAdapter arrayAdapter_light = ArrayAdapter.createFromResource(this, R.array.saveinformation_light_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_light.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saveinformation_light_Spinner.setAdapter(arrayAdapter_light);

        if (before_light != "") {
            int spinner_position_light = arrayAdapter_light.getPosition(before_light);
            saveinformation_light_Spinner.setSelection(spinner_position_light);
        }
        saveinformation_light_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.saveinformation_saving_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD infoplant PUT code

                String family_name = "";
                EditText saveinformation_family_name_EditText = (EditText)findViewById(R.id.saveinformation_family_name_EditText);
                if (saveinformation_family_name_EditText.getText().toString().length() == 0) { family_name = "선택 안함"; } //if null
                else { family_name = saveinformation_family_name_EditText.getText().toString(); }

                String korean_name = "";
                EditText saveinformation_korean_name_EditText = (EditText)findViewById(R.id.saveinformation_korean_name_EditText);
                if (saveinformation_korean_name_EditText.getText().toString().length() == 0) { korean_name = "선택 안함"; } //if null
                else { korean_name = saveinformation_korean_name_EditText.getText().toString(); }

                String water_cycle = "";
                water_cycle = saveinformation_water_cycle_Spinner.getSelectedItem().toString();

                String height = "";
                EditText saveinformation_height_EditText = (EditText)findViewById(R.id.saveinformation_height_EditText);
                if (saveinformation_height_EditText.getText().toString().length() == 0) { height = "선택 안함"; }
                else { height = saveinformation_height_EditText.getText().toString(); }

                String place = "";
                EditText saveinformation_place_EditText = (EditText)findViewById(R.id.saveinformation_place_EditText);
                if (saveinformation_place_EditText.getText().toString().length() ==0) { place = "선택 안함";}
                else { place = saveinformation_place_EditText.getText().toString(); }

                String smell = "";
                smell = saveinformation_smell_Spinner.getSelectedItem().toString();

                String growth_speed = "";
                growth_speed = saveinformation_growth_speed_Spinner.getSelectedItem().toString();

                String proper_temperature = "";
                proper_temperature = saveinformation_proper_temperature_Spinner.getSelectedItem().toString();

                String pest = "";
                EditText saveinformation_pest_EditText = (EditText)findViewById(R.id.saveinformation_pest_EditText);
                if (saveinformation_pest_EditText.getText().toString().length() == 0) { pest = "선택 안함";}
                else { pest = saveinformation_pest_EditText.getText().toString(); }

                String manage_level = "";
                manage_level = saveinformation_manage_level_Spinner.getSelectedItem().toString();

                String light = "";
                light = saveinformation_light_Spinner.getSelectedItem().toString();

                Log.v("SaveInformationActivity", "scientific name : " +scientific_name
                + "\nfamily name : " + family_name
                + "\nkorean name :" + korean_name
                + "\nwater cycle : " + water_cycle
                + "\nheight : " + height
                + "\nplace : " + place
                + "\nsmell : " + smell
                + "\ngrowth speed : " + growth_speed
                + "\nproper temperature : " + proper_temperature
                + "\npest : " + pest
                + "\nmanage level : " + manage_level
                + "\nlight : " + light);


                SaveInformation_put(image_string, scientific_name, family_name, korean_name, water_cycle, height, place, smell, growth_speed,
                        proper_temperature, pest, manage_level, light);
            }
        });
    }

    public void SaveInformation_put(String image,String scientific_name, String family_name, String korean_name, String water_cycle, String height, String place,
                                    String smell, String growth_speed, String proper_temperature, String pest, String manage_level,
                                    String light) {

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
        System.out.println("scientific_name : " + scientific_name);
        if (!family_name.equals("선택 안함")) {map.put("family_name", family_name); System.out.println("family_name : " + family_name);}
        if (!korean_name.equals("선택 안함")) {map.put("korean_name", korean_name); System.out.println("korean_name : " + korean_name);}
        if (!water_cycle.equals("선택 안함")) {map.put("water_cycle", water_cycle); System.out.println("water_cycle : " + water_cycle);}
        if (!height.equals("선택 안함")) {map.put("height", height); System.out.println("height : " + height);}
        if (!place.equals("선택 안함")) {map.put("place", place); System.out.println("place : " + place);}
        if (!smell.equals("선택 안함")) {map.put("smell", smell); System.out.println("smell : " + smell);}
        if (!growth_speed.equals("선택 안함")) {map.put("growth_speed", growth_speed); System.out.println("growth_speed : " + growth_speed);}
        if (!proper_temperature.equals("선택 안함")) {map.put("proper_temperature", proper_temperature); System.out.println("proper_temperature : " + proper_temperature);}
        if (!pest.equals("선택 안함")) {map.put("pest", pest); System.out.println("pest : " + pest);}
        if (!manage_level.equals("선택 안함")) {map.put("manage_level", manage_level); System.out.println("manage_level : " + manage_level);}
        if (!light.equals("선택 안함")) {map.put("light", light); System.out.println("light : " + light);}

        Call<Retrofit_infoplant_PutData> call_infoplant_put = service.put_infoplant_Func(map);
        call_infoplant_put.enqueue(new Callback<Retrofit_infoplant_PutData>() {
            @Override
            public void onResponse(Call<Retrofit_infoplant_PutData> call, Response<Retrofit_infoplant_PutData> response) {
                if (response.code() == 200) { //edit infoplant success
                    response.body();
                    String message = response.body().getMessage();
                    System.out.println(message);
                    //Toast.makeText(SaveInformationActivity.this, "식물 정보를 저장했습니다.", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(SaveInformationActivity.this);
                    builder.setTitle("식물 정보를 저장했습니다.");
                    builder.setMessage("정보를 제공해주셔서 감사합니다!");
                    builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent_goto_noticeboard = new Intent(SaveInformationActivity.this, NoticeBoardActivity.class);
                            startActivity(intent_goto_noticeboard);
                        }
                    });
                    builder.create().show();

                }
                else if(response.code() == 201 ) { //add plant success
                    response.body();
                    String message = response.body().getMessage();
                    System.out.println(message);
                    Toast.makeText(SaveInformationActivity.this, "식물 정보가 완성되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    response.body();
                    //String message = response.body().getMessage();
                    //System.out.println(message);
                    Log.v("SaveInformationActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(SaveInformationActivity.this, "식물 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Retrofit_infoplant_PutData> call, Throwable t) {
                Log.v("SaveInformationActivity", "Fail");
                Toast.makeText(SaveInformationActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}