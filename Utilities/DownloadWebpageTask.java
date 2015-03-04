package com.example.asuneson.leagueoflegends.Utilities;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.asuneson.leagueoflegends.JSONParsers.SummonerID;
import com.example.asuneson.leagueoflegends.MainActivity;
import com.example.asuneson.leagueoflegends.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by asuneson on 2/16/15.
 */
public class DownloadWebpageTask extends AsyncTask<String, Void, String> {

    Activity parentActivity;

    @Override
    protected String doInBackground(String... urls) {
        parentActivity = MainActivity.thisActivity;
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (parentActivity != null) {
            TextView dataDump = (TextView) parentActivity.findViewById(R.id.SummonerIDVal);
            dataDump.setText(result);
        }else{
            Log.v("DownloadWebpageTask", "Failed to find parentActivity");
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("DownloadWebpageTask", "The response is: " + response);

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException{
        SummonerID summonersFound = new SummonerID().readSummonersStream(stream);

        return summonersFound.summoners.get(0).id + "";
    }
}
