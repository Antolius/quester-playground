package com.example.josip.engine.script;

import com.example.josip.engine.state.GameStateProviderImpl;
import com.example.josip.model.Checkpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ScriptProcessorTest {

    private ScriptProcessedCallback callback = mock(ScriptProcessedCallback.class);
    private FileManager fileManager = mock(FileManager.class);

    @Test
    public void test(){

        when(fileManager.readFromFile(any(File.class)))
                .thenReturn("var onEnter = function(){return true;}");

        ScriptProcessor processor
                = new ScriptProcessor(new GameStateProviderImpl(), callback, fileManager);

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setEventsScript(new File(""));

        processor.process(checkpoint);

        verify(callback).scriptProcessed(checkpoint);
    }
}
