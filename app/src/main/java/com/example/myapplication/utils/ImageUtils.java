package com.example.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageUtils {

    public static String downloadAndSave(Context context, String imageUrl, String fileName) throws Exception {
        URL url = new URL(imageUrl);
        InputStream input = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();

        File file = new File(context.getFilesDir(), fileName);
        FileOutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        out.flush();
        out.close();

        return file.getAbsolutePath();
    }
}
