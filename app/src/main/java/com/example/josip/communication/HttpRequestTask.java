package com.example.josip.communication;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tdubravcevic on 15.8.2014!
 */
abstract public class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {

    public abstract void onComplete(Greeting greeting);

    @Override
    protected Greeting doInBackground(Void... params) {
        try {
            final String url = "http://rest-service.guides.spring.io/greeting";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Greeting greeting = restTemplate.getForObject(url, Greeting.class);
            return greeting;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Greeting greeting) {
        onComplete(greeting);
    }
}
