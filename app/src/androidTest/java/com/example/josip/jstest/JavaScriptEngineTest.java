package com.example.josip.jstest;

import android.test.AndroidTestCase;

import com.example.josip.model.PersistentGameObject;

import junit.framework.TestCase;

import org.mozilla.javascript.EcmaError;

public class JavaScriptEngineTest extends AndroidTestCase {

    private JavaScriptEngine javaScriptEngine;
    private PersistentGameObject persistence;

    private String script;
    private Object argument;
    private String result;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.persistence = new PersistentGameObject();
        this.javaScriptEngine = new JavaScriptEngine(persistence);
    }

    public void testOnEnterSuccessfullyInvoked() throws Exception {
        givenScript("var onEnter = function(arg){ return 'enter ' + arg }");
        givenArgument("test");
        whenOnEnterInvoked();
        thenResultIs("enter test");
    }

    public void testOnEnterMethodNotDefinedCorrectly() throws Exception {
        givenScript("var notOnEnter = function(arg){ return arg }");
        givenArgument("test");
        try {
            whenOnEnterInvoked();
        } catch (MethodNotFoundException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    public void testOnEnterMethodIsNotImplementedCorrectly() throws Exception {
        givenScript("var onEnter = function(arg){ return argument }");
        givenArgument("test");
        try {
            whenOnEnterInvoked();
        } catch (EcmaError error) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    public void testTest() throws Exception {
        givenScript("var onEnter = function(arg){ " +
                "persistence.putProperty('test', arg); " +
                "return persistence.getProperty('test') }");
        givenArgument("testValue");
        whenOnEnterInvoked();
        thenResultIs("testValue");
        persistence.getProperty("test");
    }


    private void givenScript(String script) {
        this.script = script;
    }

    private void givenArgument(Object argument) {
        this.argument = argument;
    }

    private void whenOnEnterInvoked() throws Exception {
        result = javaScriptEngine.onEnter(script, argument);
    }

    private void thenResultIs(String expectedResult) {
        assertEquals(expectedResult, result);
    }
}
