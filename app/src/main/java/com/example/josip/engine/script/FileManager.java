package com.example.josip.engine.script;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

    //TODO move this class somewhere else

    public String readFromFile(File file) throws IOException {

        StringBuilder result = new StringBuilder("");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(isr);

            String readString = bufferedReader.readLine();
            while (readString != null) {
                result.append(readString);
                readString = bufferedReader.readLine();
            }

            isr.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result.toString();
    }

    public String read(String file) throws IOException {

        BufferedReader reader =
                new BufferedReader(
                        new FileReader(
                                new File(file))
                );

        String json = "";
        String line;
        while ((line = reader.readLine()) != null) {
            json += line;
        }

        return  json;
    }

    public void write(Context context, String fileName, String text){

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
