package com.ohnonono.solananftviewer.collection;

import android.content.Context;
import android.util.Log;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionMint;

import java.util.ArrayList;

public class CollectionRecycleviewAdapter extends RecyclerView.Adapter<CollectionRecycleviewAdapter.MyViewHolder> {

    private final ArrayList<SNFTCollectionMint> list;
    private Context context;

    public CollectionRecycleviewAdapter(ArrayList<SNFTCollectionMint> list) {
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name_textView;
        private final TextView price_textview;
        private final ImageView imageView;
     //   private final TextView rank_textview;
        private final CardView container_cardview;
  //      private final CardView marketname_cardview;
 //       private final TextView marketname_textview;

        public MyViewHolder(View v) {
            super(v);
            name_textView = v.findViewById(R.id.item_collectionrecycleview_tv_name);
            price_textview = v.findViewById(R.id.item_collectionrecycleview_tv_price);
            imageView = v.findViewById(R.id.item_collectionrecycleview_img_image);
          //  rank_textview = v.findViewById(R.id.item_collectionrecycleview_tv_rank_value);
            container_cardview = v.findViewById(R.id.item_collectionrecycleview_cv_container);
        //    marketname_cardview = v.findViewById(R.id.item_collectionrecycleview_cv_marketname);
        //    marketname_textview = v.findViewById(R.id.item_collectionrecycleview_tv_marketname);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        return new CollectionRecycleviewAdapter.MyViewHolder(inflater.inflate(R.layout.item_collectionrecycleview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_textView.setText(list.get(position).getName());
        holder.price_textview.setText(context.getString(R.string.sol_symbol, String.valueOf(list.get(position).getPrice())));
        Log.i("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL","  : "+list.get(position).getImage());
     //   holder.rank_textview.setText(context.getString(R.string.fragment_collection_tv_rank, String.valueOf(list.get(position).getRank())));
        Glide.with(holder.imageView.getContext().getApplicationContext())
                .load(list.get(position).getImage())
                .placeholder(R.drawable.toggle_button_attribute_dark)
          //      .format(DecodeFormat.PREFER_RGB_565)
              //  .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(DrawableTransitionOptions.withCrossFade())
             //   .override(200,200)
                .into(holder.imageView);



        //TODO create viewmodel in shownftfragment and get title/description from there

  /*      switch (list.get(position).getMarket_name()){
            case "digitaleyes":
                holder.marketname_textview.setText("DigitalEyes");
                holder.marketname_cardview.setCardBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.digitaleyes,null));
                break;
            case "magiceden":
                holder.marketname_textview.setText("Magic Eden");
                holder.marketname_cardview.setCardBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.magiceden,null));
                break;
            case "alphaart":
                holder.marketname_textview.setText("Alpha Art");
                holder.marketname_cardview.setCardBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.alphaart,null));
                break;
            case "solanart":
                holder.marketname_textview.setText("Solanart");
                holder.marketname_cardview.setCardBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.solanart,null));
                break;
        } */

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

        //TODO .ADD AFTER .ADDTOBACKSTACK

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
