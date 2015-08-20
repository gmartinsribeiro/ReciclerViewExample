package com.gmartinsribeiro.recyclerviewapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartinsribeiro.recyclerviewapp.R;
import com.gmartinsribeiro.recyclerviewapp.entity.Item;
import com.gmartinsribeiro.recyclerviewapp.exception.NetworkException;
import com.gmartinsribeiro.recyclerviewapp.network.APIClient;
import com.gmartinsribeiro.recyclerviewapp.utility.Connectivity;
import com.gmartinsribeiro.recyclerviewapp.utility.DateUtils;
import com.gmartinsribeiro.recyclerviewapp.utility.DialogUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";
    private static final String ARG_PARAM1 = "id";

    private int mId;
    private TextView title;
    private TextView subtitle;
    private TextView body;
    private TextView date;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Item ID.
     * @return A new instance of fragment DetailsFragment.
     */
    public static DetailsFragment newInstance(int id) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(ARG_PARAM1);
        }

        //Check connection
        if (!Connectivity.isConnected(getActivity())) {
            DialogUtils.createNetErrorDialog(getActivity());
        } else {
            new DownloadDataTask().execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        title = (TextView) v.findViewById(R.id.title);
        subtitle = (TextView) v.findViewById(R.id.subtitle);
        body = (TextView) v.findViewById(R.id.body);
        date = (TextView) v.findViewById(R.id.date);

        return v;
    }

    private class DownloadDataTask extends AsyncTask<String, Integer, Item> {
        protected Item doInBackground(String... urls) {
            Item item = null;
            try {
                // Get data from server
                //Usually I do this using Retrofit
                item = APIClient.getItemFromServer(Integer.toString(mId));
            } catch (NetworkException e) {
                Log.e(TAG, e.getMessage());
                DialogUtils.createApiErrorDialog(getActivity());
            }catch (Exception e) {
                Log.e(TAG, e.getMessage());
                DialogUtils.createInternalErrorDialog(getActivity());
            }
            return item;
        }

        protected void onProgressUpdate(Integer... progress) {
            //TODO Show loading bar
        }

        protected void onPostExecute(Item result) {
            publishItemToView(result);
        }
    }

    private void publishItemToView(Item item) {
        title.setText(item.getTitle());
        subtitle.setText(item.getSubtitle());
        body.setText(item.getBody());
        date.setText(DateUtils.parseDateToString(item.getDate()));
    }

    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

}
