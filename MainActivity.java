package com.example.asuneson.leagueoflegends;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.asuneson.leagueoflegends.Utilities.DownloadWebpageTask;


public class MainActivity extends ActionBarActivity {

    String baseWebPage = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
    String APIKey = "?api_key=341da1a5b-e034-48d0-b3d5-8f18763908aa";
    public static Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart(){
        super.onStart();
        thisActivity = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheck (View v){
        String summonerName = ((TextView)findViewById(R.id.SummonerNameInput)).getText().toString();
        String URL = baseWebPage + summonerName + APIKey;
        //TextView dataDump = (TextView)findViewById(R.id.SummonerIDVal);
        //dataDump.setText(URL);
        new DownloadWebpageTask().execute(URL);
    }
}
