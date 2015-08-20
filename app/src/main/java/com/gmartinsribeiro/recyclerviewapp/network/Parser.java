package com.gmartinsribeiro.recyclerviewapp.network;

import android.util.JsonReader;
import android.util.Log;

import com.gmartinsribeiro.recyclerviewapp.entity.Item;
import com.gmartinsribeiro.recyclerviewapp.exception.DateException;
import com.gmartinsribeiro.recyclerviewapp.utility.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by NB20610 on 20-08-2015.
 */
public class Parser {

    private static final String TAG = "Parser";

    public static List readJsonListStream(InputStream in) throws Exception {
        List items = null;
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try {
            items = readItemsArray(reader);
        }finally{
            reader.close();
        }
        return items;
    }

    public static Item readJsonItemStream(InputStream in) throws Exception {
        Item item = new Item();
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try {
            reader.beginObject();
            if(reader.hasNext()) {
                reader.nextName();

                item = readItem(reader);
            }
        }finally{
            reader.close();
        }
        return item;
    }

    public static List readItemsArray(JsonReader reader) throws Exception {
        List items = new ArrayList();

        reader.beginObject();
        if(reader.hasNext()){
            reader.nextName();

            reader.beginArray();
            while (reader.hasNext()) {
                items.add(readItem(reader));
            }
            reader.endArray();
        }

        return items;
    }

    public static Item readItem(JsonReader reader) throws IOException {
        int id = -1;
        String title = null;
        String subtitle = null;
        String body = null;
        Date date = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("subtitle")) {
                subtitle = reader.nextString();
            } else if (name.equals("body")) {
                body = reader.nextString();
            } else if (name.equals("date")) {
                try {
                    date = DateUtils.parseStringToDate(reader.nextString());
                }catch (DateException e){
                    //Not critical exception
                    //App can continue without date input
                    Log.e(TAG, e.getMessage());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Item(id, title, subtitle, body, date);
    }
}
