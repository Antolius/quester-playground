package com.example.josip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

    public static String getStringFromFile (File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        String result = convertStreamToString(fileInputStream);
        fileInputStream.close();
        return result;
    }

    private static String convertStreamToString(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }
}
