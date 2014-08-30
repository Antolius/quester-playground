package com.example.josip.perisistance.manager;

import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityTestCase;
import android.test.AndroidTestCase;

import com.example.josip.persistance.QuestDetails;
import com.example.josip.persistance.flyway.FlywayDatabaseMigrator;
import com.example.josip.persistance.manager.QuestDetailsRepository;

/**
 * Created by tdubravcevic on 15.8.2014!
 */
public class QuestDetailsRepositoryTest extends ActivityTestCase {

        public void testTest(){

            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase("android_test", null);

            FlywayDatabaseMigrator migrator = new FlywayDatabaseMigrator(database, getInstrumentation().getContext());
            migrator.clean();
            migrator.migrate();

            new QuestDetailsRepository(database).save(new QuestDetails());
        }
}
