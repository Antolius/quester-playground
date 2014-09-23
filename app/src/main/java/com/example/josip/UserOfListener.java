package com.example.josip;

/**
 * Created by tdubravcevic on 30.8.2014!
 */
public class UserOfListener{

    MethodInvoker invoker;

    public void main(){

        Listener l = invoker.register(new Listener() {
            @Override
            public void method() {

            }
        });

        invoker.unregister(l);

    }
}
