package com.example.josip.engine;

import android.content.Intent;
import android.location.Location;

import com.example.josip.engine.location.LocationProcessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GameEngineTest {

    @Test
    public void test() throws InterruptedException {

        GameEngine engine = new GameEngine();

        engine.onCreate();

        engine.onStartCommand(new Intent(), 0, 0);

        Intent intent = new Intent();
        intent.putExtra(LocationProcessor.TRIGGERING_CHECKPOINT_ID, "1");
        Location location = new Location("test");
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        intent.putExtra(LocationProcessor.TRIGGERING_LOCATION, location);

        Thread.sleep(15000);

        engine.locationProcessor.onReceive(Robolectric.application, intent);

    }
}
