package com.example.josip.jstest;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.josip.model.PersistentGameObject;

public class WebAppInterface {
    Context mContext;
    private PersistentGameObject persistentGameObject;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c, PersistentGameObject persistentGameObject) {
        mContext = c;
        this.persistentGameObject = persistentGameObject;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public Object getProperty(String key){
        return persistentGameObject.getProperty(key);
    }

    @JavascriptInterface
    public void putProperty(String key, String value){
        persistentGameObject.putProperty(key, value);
    }
}
