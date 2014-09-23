package com.example.josip.archispective;

/**
 * Created by tdubravcevic on 30.8.2014!
 */
public class Overlord {

    public void main(){

        GameStateProvider gameStateProvider = new GameStateProvider();
        LocationService locationService = new LocationService(gameStateProvider);
        GameEngine gameEngine = new GameEngine(gameStateProvider, locationService);

    }
}
