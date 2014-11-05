package com.example.josip.engine.multiplayer;

import com.example.josip.engine.state.GameStateProviderImpl;
import com.example.josip.model.Checkpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MultiPlayerProcessorTest {

    private MultiplayerProcessor processor;

    private MultiplayerNetworkAPI networkAPI = mock(MultiplayerNetworkAPI.class);
    private QuestSynchronizedCallback callback = mock(QuestSynchronizedCallback.class);

    @Test
    public void test(){

        processor = new MultiplayerProcessor(new GameStateProviderImpl(), callback, networkAPI);

        Checkpoint checkpoint = new Checkpoint();

        when(networkAPI.processCheckpoint(checkpoint)).thenReturn(true);

        processor.process(checkpoint);

        verify(callback).questSynchronized(checkpoint);
    }
}
