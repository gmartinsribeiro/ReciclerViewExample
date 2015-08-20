package com.gmartinsribeiro.recyclerviewapp.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.gmartinsribeiro.recyclerviewapp.network.APIClient;
import com.gmartinsribeiro.recyclerviewapp.utility.Connectivity;
import com.gmartinsribeiro.recyclerviewapp.utility.DialogUtils;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListFragment.
     */
    public static ListFragment newInstance() {
        return new ListFragment();
    }

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

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Check connection
        if (!Connectivity.isConnected(getActivity())) {
            DialogUtils.createNetErrorDialog(getActivity());
        } else {
            new DownloadDataTask().execute();
        }
    }

    private class DownloadDataTask extends AsyncTask<String, Integer, List<Item>> {
        protected List<Item> doInBackground(String... urls) {
            List<Item> items = null;
            try {
                // Get data from server
                //Usually I do this using Retrofit
                items = APIClient.getListFromServer();
            } catch (NetworkException e) {
                Log.e(TAG, e.getMessage());
                DialogUtils.createApiErrorDialog(getActivity());
            }catch (Exception e) {
                Log.e(TAG, e.getMessage());
                DialogUtils.createInternalErrorDialog(getActivity());
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
        mItemList.setHasFixedSize(true);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }
}
