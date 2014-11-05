package com.example.josip.engine.location;

import android.content.Intent;
import android.location.Location;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.example.josip.model.area.CheckpointArea;
import com.example.josip.model.area.Circle;
import com.example.josip.model.enums.MeasurementUnit;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContext;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LocationProcessorTest {


    private LocationProcessor locationProcessor;
    private LocationReachedCallback callback = mock(LocationReachedCallback.class);

    @Test
    public void test() {

        locationProcessor = new LocationProcessor(Robolectric.application, callback);

        Intent intent = new Intent();
        intent.putExtra(LocationProcessor.CHECKPOINT_EXTRA_ID, "1");
        Location location = new Location("test");
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        intent.putExtra(LocationProcessor.TRIGGERING_LOCATION, location);

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setId(1);
        checkpoint.setArea(new CheckpointArea() {
            @Override
            public boolean isInside(Point point) {
                return true;
            }

            @Override
            public double distanceFrom(Point point, MeasurementUnit messurmentUnit) {
                return 0;
            }

            @Override
            public Circle aproximatingCircle() {
                return null;
            }

            @Override
            public JSONObject getJsonData() throws JSONException {
                return null;
            }
        });

        Set<Checkpoint> checkpoints = new HashSet<Checkpoint>();
        checkpoints.add(checkpoint);

        locationProcessor.start(checkpoints);

        locationProcessor.onReceive(Robolectric.application, intent);

        verify(callback).locationReached(checkpoint);

    }
}
