package com.example.josip.gameService;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.gameService.locationService.LocationService;
import com.example.josip.gameService.locationService.impl.LocationServiceImpl;
import com.example.josip.gameService.stateProvider.GameStateProvider;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class GameEngineService extends Service implements GameContext {

    Messenger messenger;
    private LocationServiceImpl locationService;
    private GameStateProvider gameStateProvider;
    private GameEngine gameEngine;

    public LocationService getLocationService() {
        return locationService;
    }

    public GameStateProvider getGameStateProvider() {
        return gameStateProvider;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    @Override
    public void onCreate() {
        Log.d("QUESTER", "Is created");

        locationService = new LocationServiceImpl(this, null);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Log.d("QUESTER", "Is started");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        locationService.start();

        messenger = intent.getParcelableExtra("Messenger");
        try {
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("Poruka", "123");
            message.setData(bundle);
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d("QUESTER", getApplicationContext().toString());
        Log.d("QUESTER", getApplicationInfo().toString());

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("QUESTER", "Is destroyed");
        locationService.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        Log.d("QUESTER", "Is bound");
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("QUESTER", "Task removed");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        Log.d("QUESTER", "Is dumped");
        super.dump(fd, writer, args);
    }

    @Override
    public void onLowMemory() {
        Log.d("QUESTER", "Low memory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d("QUESTER", "Trimmed memory");
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("QUESTER", "New config");
        super.onConfigurationChanged(newConfig);
    }

}
