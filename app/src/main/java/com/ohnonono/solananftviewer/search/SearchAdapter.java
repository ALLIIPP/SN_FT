package com.ohnonono.solananftviewer.search;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.collection.CollectionFragment;
import com.ohnonono.solananftviewer.collection.CollectionViewModel;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    private final ArrayList<SNFTHomepage.DescriptiveCollection> list;
    private Context context;

    //TODO make payload smaller remove lildesc use viewmodel to move data
    //portals messed up

    public SearchAdapter(ArrayList<SNFTHomepage.DescriptiveCollection> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView name_textView;
        private final TextView description_textView;
        private final CardView cardView;


        public MyViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.item_search_cv_container);
            imageView = v.findViewById(R.id.item_search_background);
            name_textView = v.findViewById(R.id.item_search_tv_name);
            description_textView = v.findViewById(R.id.item_search_tv_description);
        }
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        return new SearchAdapter.MyViewHolder(inflater.inflate(R.layout.item_search_recycleview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {

        holder.name_textView.setText(list.get(position).getName());
        holder.description_textView.setText(list.get(position).getDescription().replace("\n                        "," "));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide Keyboard

                InputMethodManager inm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inm.hideSoftInputFromWindow(holder.cardView.getRootView().getWindowToken(),0);

                //Add Fragment CollectionFragment
                CollectionViewModel viewModel = new ViewModelProvider((FragmentActivity) context).get(CollectionViewModel.class);
                viewModel.setId(list.get(position).getId());
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_enter_right, R.anim.fragment_exit_left, R.anim.fragment_enter_left, R.anim.fragment_exit_right);
                transaction.add(R.id.activity_main_fl_frame, new CollectionFragment(),null);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Glide.with(context.getApplicationContext())
                .load(list.get(position).getImg())
                .placeholder(R.drawable.toggle_button_attribute_dark)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
