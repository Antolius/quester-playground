package com.example.josip.engine.location.geofencing;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.josip.engine.location.GeofenceIntentService;
import com.example.josip.engine.location.LocationProcessor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public abstract class GoogleGeofencingClient implements GeofencingClient {

    private Context context;
    private GoogleApiClient apiClient;

    public GoogleGeofencingClient(Context context) {

        this.context = context;

        apiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.d("QUESTER", "client connected");
                        GoogleGeofencingClient.this.onStarted();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("QUESTER", "client connection suspended");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.d("QUESTER", "client connection failed");
                    }
                })
                .build();
    }

    @Override
    public void start() {
        apiClient.connect();
    }

    @Override
    public void stop() {
        apiClient.disconnect();
    }

    @Override
    public void registerGeofences(List<Geofence> geofenceList) {

        ArrayList<String> checkpointIds = new ArrayList<String>();
        for (Geofence geofence : geofenceList) {
            checkpointIds.add(geofence.getRequestId());
        }

        LocationServices.GeofencingApi.addGeofences(
                apiClient,
                geofenceList,
                PendingIntent.getService(
                        context,
                        0,
                        new Intent(context, GeofenceIntentService.class)
                                .putStringArrayListExtra(
                                        LocationProcessor.CHECKPOINTS_ARRAY_LIST_EXTRA_ID,
                                        checkpointIds),
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
        );
    }
}
