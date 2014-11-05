package com.example.josip.engine.script;

import android.util.Log;

import com.example.josip.engine.Processor;
import com.example.josip.engine.script.api.JavascriptAPI;
import com.example.josip.engine.state.GameStateProvider;
import com.example.josip.model.Checkpoint;

import java.util.Objects;

/**
 * Created by Josip on 12/10/2014!
 */
public class ScriptProcessor implements Processor {

    private GameStateProvider gameStateProvider;
    private ScriptProcessedCallback scriptProcessedCallback;
    private FileManager fileManager;

    public ScriptProcessor(GameStateProvider gameStateProvider,
                           ScriptProcessedCallback scriptProcessedCallback,
                           FileManager fileManager) {
        this.gameStateProvider = gameStateProvider;
        this.scriptProcessedCallback = scriptProcessedCallback;
        this.fileManager = fileManager;
    }

    public ScriptProcessor(GameStateProvider gameStateProvider, ScriptProcessedCallback scriptProcessedCallback) {
        this.gameStateProvider = gameStateProvider;
        this.scriptProcessedCallback = scriptProcessedCallback;
        this.fileManager = new FileManagerImpl();
    }

    @Override
    public void process(Checkpoint reachedCheckpoint) {

        if (reachedCheckpoint.getEventsScript() == null) {
            return;
        }

        JavascriptAPI api = new JavascriptAPI();
        api.setPersistentGameObject(gameStateProvider.getPersistentGameObject());
        api.setQuestState(gameStateProvider.getCurrentQuestState());
        api.setCurrentCheckpoint(reachedCheckpoint);

        JavaScriptEngine javaScriptEngine = new JavaScriptEngine(api);

        try {
            if (javaScriptEngine.onEnter(fileManager.readFromFile(reachedCheckpoint.getEventsScript()))) {
                scriptProcessedCallback.scriptProcessed(reachedCheckpoint);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //Log.e("QUESTER", "Invoking failed", e);
        }
    }

}
