package com.example.josip.engine.script;

import com.example.josip.engine.script.api.JavascriptAPI;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.lang.reflect.InvocationTargetException;

public class JavaScriptEngine {

    private JavascriptAPI api;

    public JavaScriptEngine(JavascriptAPI api) {
        this.api = api;
    }

    public boolean onEnter(String script, Object argument) throws Exception {

        return runMethodFromScript("onEnter", script, new Object[]{argument});
    }

    public void onExit(String script) throws Exception {

        runMethodFromScript("onExit", script, new Object[]{});
    }

    private boolean runMethodFromScript(String method, String script, Object functionArgs[]) throws Exception {

        Context context = Context.enter();
        context.setOptimizationLevel(-1);

        try {
            Scriptable scope = context.initStandardObjects();
            addApiToScope(context, scope);
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

    private void addApiToScope(Context context, Scriptable scope) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ScriptableObject.defineClass(scope, JavascriptAPI.class);
        Scriptable persistence = context.newObject(scope, JavascriptAPI.ENTRY_POINT_NAME, new Object[]{api});
        scope.put(JavascriptAPI.ENTRY_POINT_NAME, scope, persistence);
    }

}