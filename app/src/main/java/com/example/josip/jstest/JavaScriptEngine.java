package com.example.josip.jstest;

import com.example.josip.model.PersistentGameObject;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class JavaScriptEngine {

    private PersistentGameObject persistentGameObject;

    public JavaScriptEngine() {
        this.persistentGameObject = new PersistentGameObject();
    }

    public JavaScriptEngine(PersistentGameObject persistentGameObject) {
        this.persistentGameObject = persistentGameObject;
    }

    public String onEnter(String script, Object argument) throws Exception {

        Object functionArgs[] = {argument};
        return runMethodFromScript("onEnter", script, functionArgs);
    }

    public void onExit(String script) throws Exception {

        Object functionArgs[] = {};
        runMethodFromScript("onExit", script, functionArgs);
    }

    private String runMethodFromScript(String method, String script, Object functionArgs[]) throws Exception {

        Context context = Context.enter();
        context.setOptimizationLevel(-1);

        try {
            Scriptable scope = context.initStandardObjects();
            ScriptableObject.defineClass(scope, PersistentGameObject.class);
            Object[] arg = { persistentGameObject };
            Scriptable persistence = context.newObject(scope, "PersistentGameObject", arg);
            scope.put("persistence", scope, persistence);
            context.evaluateString(scope, script, "error:", 1, null);

            Object function = scope.get(method, scope);
            if (!(function instanceof Function)) {
                throw new MethodNotFoundException("Method " + method + " not found in script " + script);
            } else {
                Object result = ((Function) function).call(context, scope, scope, functionArgs);
                return Context.toString(result);
            }
        } finally {
            Context.exit();
        }
    }

}