package com.example.josip.engine.script;

import android.util.Log;

import com.example.josip.engine.script.api.JavascriptAPI;
import com.example.josip.engine.state.GameStateProvider;
import com.example.josip.model.Checkpoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Josip on 12/10/2014!
 */
public class ScriptProcessor {

    private GameStateProvider gameStateProvider;
    private ScriptProcessedCallback scriptProcessedCallback;

    public ScriptProcessor(GameStateProvider gameStateProvider, ScriptProcessedCallback scriptProcessedCallback) {
        this.gameStateProvider = gameStateProvider;
        this.scriptProcessedCallback = scriptProcessedCallback;
    }

    public void processCheckpoint(Checkpoint reachedCheckpoint) {
        if (reachedCheckpoint.getEventsScript() == null) {
            return;
        }

        JavascriptAPI api = new JavascriptAPI();
        api.setPersistentGameObject(gameStateProvider.getPersistentGameObject());
        api.setQuestState(gameStateProvider.getCurrentQuestState());
        api.setCurrentCheckpoint(reachedCheckpoint);

        JavaScriptEngine javaScriptEngine = new JavaScriptEngine(api);

        try {
            if (javaScriptEngine.onEnter(readFromFile(reachedCheckpoint.getEventsScript()), "Text added to on Enter")) {
                scriptProcessedCallback.scriptProcessed(reachedCheckpoint);
            }
        } catch (Exception e) {
            Log.e("QUESTER", "Invoking failed", e);
        }

        /*
        try {
            if(evaluateScript(loadScript(reachedCheckpoint.getEventsScript()), "onEnterScript")) {
                checkpointVisitedCallback.scriptProcessed(reachedCheckpoint);
            }
        } catch (FileNotFoundException e) {
            if (Log.isLoggable("QUESTER", Log.ERROR)) {
                Log.e("QUESTER", "Script file not found for checkpoint " + reachedCheckpoint, e);
            }
        } finally {
            Context.exit();
        }
        */
    }

    private String readFromFile(File file) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            //fIn = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
            fIn = new FileInputStream(file);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

}
