package com.example.josip.engine.script;

import com.example.josip.engine.state.GameStateProvider;
import com.example.josip.model.Checkpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ScriptProcessorTest {

    private ScriptProcessor processor;

    private ScriptProcessedCallback callback = mock(ScriptProcessedCallback.class);
    private FileManager fileManager = mock(FileManager.class);

    private Checkpoint checkpoint;

    @Before
    public void setUp() {

        processor = new ScriptProcessor(fileManager, new GameStateProvider(), callback);
    }

    @Test
    public void whenNoScriptDefinedCallbackIsNotInvoked() {

        givenCheckpointWithNoScript();

        whenProcessCheckpoint();

        thenCallbackIsNotInvoked();
    }

    @Test
    public void whenScriptIsSuccessfullyProcessedCallbackIsInvoked() throws IOException {

        givenCheckpointWithScript("var onEnter = function(){return true;}");

        whenProcessCheckpoint();

        thenCallbackIsInvoked();
    }

    @Test
    public void whenScriptIsUnSuccessfullyProcessedCallbackIsNotInvoked() throws IOException {

        givenCheckpointWithScript("var onEnter = function(){return false;}");

        whenProcessCheckpoint();

        thenCallbackIsNotInvoked();
    }

    private void givenCheckpointWithNoScript() {

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setEventsScript(null);

        this.checkpoint = checkpoint;
    }

    private void givenCheckpointWithScript(String script) throws IOException {

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setEventsScript(new File(""));

        when(fileManager.readFromFile(any(File.class))).thenReturn(script);

        this.checkpoint = checkpoint;
    }

    private void whenProcessCheckpoint() {

        processor.process(checkpoint);
    }

    private void thenCallbackIsInvoked() {

        verify(callback).scriptProcessed(checkpoint);
    }

    private void thenCallbackIsNotInvoked() {

        verify(callback, never()).scriptProcessed(checkpoint);
    }
}
