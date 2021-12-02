package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewMyplantActivity extends AppCompatActivity {

    TextView PlantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_myplant);

        Intent intent_comefrom_addmyplant_page = getIntent();
        String PlantName_string = intent_comefrom_addmyplant_page.getExtras().getString("PlantName");

        PlantName = (TextView)findViewById(R.id.myplant_name_TextView);
        PlantName.setText(PlantName_string);


        findViewById(R.id.view_to_add_page_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_modify_page = new Intent(ViewMyplantActivity.this, AddMyplantActivity.class);
                startActivity(intent_modify_page);
            }
        });
    }
}