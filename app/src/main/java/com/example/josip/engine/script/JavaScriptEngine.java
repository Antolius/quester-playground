package com.example.josip.engine.script;

import com.example.josip.engine.script.api.JavascriptAPI;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class JavaScriptEngine {

    private JavascriptAPI api;

    public JavaScriptEngine(JavascriptAPI api) {
        this.api = api;
    }

    public boolean onEnter(String script, Object argument) throws Exception {

        Object functionArgs[] = {argument};
        return runMethodFromScript("onEnter", script, functionArgs);
    }

    public void onExit(String script) throws Exception {

        Object functionArgs[] = {};
        runMethodFromScript("onExit", script, functionArgs);
    }

    private boolean runMethodFromScript(String method, String script, Object functionArgs[]) throws Exception {

        Context context = Context.enter();
        context.setOptimizationLevel(-1);

        try {
            Scriptable scope = context.initStandardObjects();
            ScriptableObject.defineClass(scope, JavascriptAPI.class);
            Object[] arg = {api};
            Scriptable persistence = context.newObject(scope, "API", arg);
            scope.put("API", scope, persistence);
            context.evaluateString(scope, script, "error:", 1, null);

            Object function = scope.get(method, scope);
            if (!(function instanceof Function)) {
                throw new Exception("Method " + method + " not found in script " + script);
            } else {
                Object result = ((Function) function).call(context, scope, scope, functionArgs);
                return Context.toBoolean(result);
            }
        } finally {
            Context.exit();
        }
    }

}