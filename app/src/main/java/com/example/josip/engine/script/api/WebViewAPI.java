package com.example.josip.engine.script.api;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.NativeObject;

import java.util.Iterator;

public class WebViewAPI {

    private Context context;
    private JavascriptAPI api;

    public WebViewAPI(Context context, JavascriptAPI api) {
        this.context = context;
        this.api = api;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    //TODO not general enough
    @JavascriptInterface
    public String getPersistence(){

        return JsonUtils.fromNativeObject(api.getPersistenceObject());
    }

    @JavascriptInterface
    public void setPersistence(String json) throws JSONException {

        JSONObject object = new JSONObject(json);

        Iterator<String> iterator = object.keys();
        while(iterator.hasNext()){
            String key = iterator.next();
            NativeObject.putProperty(api.getPersistenceObject(), key, object.get(key));
        }
    }
}
