package com.example.josip.gameService.engine.impl;

import com.example.josip.gameService.engine.JavaScriptEvaluator;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Created by Josip on 11/08/2014!
 */
public class JavaScriptEvaluatorImpl implements JavaScriptEvaluator {

    @Override
    public Object evaluateScript(String script, String scriptTag) {
        Context javaScriptRuntimeContext = Context.enter();
        javaScriptRuntimeContext.setOptimizationLevel(-1);
        try {
            Scriptable scope = javaScriptRuntimeContext.initStandardObjects();
            return javaScriptRuntimeContext.evaluateString(scope, script, scriptTag, 1, null);
        } finally {
            Context.exit();
        }
    }

}