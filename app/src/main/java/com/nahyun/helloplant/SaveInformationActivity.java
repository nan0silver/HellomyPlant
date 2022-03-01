package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

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

        findViewById(R.id.saveinformation_saving_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD infoplant PUT code
            }
        });

        //====SPINNER CODE====//

        //---water cycle spinner---//
        Spinner saveinformation_water_cycle_Spinner = findViewById(R.id.saveinformation_water_cycle_Spinner);

        ArrayAdapter arrayAdapter_water_cycle = ArrayAdapter.createFromResource(this, R.array.saveinformation_water_cycle_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_water_cycle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saveinformation_water_cycle_Spinner.setAdapter(arrayAdapter_water_cycle);

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

        saveinformation_light_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}