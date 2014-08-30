package com.example.josip.jstest.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.josip.jstest.R;
import com.google.common.io.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class HtmlTemplateActivity extends Activity {

    private static final Logger logger = LoggerFactory.getLogger(HtmlTemplateActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_template);

        WebView view = (WebView) findViewById(R.id.templateHolder);
        logger.info(view.getSettings().getJavaScriptEnabled()+"");
        logger.info(view.getSettings().getDomStorageEnabled()+"");
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

        String myHtmlString = "<html>\n" +
                "<head>\n" +
                "<script>\n" +
                "function myFunction() {\n" +
                "    document.getElementById(\"demo\").innerHTML = \"Paragraph changed.\";\n" +
                "}\n" +
                "</script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<h1>My Web Page</h1>\n" +
                "\n" +
                "<p id=\"demo\">A Paragraph.</p>\n" +
                "\n" +
                "<button type=\"button\" onclick=\"myFunction()\">Try it</button>\n" +
                "\n" +
                "</body>\n" +
                "</html> ";
        String filename = "checkpoint";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(myHtmlString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(this.getFilesDir(), filename);
        String htmlString = null;
        try {
            htmlString = Files.toString(file, Charset.defaultCharset());
            view.loadData(header+htmlString, "text/html", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.html_template, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
