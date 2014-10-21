package com.example.josip.jstest;

import android.test.AndroidTestCase;

import com.example.josip.engine.script.JavaScriptEngine;
import com.example.josip.engine.script.api.JavascriptAPI;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

import java.util.ArrayList;

public class JSEngineTest extends AndroidTestCase {

    public void testTest(){

        JavascriptAPI api = new JavascriptAPI();

        JavaScriptEngine engine = new JavaScriptEngine(api);
        boolean s = false;
        try {
            s = engine.onEnter("var onEnter = function(arg){" +
                    "API.getPersistenceObject().putProperty('a',true);" +
                    "API.getCurrentCheckpoint().setRoot(true);" +
                    "var a = API.getPersistenceObject().getProperty('a');" +
                    "var b = API.getCurrentCheckpoint().isRoot();" +
                    "var c = API.getVisitedCheckpoints().isEmpty();" +
                    "return a && b && c;" +
                    "}", "bla");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(true, s);
    }
}
