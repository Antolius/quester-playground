package com.example.josip.persistance.manager;

import android.database.sqlite.SQLiteDatabase;

import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLiteTemplates;

import org.sqldroid.SQLDroidDriver;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by tdubravcevic on 14.8.2014!
 */
public class SQLiteRepository {

    protected SQLiteDatabase database;
    protected Connection connection;
    protected SQLQuery query;

    public SQLiteRepository(SQLiteDatabase database) {

        this.database = database;

        try {
            connection = new SQLDroidDriver().connect("jdbc:sqlite:" + database.getPath(), new Properties());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLTemplates dialect = new SQLiteTemplates();
        query = new SQLQueryImpl(connection, dialect);
    }
}
