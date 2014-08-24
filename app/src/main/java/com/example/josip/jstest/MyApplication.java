package com.example.josip.jstest;

import android.app.Application;

import com.example.josip.jstest.injectionModules.AndroidModule;
import com.example.josip.jstest.injectionModules.MyModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Josip on 24/08/2014!
 */
public class MyApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(getModules().toArray());
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(
                new AndroidModule(this),
                new MyModule()
        );
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
