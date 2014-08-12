package com.example.josip.model;

import java.util.HashMap;

/**
 * Created by Josip on 11/08/2014!
 */
public class PersistentGameObject {

    private HashMap<String, Object> map = new HashMap<String, Object>();

    public PersistentGameObject putProperty(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Object getProperty(String key) {
        return map.get(key);
    }


}
