package com.example.josip.jstest;

/**
 * koji autoboxing covijece!
 * Created by Josip on 09/08/2014.
 */
public class SuperTestable {

    SuperMockable dependency;

    public void setDependency(SuperMockable dependency) {
        this.dependency = dependency;
    }

    public int cantTestMe() {
        return dependency.getAbsolutalyNotMockedValue();
    }
}
