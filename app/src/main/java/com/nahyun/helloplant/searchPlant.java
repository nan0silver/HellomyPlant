package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.io.FileInputStream;
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

import static android.os.Environment.DIRECTORY_PICTURES;

public class searchPlant extends BottomNavigationActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private static final int REQUEST_GALLERY = 682;
    private String imageFilePath;
    private Uri photoUri;
    private File temp_gallery_File;
    private int mDegree;


    private MediaScanner mMediaScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);

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
                        SP_intent_home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SP_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_ranking:
                        Intent SP_intent_ranking = new Intent(searchPlant.this, RankingListActivity.class);
                        SP_intent_ranking.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SP_intent_ranking);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        Intent SP_intent_talk = new Intent(searchPlant.this, NoticeBoardActivity.class);
                        SP_intent_talk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
            int resize_width = 300;
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
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

                try {
                    scientific_name = new NetworkTask().execute(data).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String[] idAndName = new String[2];
                if(scientific_name.equals("network error")){
                    Toast.makeText(getApplicationContext(),"네트워크에 에러가 있습니다. 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(!scientific_name.equals("not plant")){
                    try {
                        idAndName = new NongSaroGardenListTask().execute(scientific_name).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i<idAndName.length; i++){
                        System.out.println(idAndName[i]);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"식물이 아닙니다. 정확한 식물 사진을 넣어주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject plantDetailData = new JSONObject();
                if(idAndName[0].equals("noData")){
                    Intent intent_goto_noinfo_page = new Intent(searchPlant.this, NoPlantinformationActivity.class);
                    intent_goto_noinfo_page.putExtra("ScientificName", scientific_name);

                    ImageView selected_Image_View = (ImageView)findViewById(R.id.cameraImageview);
                    BitmapDrawable selected_image_drawable = (BitmapDrawable)selected_Image_View.getDrawable();
                    int height = selected_image_drawable.getBitmap().getHeight();
                    int width = selected_image_drawable.getBitmap().getWidth();
                    resize_width = 400*width/height;
                    System.out.println(resize_width);
                    Bitmap selected_image_bitmap = Bitmap.createScaledBitmap(selected_image_drawable.getBitmap(), resize_width, 400, true);
                    ByteArrayOutputStream stream_change = new ByteArrayOutputStream();
                    selected_image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream_change);
                    byte[] byteArray_result = stream_change.toByteArray();
                    intent_goto_noinfo_page.putExtra("PlantImage",  byteArray_result);

                    startActivity(intent_goto_noinfo_page);

                    Toast.makeText(getApplicationContext(), "식물 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(idAndName[0].equals("listerror")){
                    Toast.makeText(getApplicationContext(), "네트워크에 에러가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    try {
                        plantDetailData = new NongSaroGardenDetailTask().execute(idAndName[0]).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        plantDetailData.put("name", idAndName[1]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //show loading page
                //customProgressDialog.show();

                //----change page----//

                Intent intent_goto_plantinformation_page = new Intent(searchPlant.this, PlantInformationActivity.class);

                intent_goto_plantinformation_page.putExtra("plantDetailData", plantDetailData.toString());

                //put Imageview image to intent
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
                intent_goto_plantinformation_page.putExtra("image_bitmap", byteArray_result);



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent_goto_plantinformation_page);
                    }
                }, 3000);

                return;
            }
        });

        findViewById(R.id.searched_plant_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_goto_searchedname_page = new Intent(searchPlant.this, SearhedNameActivity.class);
                startActivity(intent_goto_searchedname_page);
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

    public Bitmap resizing_image(Bitmap bitmap, int targetWidth, int targetHeight) {
        BitmapFactory.Options bf_options = new BitmapFactory.Options();
        bf_options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFilePath, bf_options);

        int photoWidth = bf_options.outWidth;
        int photoHeight = bf_options.outHeight;

        if(targetHeight <= 0) {
            targetHeight = (targetWidth * photoHeight) / photoWidth;
        }

        int scaleFactor = 1;
        if (photoWidth > targetWidth) {
            scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight);
        }

        bf_options.inJustDecodeBounds = false;
        bf_options.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imageFilePath, bf_options);
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

class NetworkTask extends AsyncTask<JSONObject, Void, String> {
    private Exception exception;

    private String scientific_name = "not plant";
    protected String doInBackground(JSONObject... data) {
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
                System.out.println(scientific_name);

            }
            else{
                System.out.println("probability is too low");
            }
            System.out.println(probability);
            is.close();
            con.disconnect();
            return scientific_name;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "network error";


    }
    protected void onPostExecute(String scientific_name){

    }

}

