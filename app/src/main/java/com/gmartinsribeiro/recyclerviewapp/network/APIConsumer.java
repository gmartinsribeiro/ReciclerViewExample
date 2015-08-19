package com.gmartinsribeiro.recyclerviewapp.network;

import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import com.gmartinsribeiro.recyclerviewapp.entity.Item;
import com.gmartinsribeiro.recyclerviewapp.exception.NetworkException;
import com.gmartinsribeiro.recyclerviewapp.utility.Constants;
import com.gmartinsribeiro.recyclerviewapp.utility.DateUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 * Comments from Android Developers blog
 */
public class APIConsumer {

    private static final String TAG = "APIConsumer";

    /*
    * Apache HTTP client has fewer bugs on Eclair and Froyo. It is the best choice for these releases.
    * For Gingerbread and better, HttpURLConnection is the best choice. Its simple API and small size makes it great fit for Android.
    * Transparent compression and response caching reduce network use, improve speed and save battery. New applications should use HttpURLConnection;
     */
    public static List<Item> getListFromServer() throws NetworkException {
        URL url = null;
        List<Item> result = null;

        try {
            url = new URL(Constants.API_ADDRESS_LIST);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }
        HttpURLConnection urlConnection = null;

        disableConnectionReuseIfNecessary();

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            result = readJsonListStream(in);
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }finally {
            urlConnection.disconnect();
        }

        return result;
    }

    public static Item getItemFromServer() throws NetworkException {
        URL url = null;
        Item result = null;

        try {
            url = new URL(Constants.API_ADDRESS_ITEM);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }
        HttpURLConnection urlConnection = null;

        disableConnectionReuseIfNecessary();

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            result = readJsonItemStream(in);
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }finally {
            urlConnection.disconnect();
        }

        return result;
    }

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
            item = readItem(reader);
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

    public static Item readItem(JsonReader reader) throws Exception {
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
                date = DateUtils.parseStringToDate(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Item(id, title, subtitle, body, date);
    }

    /*
     *Prior to Froyo, HttpURLConnection had some frustrating bugs.
     *In particular, calling close() on a readable InputStream could poison the connection pool.
    */
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
}
