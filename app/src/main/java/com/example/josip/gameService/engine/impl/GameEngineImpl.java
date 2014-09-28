package com.example.josip.gameService.engine.impl;

import com.example.josip.gameService.GameContext;
import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.gameService.engine.JavaScriptEngine;
import com.example.josip.model.Checkpoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class GameEngineImpl implements GameEngine {

    public static final long ON_UPDATE_DELAY = 1000 * 60; //one minute

    private GameContext gameContext;

    private Timer onUpdateEventsTimer = new Timer();

    private HashMap<Checkpoint, TimerTask> aciveUpdateTimerTasks = new HashMap<Checkpoint, TimerTask>();

    private JavaScriptEngine javaScriptEngine;

    public GameEngineImpl(GameContext gameContext) {
        this.gameContext = gameContext;

        //TODO: injection!
        javaScriptEngine = new JavaScriptEngineImpl(gameContext);
    }

    @Override
    public boolean onCheckpointAreaEnter(final Checkpoint checkpoint) {

        String onEnterScript = readFile(checkpoint.getEventsScript());
        if (!onEnterScript.isEmpty()) {
            if (!javaScriptEngine.runEnterScript(onEnterScript)) {
                //enter event was halted by the onEnterScript
                return false;
            }
        }

        TimerTask updateEventTask = new TimerTask() {
            @Override
            public void run() {
                onUpdate(checkpoint);
            }
        };

        onUpdateEventsTimer.scheduleAtFixedRate(updateEventTask, ON_UPDATE_DELAY, ON_UPDATE_DELAY);
        aciveUpdateTimerTasks.put(checkpoint, updateEventTask);

        return  true;
    }

    @Override
    public void onCheckpointAreaExit(final Checkpoint checkpoint) {

        String onExitScript = readFile(checkpoint.getEventsScript());
        if (!onExitScript.isEmpty()) {
            if (!javaScriptEngine.runExitScript(onExitScript)) {
                //exit event was halted by the onEnterScript
                return;
            }
        }

        TimerTask updateEventTask = aciveUpdateTimerTasks.get(checkpoint);

        if (updateEventTask != null) {
            updateEventTask.cancel();
            aciveUpdateTimerTasks.remove(checkpoint);
        }

    }

    private void onUpdate(Checkpoint checkpoint) {

    }

    //TODO: extract into utils class
    private String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
            //TODO: handle
        } catch (IOException e) {
            //TODO: handle
        }

        return stringBuilder.toString();
    }
}
