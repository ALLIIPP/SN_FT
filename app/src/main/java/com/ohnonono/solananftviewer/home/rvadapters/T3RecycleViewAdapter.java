package com.ohnonono.solananftviewer.home.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ohnonono.solananftviewer.NumConfig;
import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.collection.CollectionFragment;
import com.ohnonono.solananftviewer.collection.CollectionViewModel;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;

import java.util.ArrayList;

public class T3RecycleViewAdapter extends RecyclerView.Adapter<T3RecycleViewAdapter.MyViewHolder> {
    private final ArrayList<SNFTHomepage.Collection> list;
    private Context context;

    public T3RecycleViewAdapter(ArrayList<SNFTHomepage.Collection> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;
        private final ImageView imageView;
        private final TextView name_textView;
        private final TextView floor_price_textview;
        private final TextView percent_change_textview;


        public MyViewHolder(View v) {
            super(v);

            linearLayout = v.findViewById(R.id.item_t3recycleview_ll_container);
            imageView = v.findViewById(R.id.item_t3recycleview_img);
            name_textView = v.findViewById(R.id.item_t3recycleview_tv_name);
            floor_price_textview = v.findViewById(R.id.item_t3recycleview_tv_floorprice);
            percent_change_textview = v.findViewById(R.id.item_t3recycleview_tv_pricechange);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        return new T3RecycleViewAdapter.MyViewHolder(inflater.inflate(R.layout.item_t3recycleview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(list.get(position).getImg()).into(holder.imageView);

        holder.name_textView.setText(list.get(position).getName());
        holder.percent_change_textview.setText(context.getString(R.string.percent, NumConfig.percent_format(list.get(position).getAverage_price_1day_change()*100)));
        holder.floor_price_textview.setText(context.getString(R.string.sol, NumConfig.priceamount_format(list.get(position).getAverage_price())));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionViewModel viewModel = new ViewModelProvider((FragmentActivity) context).get(CollectionViewModel.class);
                viewModel.setId(list.get(position).getProject_id());
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_enter_right, R.anim.fragment_exit_left, R.anim.fragment_enter_left, R.anim.fragment_exit_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.activity_main_fl_frame, new CollectionFragment());
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
