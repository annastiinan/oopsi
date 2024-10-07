package com.example.municipalityapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PicDataRetriever {
    private Executor executor;
    public interface PicDataCallback {
        void onPicDataLoaded(Bitmap bitmap);
    }
    public PicDataRetriever() {
        executor = Executors.newSingleThreadExecutor();
    }
    public void getPicData(final PicDataCallback callback) {
        executor.execute(() -> {
            int statusCode = (int) (Math.random() * 500 + 100);
            String imageUrl = "https://http.cat/" + statusCode;
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final Bitmap finalBitmap = bitmap;
            callback.onPicDataLoaded(finalBitmap);
        });
    }
}