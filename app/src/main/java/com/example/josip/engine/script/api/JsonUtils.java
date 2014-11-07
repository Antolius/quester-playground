package com.example.josip.engine.script.api;

import org.mozilla.javascript.NativeObject;

public class JsonUtils {

    public static String fromNativeObject(NativeObject nativeObject){

        String json = "{";
        for(Object object : nativeObject.keySet()){

            Object value = nativeObject.get(object);
            if(value instanceof NativeObject){
                value = fromNativeObject((NativeObject) value);
            }

            json +="\""+object+"\":\""+value+"\",";
        }
        json = json.substring(0,json.length()-1)+"}";

        return json;
    }
}
