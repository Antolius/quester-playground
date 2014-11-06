package com.example.josip;

import android.util.Log;

public class Logger {

    private Class aClass;

    public static Logger getLogger(Class aClass){
        return new Logger(aClass);
    }

    private Logger(Class aClass) {
        this.aClass = aClass;
    }

    public void debug(String message){
        if(Log.isLoggable("QUESTER-" +  aClass.getName(), Log.DEBUG)){
            Log.d("QUESTER-" +  aClass.getName(), message);
        }
    }

    public void error(String message){
        if(Log.isLoggable("QUESTER-" +  aClass.getName(), Log.ERROR)){
            Log.e("QUESTER-" +  aClass.getName(), message);
        }
    }

    public void error(String message, Throwable throwable){
        if(Log.isLoggable("QUESTER-" +  aClass.getName(), Log.ERROR)){
            Log.e("QUESTER-" +  aClass.getName(), message, throwable);
        }
    }
}
