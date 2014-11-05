package com.example.josip.engine.location;

import android.content.Intent;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GeofenceIntentServiceTest {

    private GeofenceIntentService geofenceIntentService;

    @Ignore
    @Test
    public void test(){

        Intent intent = mock(Intent.class);

        geofenceIntentService = new GeofenceIntentService();

        geofenceIntentService.onHandleIntent(intent);
    }
}
