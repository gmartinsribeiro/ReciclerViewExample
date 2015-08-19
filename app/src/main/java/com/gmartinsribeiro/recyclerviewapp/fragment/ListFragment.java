package com.gmartinsribeiro.recyclerviewapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmartinsribeiro.recyclerviewapp.R;
import com.gmartinsribeiro.recyclerviewapp.adapter.ItemAdapter;
import com.gmartinsribeiro.recyclerviewapp.entity.Item;
import com.gmartinsribeiro.recyclerviewapp.exception.NetworkException;
import com.gmartinsribeiro.recyclerviewapp.network.APIConsumer;

import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    private RecyclerView mItemList;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ItemAdapter mAdapter;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mItemList = (RecyclerView) v.findViewById(R.id.list);

        new DownloadDataTask().execute();

        return v;
    }

    private class DownloadDataTask extends AsyncTask<String, Integer, List<Item>> {
        protected List<Item> doInBackground(String... urls) {
            List<Item> items = null;
            try {
                // Get data from server
                //Usually I do this using Retrofit
                items = APIConsumer.getListFromServer();
            } catch (NetworkException e) {
                e.printStackTrace();
                //TODO Show toast
            }catch (Exception e) {
                e.printStackTrace();
                //TODO Show toast
            }
            return items;
        }

        protected void onProgressUpdate(Integer... progress) {
            //TODO Show loading bar
        }

        protected void onPostExecute(List<Item> result) {
            publishItemsToView(result);
        }
    }

    private void publishItemsToView(List<Item> items) {

        mAdapter = new ItemAdapter(getActivity(), items);

        mItemList.setAdapter(mAdapter);

        // Setup layout manager for items
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        // Attach layout manager to the RecyclerView
        mItemList.setLayoutManager(layoutManager);
    }
}
