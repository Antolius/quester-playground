package com.example.josip.engine.multiplayer;

import com.example.josip.model.Checkpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MultiPlayerProcessorTest {

    private MultiplayerProcessor processor;

    private MultiplayerNetworkAPI networkAPI = mock(MultiplayerNetworkAPI.class);
    private QuestSynchronizedCallback callback = mock(QuestSynchronizedCallback.class);

    private Checkpoint checkpoint;

    @Before
    public void setUp() {

        processor = new MultiplayerProcessor(networkAPI, callback);
    }

    @Test
    public void processingSuccessful() {

        givenCheckpoint();
        givenAPIReturns(true);

        whenProcessCheckpoint();

        thenCallbackIsInvoked();
    }

    @Test
    public void processingUnSuccessful() {

        givenCheckpoint();
        givenAPIReturns(false);

        whenProcessCheckpoint();

        thenCallbackIsNotInvoked();
    }

    private void givenCheckpoint() {

        this.checkpoint = new Checkpoint();
    }

    private void givenAPIReturns(Boolean result) {

        when(networkAPI.processCheckpoint(checkpoint)).thenReturn(result);
    }

    private void whenProcessCheckpoint() {

        processor.process(checkpoint);
    }

    private void thenCallbackIsInvoked() {

        verify(callback).questSynchronized(checkpoint);
    }

    private void thenCallbackIsNotInvoked() {

        verify(callback, never()).questSynchronized(checkpoint);
    }
}
