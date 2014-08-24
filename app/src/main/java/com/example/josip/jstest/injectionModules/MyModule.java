package com.example.josip.jstest.injectionModules;

import com.example.josip.jstest.activities.MyActivity;

import dagger.Module;

/**
 * Created by Josip on 24/08/2014!
 */

@Module(
        injects = MyActivity.class,
        complete = false
)
public class MyModule {

    //Moji provideri dođu ovdje

}

