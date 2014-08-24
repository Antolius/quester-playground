package com.example.josip.jstest.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.josip.QExample;
import com.example.josip.gameService.GameEngineService;
import com.example.josip.jstest.R;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLiteTemplates;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqldroid.SQLDroidDriver;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

//import org.flywaydb.core.Flyway;
//import org.flywaydb.core.api.android.ContextHolder;

public class MyActivity extends InjectionActivity {

    private static final Logger logger = LoggerFactory.getLogger(MyActivity.class);
    @Inject
    LocationManager locationManager;
    private Button startServiceButton, stopServiceButton;
    private TextView latitudeText, longitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initViewComponents();

        experimentWithDatabase();

        useLocationManager();

        final Intent i = new Intent(this, GameEngineService.class);
        Messenger messenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("QUESTER", msg.getData().getString("Poruka"));
            }
        });
        i.putExtra("Messenger", messenger);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(i);
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
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

    private void initViewComponents() {
        startServiceButton = (Button) findViewById(R.id.startServiceButton);
        stopServiceButton = (Button) findViewById(R.id.stopServiceButton);

        latitudeText = (TextView) findViewById(R.id.latitude);
        longitudeText = (TextView) findViewById(R.id.longitude);
    }

    private void experimentWithDatabase() {

        SQLiteDatabase db = openOrCreateDatabase("test", 0, null);
        ContextHolder.setContext(this);
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
        flyway.migrate();

        Connection connection = null;
        try {
            connection = new SQLDroidDriver().connect("jdbc:sqlite:" + db.getPath(), new Properties());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLTemplates dialect = new SQLiteTemplates(); // SQL-dialect
        SQLQuery query = new SQLQueryImpl(connection, dialect);

        QExample c = new QExample("example");
        List<String> lastNames = query.from(c)
                .where(c.name.eq("Bob"))
                .list(c.name);
        logger.info(lastNames.size() + "");

    }


    private void useLocationManager() {

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
        latitudeText.setText(Double.toString(location.getLatitude()));
        longitudeText.setText(Double.toString(location.getLongitude()));

    }
}
