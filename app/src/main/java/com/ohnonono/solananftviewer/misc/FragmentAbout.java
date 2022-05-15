package com.ohnonono.solananftviewer.misc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ohnonono.solananftviewer.R;

public class FragmentAbout extends Fragment {

    private BottomNavigationView navBar;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        navBar = requireActivity().findViewById(R.id.activity_main_bnv_navbar);


        ImageView backbutton_imgv = view.findViewById(R.id.partial_toolbar_backbar_imgv_backbutton);
        backbutton_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        TextView about_tv = view.findViewById(R.id.fragment_about_tv_about);
        about_tv.setText(Html.fromHtml(getString(R.string.fragment_about_message), HtmlCompat.FROM_HTML_MODE_COMPACT));
        about_tv.setMovementMethod(LinkMovementMethod.getInstance());


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SNFT-About");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);

        CardView cv = view.findViewById(R.id.partial_signup_cv_container);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                if (requireContext().getResources().getConfiguration().locale.getCountry().equals("US")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ftx.us/home/#a=1345915"));
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SNFT-About-SignupFTX");
                    requireActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coinbase.com/join/perez_9l"));
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SNFT-About-SignupCB");
                    requireActivity().startActivity(intent);
                }

                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        });

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (navBar != null) {
            navBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (navBar != null) {
            navBar.setVisibility(View.VISIBLE);
        }
    }
}
