package com.example.josip.engine;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.josip.engine.location.LocationProcessor;
import com.example.josip.engine.location.LocationReachedCallback;
import com.example.josip.engine.multiplayer.MultiplayerProcessor;
import com.example.josip.engine.multiplayer.QuestSynchronizedCallback;
import com.example.josip.engine.script.ScriptProcessedCallback;
import com.example.josip.engine.script.ScriptProcessor;
import com.example.josip.engine.state.GameStateProvider;
import com.example.josip.engine.state.GameStateProviderImpl;
import com.example.josip.engine.state.archive.MockedQuestProvider;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.QuestState;
import com.example.josip.model.graph.QuestGraphUtils;

import java.util.Set;

public class GameEngine extends Service {

    private LocationProcessor locationProcessor;
    private ScriptProcessor scriptProcessor;
    private MultiplayerProcessor multiplayerProcessor;

    private GameStateProvider gameStateProvider;

    private Context context;

    @Override
    public void onCreate() {

        this.context = this;
        gameStateProvider = new GameStateProviderImpl();
        gameStateProvider.setCurrentQuestState(
                new QuestState(MockedQuestProvider.getMockedQuest(this).getQuestGraph()));

        locationProcessor = new LocationProcessor(this, new LocationReachedCallback() {
            @Override
            public void locationReached(Checkpoint reachedCheckpoint) {
                scriptProcessor.processCheckpoint(reachedCheckpoint);
            }
        });

        scriptProcessor = new ScriptProcessor(gameStateProvider, new ScriptProcessedCallback() {
            @Override
            public void scriptProcessed(Checkpoint visitedCheckpoint) {
                multiplayerProcessor.synchronize(visitedCheckpoint);
            }
        });

        multiplayerProcessor = new MultiplayerProcessor("http://www.test.com", gameStateProvider,
                new QuestSynchronizedCallback() {
                    @Override
                    public void questSynchronized(Checkpoint visitedCheckpoint) {
                        GameEngine.this.checkpointVisited(visitedCheckpoint);
                    }
                }
        );

        super.onCreate();
    }

    private void checkpointVisited(Checkpoint visitedCheckpoint) {

        Toast.makeText(context, "Visited checkpoint" + visitedCheckpoint.getName(), Toast.LENGTH_LONG).show();

        gameStateProvider.getCurrentQuestState().getVisitedCheckpoints().add(visitedCheckpoint);

        Set<Checkpoint> nextCheckpoints = gameStateProvider
                .getCurrentQuestState()
                .getQuestGraph()
                .getChildren(visitedCheckpoint);

        if (nextCheckpoints == null || nextCheckpoints.isEmpty()) {
            Toast.makeText(context, "No more checkpoints", Toast.LENGTH_LONG).show();
            return;
        }

        locationProcessor.trackLocation(nextCheckpoints);

        gameStateProvider.saveState();

        //TODO: okinuti notifikaciju ili tako nesto :)
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationProcessor.start(
                QuestGraphUtils.getRootCheckpoints(
                        gameStateProvider.getCurrentQuestState().getQuestGraph())
        );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        locationProcessor.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
