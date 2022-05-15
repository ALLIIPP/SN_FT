package com.ohnonono.solananftviewer.collection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionMint;

import java.util.ArrayList;

public class ShowNFTRecycleViewAdapter extends RecyclerView.Adapter<ShowNFTRecycleViewAdapter.MyViewHolder>{

    private final ArrayList<SNFTCollectionMint.RankExplain> list;

    public ShowNFTRecycleViewAdapter(ArrayList<SNFTCollectionMint.RankExplain> list) {
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView attribute_textView;
        private final TextView attributevalue_textview;


        public MyViewHolder(View v) {
            super(v);
            attribute_textView = v.findViewById(R.id.item_shownft_attributes_tv_attribute);
            attributevalue_textview = v.findViewById(R.id.item_shownft_attributes_tv_attributevalue);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ShowNFTRecycleViewAdapter.MyViewHolder(inflater.inflate(R.layout.item_shownft_attributes, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.attribute_textView.setText(list.get(position).getAttribute());
        holder.attributevalue_textview.setText(list.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
