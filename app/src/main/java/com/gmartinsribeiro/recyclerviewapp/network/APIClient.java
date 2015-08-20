package com.gmartinsribeiro.recyclerviewapp.network;

import android.util.Log;

import com.gmartinsribeiro.recyclerviewapp.entity.Item;
import com.gmartinsribeiro.recyclerviewapp.exception.NetworkException;
import com.gmartinsribeiro.recyclerviewapp.utility.Constants;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 * Comments from Android Developers blog
 */
public class APIClient {

    private static final String TAG = "APIClient";

    /*
    * Apache HTTP client has fewer bugs on Eclair and Froyo. It is the best choice for these releases.
    * For Gingerbread and better, HttpURLConnection is the best choice. Its simple API and small size makes it great fit for Android.
    * Transparent compression and response caching reduce network use, improve speed and save battery. New applications should use HttpURLConnection;
     */
    public static List<Item> getListFromServer() throws NetworkException {
        URL url;
        List<Item> result = null;

        try {
            url = new URL(Constants.API_ADDRESS_LIST);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            result = Parser.readJsonListStream(in);
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }finally {
            urlConnection.disconnect();
        }

        return result;
    }

    public static Item getItemFromServer(String id) throws NetworkException {
        URL url;
        Item result = null;

        try {
            url = new URL(Constants.API_ADDRESS_ITEM.replace("[id]", id));
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            result = Parser.readJsonItemStream(in);
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e.getMessage());
        }finally {
            urlConnection.disconnect();
        }

        return result;
    }

}
