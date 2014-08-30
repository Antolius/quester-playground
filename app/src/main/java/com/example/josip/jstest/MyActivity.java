package com.example.josip.jstest;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;

import com.example.josip.communication.Greeting;
import com.example.josip.communication.HttpRequestTask;
import com.example.josip.gameService.GameEngineService;
import com.example.josip.model.Point;
import com.example.josip.persistance.QuestDetails;
import com.example.josip.persistance.flyway.FlywayDatabaseMigrator;
import com.example.josip.persistance.manager.QuestDetailsRepository;
import com.mysema.query.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

//import org.flywaydb.core.Flyway;
//import org.flywaydb.core.api.android.ContextHolder;

public class MyActivity extends Activity {

    private static final Logger logger = LoggerFactory.getLogger(MyActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SQLiteDatabase database = openOrCreateDatabase("test", 0, null);

        //copyDatabase(this, "test");

        FlywayDatabaseMigrator flyway = new FlywayDatabaseMigrator(database, this);
        //flyway.clean();
        flyway.migrate();


        QuestDetailsRepository repository = new QuestDetailsRepository(database);
        /*
        QuestDetails details = new QuestDetails();
        details.setName("tres");
        details.setPoint(new Point(1.0, 1.0));
        repository.save(details);
        repository.save(details);
        */

        for (QuestDetails questDetails : repository.getAllDetailsSortedByDistance(new Point(0.0, 0.0))) {
            logger.info(questDetails.getId() + ":" + questDetails.getName());
        }

        String MY_AWESOME_STRING = "'Awesome'";
        final String CUSTOM_STRING_FUNCTION = "var onEnter = function (string) { return string += ' and then some!' };";
        Log.d("JSTEST", new JavaScriptEngine().runOnEnterScript(CUSTOM_STRING_FUNCTION, MY_AWESOME_STRING));

        final Intent i = new Intent(this, GameEngineService.class);
        Messenger messenger = new Messenger(new Handler() {
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

        /*
        new HttpRequestTask(){
            @Override
            public void onComplete(Greeting greeting) {
                logger.info(greeting.getContent());
            }
        }.execute();
        */
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

    public void copyDatabase(Context c, String DATABASE_NAME) {
        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("testing", " testing db path " + databasePath);
        Log.d("testing", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                File directory = getExternalFilesDir("");
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                logger.info(directory.getAbsolutePath() + "/" + DATABASE_NAME);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
