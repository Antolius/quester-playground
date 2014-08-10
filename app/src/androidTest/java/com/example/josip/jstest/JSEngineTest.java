package com.example.josip.jstest;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

/**
 * Created by tdubravcevic on 10.8.2014.
 */
public class JSEngineTest extends AndroidTestCase {

    public void testTest(){
        JavaScriptEngine engine = new JavaScriptEngine();
        String s = engine.runOnEnterScript("var onEnter = function(s){ return s}","bla");
        assertEquals("bla", s);
    }
}
