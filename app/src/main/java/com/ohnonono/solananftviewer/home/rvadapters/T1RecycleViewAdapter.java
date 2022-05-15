package com.ohnonono.solananftviewer.home.rvadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.collection.CollectionFragment;
import com.ohnonono.solananftviewer.collection.CollectionViewModel;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;

import java.util.ArrayList;

public class T1RecycleViewAdapter extends RecyclerView.Adapter<T1RecycleViewAdapter.MyViewHolder> {

    private final ArrayList<SNFTHomepage.Collection> list;
    private Context context;

    public T1RecycleViewAdapter(ArrayList<SNFTHomepage.Collection> list) {
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;
        private final CardView cardView;

        public MyViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.item_t1recycleview_cardview);
            imageView = v.findViewById(R.id.item_t1recycleview_img_background);
            textView = v.findViewById(R.id.item_t1recycleview_tv_name);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        return new MyViewHolder(inflater.inflate(R.layout.item_t1recycleview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(list.get(position).getImg()).into(holder.imageView);

        holder.textView.setText(list.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
