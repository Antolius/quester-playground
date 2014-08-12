package com.example.josip.gameService.engine;

import com.example.josip.gameService.GameContext;

/**
 * Created by Josip on 11/08/2014!
 */
public interface JavaScriptEngine {

    /**
     * @param onEnterScript String containing JavaScript function to run on enter event
     * @return false if enter event was halted by the run script, true otherwise
     */
    public boolean runEnterScript(String onEnterScript);

    /**
     * @param onExitScript String containing JavaScript function to run on exit event
     * @return false if exit event was halted by the run script, true otherwise
     */
    boolean runExitScript(String onExitScript);
}
