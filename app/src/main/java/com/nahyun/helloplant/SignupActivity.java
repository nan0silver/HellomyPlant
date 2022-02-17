package com.nahyun.helloplant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    EditText signup_id_EditText, signup_nickname_EditText, signup_passwd_EditText, signup_passwd_confirm_EditText;
    ImageView signup_passwd_ImageView;
    Button signup_assign_Button;
    public static String email, nickname, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup_id_EditText = (EditText)findViewById(R.id.signup_id_EditText);
        signup_nickname_EditText = (EditText)findViewById(R.id.signup_nickname_EditText);

        signup_passwd_EditText = (EditText)findViewById(R.id.signup_passwd_EditText);
        signup_passwd_confirm_EditText = (EditText)findViewById(R.id.signup_passwd_confirm_EditText);
        signup_passwd_ImageView = (ImageView)findViewById(R.id.signup_passwd_ImageView);

        signup_passwd_confirm_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(signup_passwd_EditText.getText().toString().equals(signup_passwd_confirm_EditText.getText().toString())) {
                    signup_passwd_ImageView.setImageResource(R.drawable.passwd_o);
                }
                else {
                    signup_passwd_ImageView.setImageResource(R.drawable.passwd_x);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signup_assign_Button = (Button)findViewById(R.id.signup_assign_Button);
        signup_assign_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = signup_id_EditText.getText().toString();
                nickname = signup_nickname_EditText.getText().toString();
                passwd = signup_passwd_confirm_EditText.getText().toString();

                NetworkTask_Signup networkTask_signup = new NetworkTask_Signup();
                networkTask_signup.execute();
            }
        });
    }

    public class NetworkTask_Signup extends AsyncTask<String, Void, String> {

        String message;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL signup_url = new URL("http://18.116.203.236:1234/user/signup");

                HttpURLConnection conn = (HttpURLConnection) signup_url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("Cache-Control", "no-cache");

                JSONObject jsonObject_signup = new JSONObject();
                jsonObject_signup.put("email", email);
                jsonObject_signup.put("password", passwd);
                jsonObject_signup.put("nickname", nickname);

                OutputStream os = conn.getOutputStream();
                os.write(jsonObject_signup.toString().getBytes());
                os.close();

                int retCode = conn.getResponseCode();
                System.out.println("retCode = " + retCode);
                if (retCode == 201) {
                    System.out.println("sign up OK");
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String read_line;
                    StringBuffer response = new StringBuffer();
                    while ((read_line = br.readLine()) != null) {
                        response.append(read_line);
                        response.append("");
                    }
                    br.close();

                    String get_response = response.toString();
                    JSONObject json_response = new JSONObject(get_response);

                    message = (String) json_response.get("message");
                    message += "\n 로그인화면으로 이동해 로그인을 해주세요.";
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }, 0);

                } else {
                    System.out.println("error");

                }

            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException");
                e.printStackTrace();
            } catch (SocketTimeoutException e) {
                System.out.println("SocketTimeoutException");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}