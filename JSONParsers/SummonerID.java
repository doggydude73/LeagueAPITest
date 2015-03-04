package com.example.asuneson.leagueoflegends.JSONParsers;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asuneson on 2/16/15.
 */
public class SummonerID {

    public List<BaseSummonerInformation> summoners;

    public SummonerID(){
        summoners = new ArrayList<BaseSummonerInformation>();
    }

    public class BaseSummonerInformation{
        public long id;
        public String name;
        public int profileIconId;
        public long revisionDate;
        public int summonerLevel;


        public BaseSummonerInformation(long id, String name, int profileIconId, long revisionDate, int summonerLevel){
            this.id = id;
            this.name = name;
            this.profileIconId = profileIconId;
            this.revisionDate = revisionDate;
            this.summonerLevel = summonerLevel;
        }

    }

    public SummonerID readSummonersStream(InputStream in) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        SummonerID players;
        try{
            reader.beginObject();
            players = readSummonerObjectList(reader);
            reader.endObject();
        }
        finally{
            reader.close();
        }

        return players;
    }

    private SummonerID readSummonerObjectList(JsonReader reader) throws IOException{

        SummonerID players = new SummonerID();
        while (reader.hasNext()){
            reader.nextName();
            players.summoners.add(readSummoner(reader));
        }

        return players;
    }

    private BaseSummonerInformation readSummoner(JsonReader reader) throws IOException{

        long id = -1;
        String name = "";
        int profileIconId = -1;
        long revisionDate = -1;
        int summonerLevel = -1;

        reader.beginObject(); // Open up a realm object
        while (reader.hasNext()){
            String fieldName = reader.nextName();

            if (fieldName.equals("id")){
                String idIncoming = reader.nextString();
                id = Long.parseLong(idIncoming);
            }else if (fieldName.equals("name")){
                name = reader.nextString();
            }else if (fieldName.equals("profileIconId")){
                profileIconId = reader.nextInt();
            }else if (fieldName.equals("revisionDate")){
                String revDate = reader.nextString();
                revisionDate = Long.parseLong(revDate);
            }else if (fieldName.equals("summonerLevel")){
                summonerLevel = reader.nextInt();
            }else{
                reader.skipValue();
            }
            //Log.v("RealmStatusParser", fieldName);
        }
        reader.endObject();

        return new BaseSummonerInformation(id, name, profileIconId, revisionDate, summonerLevel);
    }


}
