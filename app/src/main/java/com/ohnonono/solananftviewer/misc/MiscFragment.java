package com.ohnonono.solananftviewer.misc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ohnonono.solananftviewer.R;

import java.util.ArrayList;

public class MiscFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_misc,container,false);
        TextView title_tv = view.findViewById(R.id.partial_title_tv_title);
        title_tv.setText(getString(R.string.fragment_miscellaneous_title));
        RecyclerView followstwitter_rv = view.findViewById(R.id.fragment_misc_rv_followstwitter);
        ArrayList<FollowsPojo> followstwitter_list= new ArrayList<>();
        followstwitter_list.add(new FollowsPojo("aeyakovenko","@aeyakovenko","twitter"));
        followstwitter_list.add(new FollowsPojo("armaniferrante","@armaniferrante","twitter"));
        followstwitter_list.add(new FollowsPojo("SBF_FTX","@SBF_FTX","twitter"));
        followstwitter_list.add(new FollowsPojo("DigitalEyesNFT","@DigitalEyesNFT","twitter"));
        followstwitter_list.add(new FollowsPojo("MagicEden_NFT","@MagicEden_NFT","twitter"));
        followstwitter_list.add(new FollowsPojo("SolanartNFT","@SolanartNFT","twitter"));
        followstwitter_list.add(new FollowsPojo("AlphaArtMarket","@AlphaArtMarket","twitter"));

        FollowsAdapter followstwitter_adapter = new FollowsAdapter(followstwitter_list);
        followstwitter_rv.setAdapter(followstwitter_adapter);

        RecyclerView followsdiscord_rv = view.findViewById(R.id.fragment_misc_rv_followsdiscord);
        ArrayList<FollowsPojo> followsdiscord_list = new ArrayList<>();
        followsdiscord_list.add(new FollowsPojo("https://discord.gg/the1","The One","discord"));
        followsdiscord_list.add(new FollowsPojo("https://discord.com/invite/aWrxsRyhFQ","DigitalEyes","discord"));
        followsdiscord_list.add(new FollowsPojo("https://discord.gg/bTvqPHFjgx","Solanart","discord"));
        followsdiscord_list.add(new FollowsPojo("https://discord.gg/magiceden","MagicEden","discord"));
        followsdiscord_list.add(new FollowsPojo("http://discord.gg/HGvk9YDFBy","Alpha Art","discord"));
        FollowsAdapter followsdiscord_adapter = new FollowsAdapter(followsdiscord_list);
        followsdiscord_rv.setAdapter(followsdiscord_adapter);

        CardView moonrank_cv = view.findViewById(R.id.fragment_misc_cv_moonrank);
        CardView dexyz_cv = view.findViewById(R.id.fragment_misc_cv_dexyz);
        CardView solscanio_cv = view.findViewById(R.id.fragment_misc_cv_solscanio);
        CardView phantom_cv = view.findViewById(R.id.fragment_misc_cv_phantom);

        Button gettingstarted_btn = view.findViewById(R.id.fragment_misc_btn_getstarted);
        Button about_btn = view.findViewById(R.id.fragment_misc_btn_about);
        moonrank_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://moonrank.app/"));
                requireActivity().startActivity(intent);
            }
        });
        dexyz_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://de.xyz"));
                requireActivity().startActivity(intent);
            }
        });
        solscanio_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://solscan.io"));
                requireActivity().startActivity(intent);
            }
        });

        phantom_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://phantom.app"));
                requireActivity().startActivity(intent);
            }
        });
        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_enter_right, R.anim.fragment_exit_left, R.anim.fragment_enter_left, R.anim.fragment_exit_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.activity_main_fl_frame, new FragmentAbout());
                transaction.commit();
            }
        });
        gettingstarted_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_enter_right, R.anim.fragment_exit_left, R.anim.fragment_enter_left, R.anim.fragment_exit_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.activity_main_fl_frame, new FragmentGetStarted());
                transaction.commit();
            }
        });
        /*

        de.xyz - twitters - block explorers

         */

        return view;
    }
}
