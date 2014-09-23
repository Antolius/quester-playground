package com.example.josip;

import java.util.List;

/**
 * Created by tdubravcevic on 30.8.2014!
 */
public class MethodInvoker {

    private List<Listener> listeners;

    public Listener register(Listener l){
        listeners.add(l);
        return l;
    }

    void kadHocu(){
        for(Listener l : listeners){
            l.method();
        }
    }

    public void unregister(Listener l){
        listeners.remove(l);
    }
}
