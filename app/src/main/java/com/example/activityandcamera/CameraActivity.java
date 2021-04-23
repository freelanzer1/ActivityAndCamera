package com.example.activityandcamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {


    // для работы с файлами в манифесте указать разрешения
    //  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    //  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    // и настройки провайдера file provider


    ImageView imageViewPhoto;
    final String TAG = "CameraActivity";
    String currentPhotoPath;
    final int REQUEST_CODE_PHOTO = 1;
    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
    }


    public void button2(View view) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Проверяем что на телефоне есть приложение - камера
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                //создаем файл для сохранения фото
                photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Если файл создался
                if (photoFile != null) {
                    //получаем его Uri через FileProvider это рекомендуемая гуглом  механика работы
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    //указываем наш uri
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    //отправляем интент к стороннему активити с камерой
                    startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
                }
            }
    }


    // После того как фото сделано, приложение камера возвращает результат сюда
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = false;

            //Читаем файл в битмап
            Bitmap bmp = BitmapFactory.decodeFile(currentPhotoPath, bmpFactoryOptions);

            // И и ставитм его во вью
            imageViewPhoto.setImageBitmap(bmp);

        }
    }


    //создаем файл в который запишем наше фото
    private File createImageFile() throws IOException {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // сохраняем путь до файла
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void button3(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }

}