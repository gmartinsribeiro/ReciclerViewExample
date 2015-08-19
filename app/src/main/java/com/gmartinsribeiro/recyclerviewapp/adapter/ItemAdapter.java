package com.gmartinsribeiro.recyclerviewapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmartinsribeiro.recyclerviewapp.R;
import com.gmartinsribeiro.recyclerviewapp.entity.Item;
import com.gmartinsribeiro.recyclerviewapp.utility.DateUtils;

import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context mContext;
    private final List<Item> mItems;

    public ItemAdapter(Context context, List<Item> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Item item = mItems.get(i);

        viewHolder.tt1.setText(item.getTitle());
        viewHolder.tt2.setText(item.getSubtitle());
        viewHolder.tt3.setText(DateUtils.parseDateToString(item.getDate()));

        viewHolder.currentItem = item;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tt1;
        public TextView tt2;
        public TextView tt3;
        public Item currentItem;

        public ViewHolder(View v) {
            super(v);
            tt1 = (TextView) v.findViewById(R.id.firstLine);
            tt2 = (TextView) v.findViewById(R.id.secondLine);
            tt3 = (TextView) v.findViewById(R.id.thirdLine);
        }
    }

}