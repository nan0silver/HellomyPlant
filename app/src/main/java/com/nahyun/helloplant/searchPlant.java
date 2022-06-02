package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Environment.DIRECTORY_PICTURES;

public class searchPlant extends BottomNavigationActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private static final int REQUEST_GALLERY = 682;
    private String imageFilePath;
    private Uri photoUri;
    private File temp_gallery_File;
    private int mDegree, resize_width = 300;
    private ProgressDialog progressDialog;

    private MediaScanner mMediaScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);

        Calendar currentDate = Calendar.getInstance();
        Log.d("AddMyplantActivity", "HOUR : " + currentDate.get(Calendar.HOUR_OF_DAY));

        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.action_camera);
        navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        break;
                    case R.id.action_home:
                        Intent SP_intent_home = new Intent(searchPlant.this, MyplantListActivity.class);
                        //SP_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SP_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent SP_intent_talk = new Intent(searchPlant.this, NoticeBoardActivity.class);
                        //SP_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SP_intent_talk);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        mMediaScanner = MediaScanner.getInstance(getApplicationContext());

        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 및 파일을 저장하기 위해 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용하실 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

        //create loading page object
        ProgressDialog customProgressDialog;
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.camera_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.camera_menu) {
                            //----connection with camera---//
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                File photoFile = null;
                                try {
                                    photoFile = createImageFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (photoFile != null) {
                                    photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                                }
                            }
                        }
                        else {
                            //----connect with gallery----//
                            Toast.makeText(searchPlant.this, "갤러리 선택", Toast.LENGTH_SHORT).show();

                            Intent intent_gallary = new Intent(Intent.ACTION_PICK);
                            intent_gallary.setType(MediaStore.Images.Media.CONTENT_TYPE);
                            startActivityForResult(intent_gallary, REQUEST_GALLERY);

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        findViewById(R.id.searchImageButton).setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(searchPlant.this);
                progressDialog.setMax(100);
                progressDialog.setMessage("정보를 불러오고 있는 중입니다.");
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

                progressDialog.show();

//                String apiKey = "nQljT3UMscsIaE5YapywR1oTs96TrCzw2V9fdzqegI6j5mvxAw";
                String apiKey = "g5AkSeLBbiQjfWUK45AhmNu6e07gvLlCxXCzov0ZeEzOYq1uOK";

                String [] flowers = new String[] {"test_image.jpeg"};

                JSONObject data = new JSONObject();
                try {
                    data.put("api_key", apiKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray images = new JSONArray();
                for(String filename : flowers) {
                    String fileData = null;
                    try {
                        fileData = base64EncodeFromFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    images.put(fileData);
                }

                try {
                    data.put("images", images);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // add language
                try {
                    data.put("plant_language", "en");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // add details
                JSONArray plantDetails = new JSONArray()
                        .put("common_names")
                        .put("url")
                        .put("name_authority");
                try {
                    data.put("plant_details", plantDetails);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(data);

                String scientific_name = "";
                String probability = "";
                String[] NetworkTask_result = new String[2];

                try {
                    NetworkTask_result = new NetworkTask().execute(data).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                scientific_name = NetworkTask_result[0];
                probability = NetworkTask_result[1];

                if(scientific_name.equals("network error")){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"네트워크에 에러가 있습니다. 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(!scientific_name.equals("not plant")){

                    searchPlant_get(scientific_name, probability);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"식물이 아닙니다. 정확한 식물 사진을 넣어주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        ImageButton rotate_image_Button = (ImageButton)findViewById(R.id.searching_plant_rotate_Button);
        rotate_image_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDegree += 90;
                ImageView rotate_ImageView = (ImageView)findViewById(R.id.cameraImageview);
                BitmapDrawable drawable = (BitmapDrawable)rotate_ImageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                rotate_ImageView.setImageBitmap(rotate(bitmap, mDegree));

                Toast.makeText(searchPlant.this, "이미지가 회전되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public byte[] ImageViewToByteArray() {

        ImageView selected_Image_View = (ImageView)findViewById(R.id.cameraImageview);
        BitmapDrawable selected_image_drawable = (BitmapDrawable)selected_Image_View.getDrawable();
        int height = selected_image_drawable.getBitmap().getHeight();
        int width = selected_image_drawable.getBitmap().getWidth();
        System.out.println("height = " + height + " width = " + width);
        resize_width = 400*width/height;
        System.out.println("resize_width = " + resize_width);
        if (resize_width <= 0) resize_width = 300;
        Bitmap selected_image_bitmap = Bitmap.createScaledBitmap(selected_image_drawable.getBitmap(), resize_width , 400, true);
        ByteArrayOutputStream stream_change = new ByteArrayOutputStream();
        selected_image_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream_change);
        byte[] byteArray_result = stream_change.toByteArray();

        return byteArray_result;
    }

    public void searchPlant_get(String scientific_name, String probability) {
        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println("searchPlant token = " + token);

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
                .baseUrl("http://3.12.148.142/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);

        Call<Retrofit_plant_GetData> call_plant_get = service.get_plant_Func(scientific_name);
        System.out.println("searchPlant scientific name = " + scientific_name);

        call_plant_get.enqueue(new Callback<Retrofit_plant_GetData>() {
            @Override
            public void onResponse(Call<Retrofit_plant_GetData> call, Response<Retrofit_plant_GetData> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    response.body();
                    String after_id = response.body().getPlant().getId();
                    String after_scientific_name = response.body().getPlant().getScientificName();
                    String after_family_name = response.body().getPlant().getFamilyName();
                    String after_korean_name = response.body().getPlant().getKoreanName();
                    String after_water_cycle = response.body().getPlant().getWaterCycle();
                    String after_height = response.body().getPlant().getHeight();
                    String after_place = response.body().getPlant().getPlace();
                    String after_smell = response.body().getPlant().getSmell();
                    String after_growth_speed = response.body().getPlant().getGrowthSpeed();
                    String after_proper_temperature = response.body().getPlant().getProperTemperature();
                    String after_pest = response.body().getPlant().getPest();
                    String after_manage_level = response.body().getPlant().getManageLevel();
                    String after_light = response.body().getPlant().getLight();
                    String after_createdAt = response.body().getPlant().getCreatedAt();
                    String after_updatedAt = response.body().getPlant().getUpdatedAt();

                    JSONObject plantDetailData = new JSONObject();

                    try {
                        plantDetailData.put("koreanName", after_korean_name);
                        plantDetailData.put("familyName", after_family_name);
                        plantDetailData.put("scientificName", after_scientific_name);
                        plantDetailData.put("height", after_height);
                        plantDetailData.put("place", after_place);
                        plantDetailData.put("smell", after_smell);
                        plantDetailData.put("growthSpeed", after_growth_speed);
                        plantDetailData.put("properTemperature", after_proper_temperature);
                        plantDetailData.put("pest", after_pest);
                        plantDetailData.put("watercycleSpring", after_water_cycle);
                        plantDetailData.put("watercycleSummer", after_water_cycle);
                        plantDetailData.put("watercycleFall", after_water_cycle);
                        plantDetailData.put("watercycleWinter", after_water_cycle);
                        plantDetailData.put("manageLevel", after_manage_level);
                        plantDetailData.put("light", after_light);
                        plantDetailData.put("after_response_id", after_id);
                        plantDetailData.put("after_response_createdAt", after_createdAt);
                        plantDetailData.put("after_response_updatedAt", after_updatedAt);
                        plantDetailData.put("probability", probability);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent_goto_plantinformation_page = new Intent(searchPlant.this, PlantInformationActivity.class);

                    intent_goto_plantinformation_page.putExtra("plantDetailData", plantDetailData.toString());
                    System.out.println("searchPlant plantDetailData : " + plantDetailData);

                    byte[] byteArray_result = ImageViewToByteArray();
                    intent_goto_plantinformation_page.putExtra("image_bitmap", byteArray_result);

                    //Toast.makeText(searchPlant.this, "식물 정보 요청에 성공했습니다.", Toast.LENGTH_SHORT).show();

                    startActivity(intent_goto_plantinformation_page);

                }
                else { //if response is 400+a
                    Intent intent_goto_noinfo_page = new Intent(searchPlant.this, NoPlantinformationActivity.class);
                    intent_goto_noinfo_page.putExtra("ScientificName", scientific_name);

                    byte[] byteArray_result = ImageViewToByteArray();
                    intent_goto_noinfo_page.putExtra("PlantImage",  byteArray_result);

                    startActivity(intent_goto_noinfo_page);

                    System.out.println("searchPlant page response code : " + response.code());
                    //Toast.makeText(getApplicationContext(), "식물 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Retrofit_plant_GetData> call, Throwable t) {
                progressDialog.dismiss();
                Log.v("searchPlantActivity", "Fail");
                Toast.makeText(getApplicationContext(), "네트워크에 에러가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_search_plant;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_camera;
    }

    // plant.id api
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String base64EncodeFromFile() throws Exception {

        ImageView imageView = findViewById(R.id.cameraImageview);

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.getEncoder().encodeToString(imageBytes);

        return imageString;
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "myPLANT_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exifInterface = null;

            try {
                exifInterface = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation, exifDegree;

            if(exifInterface != null) {
                exifOrientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            }
            else {
                exifDegree = 0;
            }

            String result = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss", Locale.getDefault());
            Date curDate = new Date(System.currentTimeMillis());
            String filename = formatter.format(curDate);

            String strFolderName = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + File.separator + "HellomyPlant" + File.separator;
            File file = new File(strFolderName);
            if (!file.exists()) file.mkdir();

            File set_filename = new File(strFolderName + "/" + filename + ".png");
            result = set_filename.getPath();

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(set_filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                result = "Save Error FileOutputStream";
            }

            rotate(bitmap, exifDegree).compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);

            try {
                fileOutputStream.flush();
            } catch (IOException e ) {
                e.printStackTrace();
            }

            try {
                fileOutputStream.close();
                mMediaScanner.mediaScanning(strFolderName+"/"+filename+".png");
            } catch (IOException e) {
                e.printStackTrace();
                result = "File close Error";
            }

            //((ImageView)findViewById(R.id.cameraImageview)).setImageBitmap(rotate(bitmap, exifDegree));
            ImageView cameraImageview = (ImageView)findViewById(R.id.cameraImageview);
            Glide.with(getApplicationContext()).asBitmap().load(rotate(bitmap, exifDegree))
                    .into(cameraImageview);
        }
        else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri photo_gallery_Uri = data.getData();
            Cursor cursor = null;

            try {
                String[] gal = {MediaStore.Images.Media.DATA};
                assert photo_gallery_Uri != null;
                cursor = getContentResolver().query(photo_gallery_Uri, gal, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                temp_gallery_File = new File(cursor.getString(column_index));
            }
            finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            try {
                set_gallery_Image(photo_gallery_Uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void set_gallery_Image(Uri uri) throws IOException {
        ImageView imageView = findViewById(R.id.cameraImageview);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap original_gallery_bitmap = BitmapFactory.decodeFile(temp_gallery_File.getAbsolutePath(), options);
        //rotate_gallery_Image(uri, original_gallery_bitmap);

        //imageView.setImageBitmap(original_gallery_bitmap);
        ImageView cameraImageview = (ImageView)findViewById(R.id.cameraImageview);
        Glide.with(getApplicationContext()).asBitmap().load(original_gallery_bitmap)
                .into(cameraImageview);
    }

    private  int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        else {
            return 0;
        }
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Bitmap rotate_gallery_Image(Uri uri, Bitmap bitmap) throws IOException {
        InputStream in = getContentResolver().openInputStream(uri);
        ExifInterface exifInterface = new ExifInterface(in);
        in.close();

        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        Matrix matrix = new Matrix();
        if(orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            matrix.postRotate(90);
        }
        else if(orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            matrix.postRotate(180);
        }
        else if(orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            matrix.postRotate(270);
        }
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(), matrix, true);
    }


    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
        }
    };
}

class NetworkTask extends AsyncTask<JSONObject, Void, String[]> {
    private String scientific_name = "not plant";
    protected String[] doInBackground(JSONObject... data) {
        try{
            URL url = new URL("https://api.plant.id/v2/identify");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            OutputStream os = con.getOutputStream();
            os.write(data[0].toString().getBytes());
            os.close();

            InputStream is = con.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int read;
            byte[] byte_response = new byte[30000];
            while ((read = is.read(byte_response, 0, byte_response.length)) != -1) {
                buffer.write(byte_response, 0, read);
            }
            String response = new String(byte_response);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < response.length(); i++) {
                if ('\\' == response.charAt(i) && 'u' == response.charAt(i + 1)) {
                    Character r = (char) Integer.parseInt(response.substring(i + 2, i + 6), 16);
                    sb.append(r);
                    i += 5;
                } else {
                    sb.append(response.charAt(i));
                }
            }
            System.out.println("Response code : " + con.getResponseCode());
            String strData = sb.toString();
            System.out.println("Response : " + strData);
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject = new JSONObject(strData);
                System.out.println("OBJECT : " +jsonObject.toString());
            }catch(JSONException err){
                System.out.println("Exception: " + err.toString());
            }
            JSONArray suggestions = jsonObject.getJSONArray("suggestions");
            JSONObject firstSuggestions = suggestions.getJSONObject(0);
            String probabilityString = firstSuggestions.getString("probability");
            float probability = Float.parseFloat(probabilityString);
            if(probability>0.5){
                System.out.println("probability is bigger than 0.5");
                JSONObject plant_details = firstSuggestions.getJSONObject("plant_details");
                scientific_name = plant_details.getString("scientific_name");
                System.out.println("Network Task scientific_name : " + scientific_name);
            }
            else{
                System.out.println("probability is too low");
            }
            System.out.println(probability);
            is.close();
            con.disconnect();

            String[] result = new String[2];
            result[0] = scientific_name;
            result[1] = probabilityString;
            return result;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] error_result = new String[2];
        error_result[0] = "network error";
        error_result[1] = "network error";
        return error_result;
    }
    protected void onPostExecute(String scientific_name){

    }
}


