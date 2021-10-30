package com.nahyun.helloplant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_PICTURES;

public class searchPlant extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    private MediaScanner mMediaScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);

        mMediaScanner = MediaScanner.getInstance(getApplicationContext());

        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 및 파일을 저장하기 위해 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용하실 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


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
                            //startactivityforresult

                        }

                        return false;
                    }
                });

                popupMenu.show();

            }
        });

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

            File f = new File(strFolderName + "/" + filename + ".png");
            result = f.getPath();

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(f);
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

            ((ImageView)findViewById(R.id.cameraImageview)).setImageBitmap(rotate(bitmap, exifDegree));
        }
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