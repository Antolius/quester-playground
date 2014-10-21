package com.example.josip.jstest;

import android.test.AndroidTestCase;

import com.example.josip.engine.script.JavaScriptEngine;
import com.example.josip.engine.script.api.JavascriptAPI;
import com.example.josip.model.PersistentGameObject;

public class JSEngineTest extends AndroidTestCase {

    public void testTest(){

        JavascriptAPI api = new JavascriptAPI(new PersistentGameObject());

        JavaScriptEngine engine = new JavaScriptEngine(api);
        boolean s = false;
        try {
            s = engine.onEnter("var onEnter = function(arg){" +
                    "API.getPersistenceObject().putProperty('a',true);" +
                    "API.getCurrentCheckpoint().setRoot(true);" +
                    "var b = API.getPersistenceObject().getProperty('a');" +
                    "var c = API.getCurrentCheckpoint().isRoot();" +
                    "return API.getVisitedCheckpoints().isEmpty();" +
                    "}", "bla");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(true, s);
    }
}
