package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMyplantActivity extends AppCompatActivity {

    EditText PlantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_myplant);


        Intent intent_comefrom_plantinfomation_page = getIntent();
        JSONObject plantDetailData = new JSONObject();
        String jsonString =
                intent_comefrom_plantinfomation_page.getExtras().getString("plantDetailData");
        try {
            plantDetailData = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //==== watering spinner code =====//
        String wateringInfomation = "";
        try {
            wateringInfomation = (String)plantDetailData.get("watercycleWinter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int wateringCycle = 0;
        System.out.println(wateringInfomation);
        if(wateringInfomation.charAt(0)=='항'){
            wateringCycle = 0;
        } else if(wateringInfomation.charAt(0)=='흙'){
            wateringCycle = 6;
        } else if(wateringInfomation.charAt(0)=='토'){
            wateringCycle = 13;
        }else{
            wateringCycle = 29;
        }

        Spinner spinner_watering = findViewById(R.id.set_wateringperiod_Spinner);

        ArrayAdapter arrayAdapter_watering = ArrayAdapter.createFromResource(this, R.array.watering_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_watering.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_watering.setAdapter(arrayAdapter_watering);

        spinner_watering.setSelection(wateringCycle);

        spinner_watering.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //==== fertilizing spinner code ====//
        Spinner spinner_fertilizing = findViewById(R.id.set_fertilizingperiod_Spinner);

        ArrayAdapter arrayAdapter_fertilizing = ArrayAdapter.createFromResource(this, R.array.fertilizing_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_fertilizing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_fertilizing.setAdapter(arrayAdapter_fertilizing);

        spinner_fertilizing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PlantName = (EditText)findViewById(R.id.set_plantname_EditText);

        findViewById(R.id.set_myplant_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_viewmyplant_page = new Intent(AddMyplantActivity.this, ViewMyplantActivity.class);

                intent_goto_viewmyplant_page.putExtra("PlantName", PlantName.getText().toString());

                startActivity(intent_goto_viewmyplant_page);
            }
        });


    }
}