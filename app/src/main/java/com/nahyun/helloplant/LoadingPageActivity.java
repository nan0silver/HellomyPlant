package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/*
public class LoadingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        Loadingstart();
    }
    private void Loadingstart() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent_loading = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent_loading);
                finish();
            }
        }, 2000);
    }
}*/

public class LoadingPageActivity extends Dialog {

    public LoadingPageActivity(Context context) {

        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading_page);
    }
}