class NongSaroGardenListTask extends AsyncTask<String, Void, String[]> {
    protected String[] doInBackground(String... str){
        String temp = "";
        System.out.println("input scientific name in list : " + str[0]);
        String[] nongsaroListResponse = new String[]{"error","error"};
        try{
            URL url = new URL("http://api.nongsaro.go.kr/service/garden/gardenList?apiKey=202111223IVEFOUFEVGRCFNIGNVHBA&sType=sPlntbneNm&sText="+str[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("GET");

            if (con.getResponseCode() == con.HTTP_OK) {
                nongsaroListResponse = new String[]{"noData","noData"};
                InputStreamReader tmp = new InputStreamReader(con.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp);
                }
                String receiveMsg = buffer.toString();
                System.out.println("receiveMsg from nongsaro list api : " + receiveMsg);

                reader.close();

                DocumentBuilderFactory documentBuilderFactory =  DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = null;
                Document document = null;
                NodeList nodeList = null;
                Node node = null;
                Element element = null;
                InputSource inputSource = new InputSource(new StringReader(receiveMsg));
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(inputSource);
                XPathFactory xPathFactory = XPathFactory.newInstance();
                XPath xPath = xPathFactory.newXPath();
                XPathExpression xPathExpression = xPath.compile("//items/item");
                nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
                if (nodeList.item(0) != null) {
                    NodeList child = nodeList.item(0).getChildNodes();
                    node = child.item(0);
                    nongsaroListResponse[0] = node.getTextContent();
//                System.out.println("현재 노드 값 : " + node.getTextContent());
                    node = child.item(1);
                    nongsaroListResponse[1] = node.getTextContent();
//                System.out.println("현재 노드 값 : " + node.getTextContent());
                }
                else {
                    System.out.println("내가 맞음");
                }
            } else {
                System.out.println("결과"+ con.getResponseCode() + "Error");
            }
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return nongsaroListResponse;
    }
}
class NongSaroGardenDetailTask extends AsyncTask<String,Void,JSONObject>{
    JSONObject data = new JSONObject();
    protected JSONObject doInBackground(String... id) {
        String temp = "";
        try{
            System.out.println("input id in nongsaro detail : " + id[0]);
            URL url = new URL("http://api.nongsaro.go.kr/service/garden/gardenDtl?apiKey=202111223IVEFOUFEVGRCFNIGNVHBA&cntntsNo="+id[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("GET");

            if (con.getResponseCode() == con.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(con.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp);
                }
                String receiveMsg = buffer.toString();
                System.out.println("receiveMsg from nongsaro detail api : " + receiveMsg);

                reader.close();

                DocumentBuilderFactory documentBuilderFactory =  DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = null;
                Document document = null;
                NodeList nodeList = null;
                Node node = null;
                Element element = null;
                InputSource inputSource = new InputSource(new StringReader(receiveMsg));
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(inputSource);
                XPathFactory xPathFactory = XPathFactory.newInstance();
                XPath xPath = xPathFactory.newXPath();
                XPathExpression xPathExpression = xPath.compile("//item");
                nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
                NodeList child = nodeList.item(0).getChildNodes();
//                String[] nongsaroListResponse = new String[2];
                node = child.item(0);
//                nongsaroListResponse[0] = node.getTextContent();
//                for (int i = 0; i<child.getLength(); i++){
//                    System.out.println(i);
//                    System.out.println(child.item(i).getTextContent());
//                }
                data.put("familyName", child.item(12).getTextContent());
                data.put("height", child.item(19).getTextContent());
                data.put("place", child.item(47).getTextContent());
                data.put("smell", child.item(51).getTextContent());
                data.put("growthSpeed", child.item(24).getTextContent());
                data.put("properTemperature", child.item(21).getTextContent());
                data.put("pest", child.item(4).getTextContent());
                data.put("fertilizer", child.item(17).getTextContent());
                data.put("watercycleSpring",child.item(62).getTextContent());
                data.put("watercycleSummer",child.item(62).getTextContent());
                data.put("watercycleFall",child.item(62).getTextContent());
                data.put("watercycleWinter",child.item(62).getTextContent());
                data.put("manageLevel", child.item(40).getTextContent());
                data.put("light", child.item(36).getTextContent());
                data.put("poison", child.item(54).getTextContent());
                return data;
            } else {
                System.out.println("결과"+ con.getResponseCode() + "Error");
            }
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("nongsaroDetailError");
        return null;
    }
}