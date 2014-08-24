package com.example.josip.jstest.injectionModules;

import android.content.Context;
import android.location.LocationManager;

import com.example.josip.jstest.MyApplication;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josip on 24/08/2014!
 */

@Module(library = true)
public class AndroidModule {

    private final MyApplication application;

    public AndroidModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
//    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }
}
