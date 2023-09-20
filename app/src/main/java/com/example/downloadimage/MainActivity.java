package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView; //1 Создадим переменую, которая ссылается на   imageView

    private String url = "https://img1.freepng.ru/20171221/fsq/fashion-background-vector-material-dynamic-lines-5a3bfec495c971.2770045315138812846135.jpg";   //3 сохраняем в строковую переменную скопированную URL картинки

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView); //2 Присваиваем значение
    }

    public void onClickDownloadImage(View view) {
        //16 Получаем  изображение
        DownloadImageTask task =new DownloadImageTask(); // при нажатии на кнопку мы создаем объект  DownloadImageTask()
        Bitmap bitmap=null;
        try { //17
            bitmap = task.execute(url).get(); //запускаем нашу задачу на выполнение, в качестве параметра передаем url картинки, метод get что бы получить изображение и передаем его в объект  bitmap
        } catch (ExecutionException e) {
            e.printStackTrace(); // throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace(); // throw new RuntimeException(e);
        }
        imageView.setImageBitmap(bitmap); //18 после чего у нашего  imageView устанавливаем изображение bitmap
        //19 <uses-permission android:name="android.permission.INTERNET"/>  в AdManifest.xml


    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {     //4 для того что бы загрузить наши данные из интернета создаем класс (который будет загружать изображение в другом потоке)
        @Override
        protected Bitmap doInBackground(String... strings) {  //5 переопределяем метод
            URL url = null; //6 в качестве параметра передали url
            HttpURLConnection urlConnection = null; //7
            //8 StringBuilder result = new StringBuilder(); //15 в нашем случае не нужна строка
            try { //10
                url = new URL(strings[0]); //9 создали url из первого параметра

                urlConnection = (HttpURLConnection) url.openConnection(); //10 открываем соединение в нашем url
                InputStream inputStream = urlConnection.getInputStream(); //12 получаем  inputStream  из нашего соединения
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream); //13 после этого создаем изображение из данного  inputStream
                return bitmap;//14 вернули

            } catch (MalformedURLException e) {//9
                e.printStackTrace(); // throw new RuntimeException(e); //9
            } catch (IOException e) {//11
                e.printStackTrace(); // throw new RuntimeException(e); //11
            } finally {//9
                if (urlConnection != null) {//9
                    urlConnection.disconnect();//9
                }
            }

            return null;
        }
    }
}