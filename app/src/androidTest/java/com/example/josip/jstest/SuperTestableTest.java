package com.example.josip.jstest;

/**
 * Created by Josip on 09/08/2014.
 */

import static org.mockito.Mockito.when;

import android.test.AndroidTestCase;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SuperTestableTest extends AndroidTestCase {

    @Mock
    SuperMockable mock;

    @InjectMocks
    SuperTestable testable;

    public void setUp() {
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().getPath());

        MockitoAnnotations.initMocks(this);
    }

    public void testTest() {

        when(mock.getAbsolutalyNotMockedValue()).thenReturn(145);

        assertEquals(145, testable.cantTestMe());
    }

}
