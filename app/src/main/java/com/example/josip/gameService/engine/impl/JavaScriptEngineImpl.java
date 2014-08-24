package com.example.josip.gameService.engine.impl;

import com.example.josip.gameService.GameContext;
import com.example.josip.gameService.engine.JavaScriptEngine;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Josip on 11/08/2014!
 */
public class JavaScriptEngineImpl implements JavaScriptEngine {

    private GameContext gameContext;
    private Context javaScriptRuntimeContext;

    public JavaScriptEngineImpl(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public boolean runEnterScript(String onEnterScript) {
        initJavaScriptRuntimeContext();

        try {
            return evaluateScript(onEnterScript, "onEnterScript");
        } finally {
            Context.exit();
        }
    }

    @Override
    public boolean runExitScript(String onExitScript) {
        initJavaScriptRuntimeContext();

        try {
            return evaluateScript(onExitScript, "onExitScript");
        } finally {
            Context.exit();
        }
    }

    private void initJavaScriptRuntimeContext() {
        javaScriptRuntimeContext = Context.enter();
        javaScriptRuntimeContext.setOptimizationLevel(-1);
    }

    private Scriptable getScope() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Scriptable scope = javaScriptRuntimeContext.initStandardObjects();

        ScriptableObject.defineClass(scope, JavaScriptApiImpl.class);
        Object[] constructorArguments = {gameContext.getGameStateProvider().getCurrentQuestState(), gameContext.getGameStateProvider().getPersistantGameObject()};
        Scriptable javaScriptApi = javaScriptRuntimeContext.newObject(scope, "JavaScriptApiImpl", constructorArguments);
        scope.put("GameContext", scope, javaScriptApi);

        return scope;
    }

    private boolean evaluateScript(String script, String scriptTag) {
        //TODO: handle exceptions!
        try {
            Scriptable scope = getScope();

            Object result = javaScriptRuntimeContext.evaluateString(scope, script, scriptTag, 1, null);
            return Context.toBoolean(result);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return true;
    }
}
