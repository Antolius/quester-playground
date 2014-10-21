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

        JavaScriptEngine engine = new JavaScriptEngine(getJavascriptAPI());
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

    private JavascriptAPI getJavascriptAPI() {
        JavascriptAPI api = new JavascriptAPI();
        api.setPersistentGameObject(new PersistentGameObject());
        QuestState questState = new QuestState();
        questState.setVisitedCheckpoints(new ArrayList<Checkpoint>());
        api.setQuestState(questState);
        api.setCurrentCheckpoint(new Checkpoint());
        return api;
    }
}
