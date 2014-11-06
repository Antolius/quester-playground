package com.example.josip.engine.script;

import com.example.josip.Logger;
import com.example.josip.engine.Processor;
import com.example.josip.engine.script.api.JavascriptAPI;
import com.example.josip.engine.state.GameStateProvider;
import com.example.josip.model.Checkpoint;

public class ScriptProcessor implements Processor {

    private static final Logger logger = Logger.getLogger(ScriptProcessor.class);

    private GameStateProvider gameStateProvider;
    private ScriptProcessedCallback scriptProcessedCallback;
    private FileManager fileManager;

    public ScriptProcessor(FileManager fileManager,
                           GameStateProvider gameStateProvider,
                           ScriptProcessedCallback scriptProcessedCallback) {
        this.gameStateProvider = gameStateProvider;
        this.scriptProcessedCallback = scriptProcessedCallback;
        this.fileManager = fileManager;
    }

    @Override
    public void process(Checkpoint reachedCheckpoint) {

        if (reachedCheckpoint.getEventsScript() == null) {
            //TODO should it invoke callback here
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
            logger.error("Invoking failed", e);
            //TODO should there be more handling here
        }
    }

}