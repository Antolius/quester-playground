package com.example.josip.engine.location;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GeofenceIntentServiceTest {

    private GeofenceIntentService geofenceIntentService;

    @Test
    public void test(){

        ArrayList<String> ids = new ArrayList<String>();
        ids.add("1");

        Intent intent = new Intent();
        intent.putStringArrayListExtra(LocationProcessor.REGISTERED_CHECKPOINTS_IDS, ids);

        geofenceIntentService = new GeofenceIntentService();

        geofenceIntentService.onHandleIntent(intent);
    }
}
