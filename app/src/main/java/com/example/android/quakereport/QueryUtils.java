package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<Earthquake> fetchDate(String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extraFromJSON(jsonResponse);
    }

    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating url", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return builder.toString();
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //if the url is null, return earlier
        if (url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(1000); // in milliseconds
            httpURLConnection.setConnectTimeout(1500);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //if the request code is successful, then read the input stream and parse the response
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else
                Log.e(LOG_TAG, "Error respond code " + httpURLConnection.getResponseCode());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON result", e);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    private static List<Earthquake> extraFromJSON(String json) {
        if (TextUtils.isEmpty(json))
            return null;

        List<Earthquake> earthquakes = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONArray array = root.getJSONArray("features");
            for (int i = 0; i < array.length(); i++) {
                JSONObject featureObj = array.getJSONObject(i);
                JSONObject propertiesObj = featureObj.getJSONObject("properties");
                double mag = propertiesObj.getDouble("mag");
                String location = propertiesObj.getString("place");
                long date = propertiesObj.getLong("time");
                String url = propertiesObj.getString("url");
                earthquakes.add(new Earthquake(mag, location, date, url));
            }
            return earthquakes;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

}