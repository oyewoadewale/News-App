package com.example.hp.newsflash;

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
 * Helper methods related to requesting and receiving news data from GUARDIAN API.
 */

public class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    private QueryUtils() {
    }

    /**
     * Query the GUARDIAN dataset and return a list of {@link News} objects.
     */

    public static List<News> fetchNewsData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);


        // Perform HTTP request to the URL and receive a JSON response back

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}
        List<News> newsList = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}s
        return newsList;
    }

    /**
     * Returns new URL object from the given string URL.
     */

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.d("Error response code: ", String.valueOf(urlConnection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */

    private static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding NEWS to
        List<News> newsList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            // FIRST We Extract the JSONObject associated with the key called "response",
            // which represents a list of response from the GUARDIAN API.
            JSONObject response = baseJsonResponse.getJSONObject("response");

            // Then we Extract the JSONArray associated with the key called "results",
            // which represents a list of results we got from the GUARDIAN API.
            JSONArray resultsArray = response.getJSONArray("results");

            // For each news in the newsArray, create an {@link News} object
            for (int i = 0; i < resultsArray.length(); i++) {


                // Get a single news info at position i within the list of news
                JSONObject currentResults = resultsArray.getJSONObject(i);

                // For a given news, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that news.

                // Extract the value for the key called "webTitle"
                String Title = currentResults.getString("webTitle");

                // Extract the value for the key called "sectionName"
                String category = currentResults.getString("sectionName");

                // Extract the value for the key called "webPublicationDate"
                String date = currentResults.getString("webPublicationDate");

                // Extract the value for the key called "webUrl"
                String url = currentResults.getString("webUrl");

                // Extract the value for the key called "tags"
                JSONArray tagsauthor = currentResults.getJSONArray("tags");

                //check if the name of the author is provided

                String author = "";
                if (tagsauthor.length() != 0) {

                    // if author is provided that is not equal to zero
                    // Extract the value for the key called "author "
                    JSONObject currenttagsauthor = tagsauthor.getJSONObject(0);
                    author = currenttagsauthor.getString("webTitle");
                }

                /* move Check to NewsAdapater ..
                else {
                    author = "No Author ..";
                }*/

                // Create a new {@link News} object with the Title, category, date, url, and author
                //  from the JSON response.
                News news = new News(Title, category, date, url, author);

                // Add the new {@link News} to the list of earthquakes.
                newsList.add(news);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
        }

        // Return the list of news
        return newsList;
    }
}