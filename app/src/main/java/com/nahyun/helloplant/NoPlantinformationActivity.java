package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

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
                        NP_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NP_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent NP_intent_talk = new Intent(NoPlantinformationActivity.this, NoticeBoardActivity.class);
                        NP_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        Button addmyplantButton = (Button)findViewById(R.id.addmyplantButton);
        addmyplantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_addmyplant = new Intent(NoPlantinformationActivity.this, AddMyplantActivity.class);

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
    }
}