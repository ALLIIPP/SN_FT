package com.ohnonono.solananftviewer.home.othercollection;

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
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTOtherCollection;
import com.ohnonono.solananftviewer.home.rvadapters.T4RecycleViewAdapter;

import java.util.ArrayList;

public class OtherCollectionAdapter extends RecyclerView.Adapter<OtherCollectionAdapter.MyViewHolder>{
    private ArrayList<SNFTOtherCollection.OtherCollection_Collection> list;
    private Context context;

    public OtherCollectionAdapter(ArrayList<SNFTOtherCollection.OtherCollection_Collection> list){
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView name_textView;
        private final TextView description_textView;
        private final CardView cardView;


        public MyViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.item_t4recycleview_cv_container);
            imageView = v.findViewById(R.id.item_t4recycleview_img_background);
            name_textView = v.findViewById(R.id.item_t4recycleview_tv_name);
            description_textView = v.findViewById(R.id.item_t4recycleview_tv_description);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        return new OtherCollectionAdapter.MyViewHolder(inflater.inflate(R.layout.item_t4recycleview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(list.get(position).getImg())
                .override(500,500)
                .into(holder.imageView);

        holder.name_textView.setText(list.get(position).getName());
        holder.description_textView.setText(list.get(position).getDescription().replace("\n                        "," "));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionViewModel viewModel = new ViewModelProvider((FragmentActivity) context).get(CollectionViewModel.class);
                viewModel.setId(list.get(position).getId());
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
