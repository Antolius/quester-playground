package com.example.josip.jstest;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.josip.gameService.GameEngineService;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestActivity extends Activity  {

    private static final Logger logger = LoggerFactory.getLogger(MyActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SQLiteDatabase db = openOrCreateDatabase("test", 0, null);
        ContextHolder.setContext(this);
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
        flyway.migrate();

        String MY_AWESOME_STRING = "'Awesome'";

        final String CUSTOM_STRING_FUNCTION = "var onEnter = function (string) { return string += ' and then some!' };";

        try {
            Log.d("JEJ", new JavaScriptEngine().onEnter(CUSTOM_STRING_FUNCTION, MY_AWESOME_STRING));
        } catch (Exception e) {
            logger.info("QUESTER", "Error invoking onEnter", e);
        }

        final Intent i= new Intent(this, GameEngineService.class);
        Messenger messenger = new Messenger(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("QUESTER", msg.getData().getString("Poruka"));
            }
        });
        i.putExtra("Messenger", messenger);

        Button startButton = (Button) findViewById(R.id.startServiceButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(i);
            }
        });

        Button stopButton = (Button) findViewById(R.id.stopServiceButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
