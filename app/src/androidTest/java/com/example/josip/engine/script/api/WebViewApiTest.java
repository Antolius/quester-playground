package com.example.josip.engine.script.api;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mozilla.javascript.NativeObject;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class WebViewApiTest {

    private WebViewAPI api;
    private JavascriptAPI jsApi;

    private String message;
    private String persistenceString;
    private NativeObject persistence;

    @Before
    public void setUp() {

        jsApi = new JavascriptAPI();
        persistence = new NativeObject();
        jsApi.setPersistentGameObject(persistence);
        api = new WebViewAPI(Robolectric.application, jsApi);
    }

    @Test
    public void toastDisplayedTroughWebApi() {

        givenToast("Toast message");

        whenShowToast();

        thenToastDisplayed("Toast message");
    }

    @Test
    public void webApiConvertedPersistenceToJsonString() {

        NativeObject object = new NativeObject();
        NativeObject.putProperty(object, "subString", "bla");

        givenElementInPersistence("booleanVariable", true);
        givenElementInPersistence("stringVariable", "bla");
        givenElementInPersistence("intVariable", 1);
        givenElementInPersistence("objectVariable", object);

        whenGetPersistence();

        thenPersistenceStringIs("{\"booleanVariable\":\"true\"," +
                "\"stringVariable\":\"bla\"," +
                "\"intVariable\":\"1\"," +
                "\"objectVariable\":\"{\"subString\":\"bla\"}\"}");
    }

    @Test
    public void webApiConvertedJsonStringToPersistenceObject() throws JSONException {

        givenJson("{\"booleanVariable\":true," +
                "\"stringVariable\":\"bla\"," +
                "\"intVariable\":1," +
                "\"objectVariable\":{\"subString\":\"bla\"}}");

        whenSetPersistence();


        Map<String, Object> map = new HashMap<>();
        map.put("booleanVariable", true);
        map.put("stringVariable", "bla");
        map.put("intVariable", 1);

        //TODO fix test with uncommented part
        //NativeObject object = new NativeObject();
        //NativeObject.putProperty(object, "subString", "bla");
        //map.put("objectVariable", object);

        thenPersistenceHas(map);
    }

    private void givenToast(String message) {

        this.message = message;
    }

    private void givenElementInPersistence(String key, Object value) {

        NativeObject.putProperty(this.persistence, key, value);
    }

    private void givenJson(String json) {

        this.persistenceString = json;
    }

    private void whenShowToast() {

        api.showToast(message);
    }

    private void whenGetPersistence() {

        persistenceString = api.getPersistence();
    }

    private void whenSetPersistence() throws JSONException {

        api.setPersistence(persistenceString);
        persistence = jsApi.getPersistenceObject();
    }

    private void thenToastDisplayed(String message) {

        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(message));
    }

    private void thenPersistenceStringIs(String persistenceString) {

        assertEquals(persistenceString, this.persistenceString);
    }

    private void thenPersistenceHas(Map<String, Object> result) {

        for (String key : result.keySet()) {
            assertEquals(result.get(key), this.persistence.get(key));
        }
    }
}
