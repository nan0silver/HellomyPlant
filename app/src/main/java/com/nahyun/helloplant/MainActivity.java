package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public String login_url = "http://18.116.203.236:1234/user/login?email=";

    public EditText login_email, login_passwd;
    public Button login_Button, signup_Button;
    public static String email, passwd;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_email = (EditText)findViewById(R.id.login_email_EditText);
        login_passwd = (EditText)findViewById(R.id.login_passwd_EditText);

        signup_Button = findViewById(R.id.signup_Button);
        signup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_signup_page = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(goto_signup_page);
            }
        });

        //=====LOG IN=====//
        login_Button = findViewById(R.id.login_Button);
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = login_email.getText().toString();
                passwd = login_passwd.getText().toString();

                makeRequest();
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void makeRequest() {
        String url = String.format(login_url + email + "&password=" + passwd);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MainActivity.this, "로그인이 되었습니다.", Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject_response = null;
                        try {
                            jsonObject_response = new JSONObject(response);

                            SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("accessToken", jsonObject_response.getString("accessToken"));
                            editor.commit();
                            System.out.println("accessToken is saved");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent_goto_myplantlist = new Intent(MainActivity.this, MyplantListActivity.class);
                        startActivity(intent_goto_myplantlist);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "아이디나 비밀번호를 확인해주세요.\n회원가입이 필요하면 가입해주세요", Toast.LENGTH_SHORT).show();
                        System.out.println("error = " +error.getMessage() );
                    }
                }) {

            protected Map<String, String> getParms() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

}

