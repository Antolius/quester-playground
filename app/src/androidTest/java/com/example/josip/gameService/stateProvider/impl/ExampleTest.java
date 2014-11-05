package com.example.josip.gameService.stateProvider.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by tdubravcevic on 30.10.2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ExampleTest {

    @Test
    public void test(){

        Map<String, String> map = new HashMap<>();

        List<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");

        assertTrue(true);
    }
}
