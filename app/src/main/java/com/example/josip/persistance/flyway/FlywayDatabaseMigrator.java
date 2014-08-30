package com.example.josip.persistance.flyway;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;

/**
 * Created by tdubravcevic on 14.8.2014!
 */
public class FlywayDatabaseMigrator {

    private Flyway flyway;

    public FlywayDatabaseMigrator(SQLiteDatabase database, Context context) {
        ContextHolder.setContext(context);
        flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:" + database.getPath(), "", "");
    }

    public void migrate(){
        flyway.migrate();
    }

    public void clean(){
        flyway.clean();
    }
}
