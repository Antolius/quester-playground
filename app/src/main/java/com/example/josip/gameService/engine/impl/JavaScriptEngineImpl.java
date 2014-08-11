package com.example.josip.gameService.engine.impl;

import com.example.josip.gameService.GameContext;
import com.example.josip.gameService.engine.JavaScriptEvaluator;
import com.example.josip.gameService.engine.JavaScriptEngine;

/**
 * Created by Josip on 11/08/2014!
 */
public class JavaScriptEngineImpl implements JavaScriptEngine {

    //TODO: injection!
    private JavaScriptEvaluator javaScriptEvaluator = new JavaScriptEvaluatorImpl();


    @Override
    public boolean runEnterScript(GameContext gameContext, String onEnterScript) {
        return true;
    }

    @Override
    public boolean runExitScript(GameContext gameContext, String onExitScript) {
        return true;
    }
}
