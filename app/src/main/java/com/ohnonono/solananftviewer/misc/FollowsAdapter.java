package com.ohnonono.solananftviewer.misc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ohnonono.solananftviewer.R;

import java.util.ArrayList;

public class FollowsAdapter extends RecyclerView.Adapter<FollowsAdapter.MyViewHolder>{
    private final ArrayList<FollowsPojo> list;
    private Context context;

    public FollowsAdapter(ArrayList<FollowsPojo> list) {
        this.list = list;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CardView container_cv;
        private final TextView name_tv;
        private TextView description_tv;


        public MyViewHolder(View v) {
            super(v);

            container_cv = v.findViewById(R.id.item_followsrecycleview_cv_container);
            name_tv = v.findViewById(R.id.item_followsrecycleview_tv_name);
          //  description_tv = v.findViewById(R.id.item_followsrecycleview_tv_description);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        return new FollowsAdapter.MyViewHolder(inflater.inflate(R.layout.item_followsrecycleview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_tv.setText(list.get(position).getName());
       // holder.description_tv.setText(list.get(position).getDesc());
        switch (list.get(position).getType()){
            case "discord":
                holder.container_cv.setCardBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.discord,null));
                holder.container_cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(list.get(position).getLink()));
                        context.startActivity(intent);
                    }
                });
                break;
            case "twitter":
                holder.container_cv.setCardBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.twitter,null));
                holder.container_cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        try{
                            context.getPackageManager().getPackageInfo("com.twitter.android",0);
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+list.get(position).getLink()));
                        }catch (Exception e){
                            intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/"+list.get(position).getLink()));
                        }
                        context.startActivity(intent);
                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // @ - desc - type
}
