package com.example.josip.archispective;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdubravcevic on 30.8.2014!
 */
public class LocationService {

    private GameStateProvider gameStateProvider;
    private List<EnterCheckpointListener> listeners = new ArrayList<EnterCheckpointListener>();

    public LocationService(GameStateProvider gameStateProvider) {
        this.gameStateProvider = gameStateProvider;
    }

    public EnterCheckpointListener addEnterCheckpointListener(EnterCheckpointListener listener){
        listeners.add(listener);
        return listener;
    }

    public void removeEnterCheckpointListener(EnterCheckpointListener listener){
        listeners.remove(listener);
    }

    public void metoda(){
        gameStateProvider.getVisibleCheckpoints();
        //analiza
        for(EnterCheckpointListener listener : listeners){
            listener.onEnterCheckpoint(null);
        }
    }
}
