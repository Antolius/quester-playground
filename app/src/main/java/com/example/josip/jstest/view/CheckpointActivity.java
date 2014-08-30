package com.example.josip.jstest.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.josip.jstest.R;
import com.example.josip.model.Checkpoint;
import com.google.common.io.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class CheckpointActivity extends Activity {

    private static final Logger logger = LoggerFactory.getLogger(CheckpointActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoint);

        WebView view = (WebView) findViewById(R.id.checkpointTemplate);
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);

        Checkpoint checkpoint = (Checkpoint) getIntent().getSerializableExtra("Checkpoint");

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        try {
            String html = Files.toString(checkpoint.getViewHtml(), Charset.defaultCharset());
            view.loadData(header+html, "text/html", "UTF-8");
        } catch (IOException e) {
            logger.info("Reading html view from checkpoint failed: " + e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkpoint, menu);
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
}
