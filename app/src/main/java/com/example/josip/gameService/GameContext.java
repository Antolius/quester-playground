package com.example.josip.gameService;

import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.gameService.locationService.LocationService;
import com.example.josip.gameService.stateProvider.GameStateProvider;

/**
 * Created by Josip on 11/08/2014!
 */
public interface GameContext {

    public GameStateProvider getGameStateProvider();

    public LocationService getLocationService();

    public GameEngine getGameEngine();

}
