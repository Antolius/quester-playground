package com.example.josip.model;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Josip on 11/08/2014!
 */
public class PersistentGameObject {

    public HashMap<String, Object> map;

    public PersistentGameObject() {
        map = new HashMap<String, Object>();
    }

    public PersistentGameObject(PersistentGameObject persistentGameObject) {
        this.map = persistentGameObject.map;
    }

    public void putProperty(String key, Object value) {
        map.put(key, value);
    }

    public Object getProperty(String key) {
        return map.get(key);
    }

    public Set<String> propertyKeys() {
        return map.keySet();
    }

}