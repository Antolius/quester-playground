package com.example.josip.jstest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.josip.model.PersistentGameObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyActivity extends Activity {

    private static final Logger logger = LoggerFactory.getLogger(MyActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_template);

        PersistentGameObject persistentGameObject = new PersistentGameObject();

        WebView view = (WebView) findViewById(R.id.templateHolder);
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.addJavascriptInterface(new WebAppInterface(this, persistentGameObject), "Android");

        JavaScriptEngine javaScriptEngine = new JavaScriptEngine(persistentGameObject);
        try {
            javaScriptEngine.onEnter(readFromFile("scripts/onEnter.js", this), "Text added to on Enter");
        } catch (Exception e) {
            logger.info("QUESTER", "Invoking failed", e);
        }

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        String htmlString = readFromFile("test.html", this);

        view.loadDataWithBaseURL("file:///android_asset/", header + htmlString, "text/html", "UTF-8", null);
        //view.loadData(header + htmlString, "text/html", "UTF-8");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.html_template, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String readFromFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    private String insertObject(String htmlString, PersistentGameObject persistentGameObject) {
        int index = htmlString.lastIndexOf("<head>") + 6;

        String persistence = "<script> var persistence = {";
        for(String key : persistentGameObject.propertyKeys()){
            persistence += key + ":" + "\"" +  persistentGameObject.getProperty(key) + "\"";
        }
        persistence += "};</script>";

        return new StringBuilder(htmlString).insert(index, persistence).toString();
    }

    private String insertCss(String htmlString) {
        int index = htmlString.lastIndexOf("<head>") + 6;
        String css = "<style>" + readFromFile("css/bootstrap.css", this);
        css += readFromFile("css/bootstrap-theme.css", this);
        css += "</style>";
        return new StringBuilder(htmlString).insert(index, css).toString();
    }

    private String insertJs(String htmlString) {
        int index = htmlString.lastIndexOf("<head>") + 6;
        String js = "<script>" + readFromFile("scripts/jQuery-2.1.0.js", this);
        js += readFromFile("scripts/bootstrap.js", this);
        js += "</script>";
        return new StringBuilder(htmlString).insert(index, js).toString();
    }
}
