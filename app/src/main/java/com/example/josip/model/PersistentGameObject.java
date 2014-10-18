package com.example.josip.model;

import android.webkit.JavascriptInterface;

import org.mozilla.javascript.ScriptableObject;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Josip on 11/08/2014!
 */
public class PersistentGameObject extends ScriptableObject{

    private HashMap<String, Object> map = new HashMap<String, Object>();

    public PersistentGameObject() {
    }

    public PersistentGameObject(PersistentGameObject persistentGameObject){
        this.map = persistentGameObject.map;
    }

    public void putProperty(String key, Object value) {
        map.put(key, value);
    }

    public Object getProperty(String key) {
        return map.get(key);
    }

    public Set<String> propertyKeys(){
        return map.keySet();
    }


    @Override
    public String getClassName() {
        return "PersistentGameObject";
    }

    public void jsFunction_putProperty(String key, Object value){
        putProperty(key, value);
    }

    public Object jsFunction_getProperty(String key){
        return getProperty(key);
    }
}