package com.example.josip.jstest;

import android.test.AndroidTestCase;

public class JSEngineTest extends AndroidTestCase {

    public void testTest(){
        JavaScriptEngine engine = new JavaScriptEngine();
        boolean s = false;
        try {
            s = engine.onEnter("var onEnter = function(arg){return true;}", "bla");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(true, s);
    }
}
