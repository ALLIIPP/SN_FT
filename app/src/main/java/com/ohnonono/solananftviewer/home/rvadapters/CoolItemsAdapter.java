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
import com.ohnonono.solananftviewer.collection.ShowNFTFragment;
import com.ohnonono.solananftviewer.collection.ShowNFTViewModel;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionMint;
import java.util.ArrayList;

public class CoolItemsAdapter extends RecyclerView.Adapter<CoolItemsAdapter.MyViewHolder> {

    private ArrayList<SNFTCollectionMint> list;
    private Context context;

    public CoolItemsAdapter(ArrayList<SNFTCollectionMint> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_imageView;
        private final TextView name_textView;
        private final CardView container_cardview;

        public MyViewHolder(View v) {
            super(v);
            img_imageView = v.findViewById(R.id.item_coolitems_viewpager_imv);
            name_textView = v.findViewById(R.id.item_coolitems_viewpager_tv_name);
            container_cardview = v.findViewById(R.id.item_coolitems_viewpager_cv_container);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        return new  CoolItemsAdapter.MyViewHolder(inflater.inflate(R.layout.item_coolitems_viewpager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_textView.setText(list.get(position).getName());
        Glide.with((FragmentActivity)context).load(list.get(position).getImage()).into(holder.img_imageView);
        holder.container_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNFTViewModel viewModel = new ViewModelProvider((FragmentActivity)context).get(ShowNFTViewModel.class);
                viewModel.setMint(list.get(position));


                FragmentTransaction transaction = ((FragmentActivity) holder.container_cardview.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_enter_right, R.anim.fragment_exit_left, R.anim.fragment_enter_left, R.anim.fragment_exit_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.activity_main_fl_frame, new ShowNFTFragment());
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
