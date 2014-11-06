package com.example.josip.engine.script;

import com.example.josip.engine.script.api.JavascriptAPI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mozilla.javascript.NativeObject;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class JavaScriptEngineTest {

    private JavaScriptEngine engine;

    private String script;
    private Object[] arguments;
    private NativeObject persistence;
    private boolean result;

    @Before
    public void setUp() {

        JavascriptAPI api = new JavascriptAPI();
        persistence = new NativeObject();
        api.setPersistentGameObject(persistence);

        arguments = null;
        engine = new JavaScriptEngine(api);
    }

    @Test
    public void noArgOnEnterSuccessfullyRun() throws Exception {

        givenScript("var onEnter = function(){return true;}");

        whenOnEnter();

        thenResultIs(true);
    }

    @Test
    public void onEnterSuccessfullyRunWithArguments() throws Exception {

        givenScript("var onEnter = function(result){return result;}");
        givenArguments(true);

        whenOnEnter();

        thenResultIs(true);
    }

    @Test
    public void onEnterSuccessfullyRunWithElementInPersistence() throws Exception {

        givenScript("var onEnter = function(){return API.getPersistenceObject().result;}");
        givenElementInPersistence("result", true);

        whenOnEnter();

        thenResultIs(true);
    }

    @Test
    public void noMethodFoundWhenOnEnterScriptWronglyDefined() throws Exception {

        givenScript("var onEntering = function(){return true;}");

        try {
            whenOnEnter();
        } catch (Exception e) {
            assertEquals("Method onEnter not found in script var onEntering = function(){return true;}", e.getMessage());
        }
    }

    private void givenScript(String script) {

        this.script = script;
    }

    private void givenArguments(Object... arguments) {

        this.arguments = arguments;
    }

    private void givenElementInPersistence(String key, Object value) {

        NativeObject.putProperty(this.persistence, key, value);
    }

    private void whenOnEnter() throws Exception {

        if (arguments != null) {
            result = engine.onEnter(script, arguments);
            return;
        }
        result = engine.onEnter(script);
    }

    private void thenResultIs(boolean expectedResult) {

        assertEquals(expectedResult, result);
    }
}
