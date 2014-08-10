package com.example.josip.jstest;

import android.content.Context;
import android.util.Log;

import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;

/**
 * Created by Josip on 09/08/2014.
 */
class Holder<T> {
    private T value;
    public void setValue(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }
}
public class JavaScriptEngine {

    JsEvaluator jsEvaluator;

    final Holder<String> holder = new Holder<String>();

    public JavaScriptEngine(Context context) {
        jsEvaluator = new JsEvaluator(context);
    }

    public String runOnEnterScript(String script, String argument) {
        final String SUPER_MOJO = " onEnter(" + argument + ");";

        jsEvaluator.evaluate(script + SUPER_MOJO, new JsCallback() {
            @Override
            public void onResult(String s) {
                holder.setValue(s);
                holder.notifyAll();
            }
        });

        synchronized (holder) {

            try {
                holder.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return holder.getValue();
    }

}