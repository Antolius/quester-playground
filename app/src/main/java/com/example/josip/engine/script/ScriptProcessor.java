package com.example.josip.engine.script;

import android.util.Log;

import com.example.josip.gameService.engine.impl.JavaScriptApiImpl;
import com.example.josip.gameService.stateProvider.GameStateProvider;
import com.example.josip.jstest.JavaScriptEngine;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Created by Josip on 12/10/2014!
 */
public class ScriptProcessor {

    private GameStateProvider gameStateProvider;
    private CheckpointVisitedCallback checkpointVisitedCallback;

    private Context javaScriptRuntimeContext;

    public ScriptProcessor(GameStateProvider gameStateProvider, CheckpointVisitedCallback checkpointVisitedCallback) {
        this.gameStateProvider = gameStateProvider;
        this.checkpointVisitedCallback = checkpointVisitedCallback;
    }

    public void processCheckpoint(Checkpoint reachedCheckpoint) {
        if (reachedCheckpoint.getEventsScript() == null) {
            return;
        }

        initJavaScriptRuntimeContext();

        JavaScriptEngine javaScriptEngine = new JavaScriptEngine(new PersistentGameObject());
        try {
            if(javaScriptEngine.onEnter(readFromFile(reachedCheckpoint.getEventsScript()), "Text added to on Enter")){
                checkpointVisitedCallback.checkpointVisited(reachedCheckpoint);
            }
        } catch (Exception e) {
            Log.e("QUESTER", "Invoking failed", e);
        }

        /*
        try {
            if(evaluateScript(loadScript(reachedCheckpoint.getEventsScript()), "onEnterScript")) {
                checkpointVisitedCallback.checkpointVisited(reachedCheckpoint);
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

    private String loadScript(File scriptFile) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder((int)scriptFile.length());
        Scanner scanner = new Scanner(scriptFile);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine() + lineSeparator);
            }
            return stringBuilder.toString();
        } finally {
            scanner.close();
        }
    }

    private void initJavaScriptRuntimeContext() {
        javaScriptRuntimeContext = Context.enter();
        javaScriptRuntimeContext.setOptimizationLevel(-1);
    }

    private boolean evaluateScript(String script, String scriptTag) {
        //TODO: handle exceptions!
        try {
            Scriptable scope = getScope();

            Object result = javaScriptRuntimeContext.evaluateString(scope, script, scriptTag, 1, null);
            return Context.toBoolean(result);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return true;
    }

    private Scriptable getScope() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Scriptable scope = javaScriptRuntimeContext.initStandardObjects();

        ScriptableObject.defineClass(scope, JavaScriptApiImpl.class);
        Object[] constructorArguments = {gameStateProvider.getCurrentQuestState(), gameStateProvider.getPersistantGameObject()};
        Scriptable javaScriptApi = javaScriptRuntimeContext.newObject(scope, "JavaScriptApiImpl", constructorArguments);
        scope.put("GameContext", scope, javaScriptApi);

        return scope;
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
            String line = "";
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
