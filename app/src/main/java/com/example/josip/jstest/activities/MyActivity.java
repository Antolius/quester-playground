package com.example.josip.jstest.activities;

import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.josip.QExample;
import com.example.josip.jstest.R;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.CheckpointBuilder;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestBuilder;
import com.example.josip.model.enums.CircleArea;
import com.example.josip.providers.QuestRepository;
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
    private Button startServiceButton, stopServiceButton, startMapBrowseActivity;
    private TextView latitudeText, longitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initViewComponents();

        experiment2();
        /*
        useLocationManager();

        final Intent mapActivityIntent = new Intent(this, MapBrowseActivity.class);
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

        startMapBrowseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mapActivityIntent);
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void initViewComponents() {
        startServiceButton = (Button) findViewById(R.id.startServiceButton);
        stopServiceButton = (Button) findViewById(R.id.stopServiceButton);

        startMapBrowseActivity = (Button) findViewById(R.id.startMapBrowseActivity);

        latitudeText = (TextView) findViewById(R.id.latitude);
        longitudeText = (TextView) findViewById(R.id.longitude);
    }

    private void experiment2() {

        SQLiteDatabase db = openOrCreateDatabase("test", 0, null);
        db.delete("quests", null, null);
        db.delete("connections", null, null);
        db.delete("checkpoints", null, null);
        ContextHolder.setContext(this);
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
        flyway.migrate();

        Checkpoint checkpointA =
                new CheckpointBuilder(-1L)
                        .name("A")
                        .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(0.1)
                                .centerLongitude(0.1)
                                .radius(1.0)
                                .build())
                        .build();

        Checkpoint checkpointB =
                new CheckpointBuilder(-2L)
                        .name("B")
                        .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(2.0)
                                .centerLongitude(2.0)
                                .radius(1.0)
                                .build())
                        .build();

        Quest quest = new QuestBuilder("testQuest")
                .addCheckpoint(checkpointA)
                .addCheckpoint(checkpointB)
                .addConnection(checkpointA, checkpointB)
                .build();

        QuestRepository databaseProvider = new QuestRepository(db);

        quest = databaseProvider.persistQuest(quest);
        Quest returnedQuest = databaseProvider.queryQuest(quest.getId());

        logger.info(returnedQuest.getName());
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
