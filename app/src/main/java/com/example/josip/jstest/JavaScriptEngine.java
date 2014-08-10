package com.example.josip.jstest;

import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Created by Josip on 09/08/2014.
 */
public class JavaScriptEngine {


    public String runOnEnterScript(String script, String argument) {
        final String SUPER_MOJO = " onEnter(" + argument + ");";

         return doit(script+SUPER_MOJO);
    }

    private String doit(String code)
    {
        Context cx = Context.enter();
        cx.setOptimizationLevel(-1);

        try
        {
            Scriptable scope = cx.initStandardObjects();
            return cx.evaluateString(scope, code, "doit:", 1, null).toString();
        }
        finally
        {
            Context.exit();
        }
    }

}