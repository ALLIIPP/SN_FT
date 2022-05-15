package com.ohnonono.solananftviewer.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.ohnonono.solananftviewer.R;

import com.ohnonono.solananftviewer.collection.CollectionFragment;
import com.ohnonono.solananftviewer.collection.CollectionViewModel;
import com.ohnonono.solananftviewer.data.network.SolanaNFTRetrofit;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;
import com.ohnonono.solananftviewer.home.othercollection.OtherCollectionFragment;
import com.ohnonono.solananftviewer.home.othercollection.OtherCollectionViewModel;
import com.ohnonono.solananftviewer.home.rvadapters.CoolItemsAdapter;
import com.ohnonono.solananftviewer.home.rvadapters.T1RecycleViewAdapter;
import com.ohnonono.solananftviewer.home.rvadapters.T2RecycleViewAdapter;
import com.ohnonono.solananftviewer.home.rvadapters.T3RecycleViewAdapter;
import com.ohnonono.solananftviewer.home.rvadapters.T4RecycleViewAdapter;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private DisposableObserver<SNFTHomepage> disposableObserver;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView otherfirst_tv;
    private TextView othersecond_tv;
    private TextView otherthird_tv;
    private RecyclerView top_collections_rv;
    private RecyclerView hot_collections_rv;
    private RecyclerView gainers_collections_rv;
    private RecyclerView otherfirst_collections_rv;
    private RecyclerView othersecond_collections_rv;
    private RecyclerView otherthird_collections_rv;
    private T1RecycleViewAdapter top_adapter;
    private T2RecycleViewAdapter hot_adapter;
    private T3RecycleViewAdapter gainers_adapter;
    private T4RecycleViewAdapter otherfirst_adapter;
    private T4RecycleViewAdapter othersecond_adapter;
    private T4RecycleViewAdapter otherthird_adapter;
    private ViewPager2 cool_items_vp;
    private RelativeLayout error_layout;
    private HomeViewModel viewModel;
    private Button other_first_seeall_btn;
    private Button other_second_seeall_btn;
    private Button other_third_seeall_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        error_layout = view.findViewById(R.id.partial_error_layout_rl_container);
        CardView retry_cv = view.findViewById(R.id.partial_error_cv_retrycv);
        retry_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCollection();
            }
        });

        TextView title_tv = view.findViewById(R.id.partial_title_tv_title);
        title_tv.setText(getString(R.string.fragment_home_title));
        otherfirst_tv = view.findViewById(R.id.fragment_home_tv_other_first);
        othersecond_tv = view.findViewById(R.id.fragment_home_tv_other_second);
        otherthird_tv = view.findViewById(R.id.fragment_home_tv_other_third);

        other_first_seeall_btn = view.findViewById(R.id.fragment_home_btn_otherfirst_see_all);
        other_second_seeall_btn = view.findViewById(R.id.fragment_home_btn_othersecond_see_all);
        other_third_seeall_btn = view.findViewById(R.id.fragment_home_btn_otherthird_see_all);


        top_collections_rv = view.findViewById(R.id.fragment_home_rv_top_collections);
        hot_collections_rv = view.findViewById(R.id.fragment_home_rv_hot_collections);
        gainers_collections_rv = view.findViewById(R.id.fragment_home_rv_gainers_collections);
        otherfirst_collections_rv = view.findViewById(R.id.fragment_home_rv_other_first);
        othersecond_collections_rv = view.findViewById(R.id.fragment_home_rv_other_second);
        otherthird_collections_rv = view.findViewById(R.id.fragment_home_rv_other_third);
        cool_items_vp  = view.findViewById(R.id.fragment_home_vp_coolitems);

        RecyclerView.LayoutManager top_layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager hot_layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager gainers_layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager otherfirst_layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager othersecond_layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager otherthird_layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        top_collections_rv.setLayoutManager(top_layoutManager);
        hot_collections_rv.setLayoutManager(hot_layoutManager);
        gainers_collections_rv.setLayoutManager(gainers_layoutManager);
        otherfirst_collections_rv.setLayoutManager(otherfirst_layoutManager);
        othersecond_collections_rv.setLayoutManager(othersecond_layoutManager);
        otherthird_collections_rv.setLayoutManager(otherthird_layoutManager);

        gainers_collections_rv.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        cool_items_vp.setClipToPadding(false);
        cool_items_vp.setClipChildren(false);

        cool_items_vp.setOffscreenPageLimit(3);
        cool_items_vp.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        cool_items_vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case(MotionEvent.ACTION_DOWN):
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case (MotionEvent.ACTION_UP):
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;

                }
                view.onTouchEvent(event);
                return true;
            }
        });

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.8f + r * 0.15f);
            }
        });
        cool_items_vp.setPageTransformer(compositePageTransformer);

        swipeRefreshLayout = view.findViewById(R.id.fragment_home_srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCollection();
            }
        });

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        if (viewModel.getHomepage() == null) {
            getCollection();
        } else {

            SNFTHomepage homepage = viewModel.getHomepage();

            otherfirst_tv.setText(homepage.getOther_collections().get(0).getTitle());
            othersecond_tv.setText(homepage.getOther_collections().get(1).getTitle());
            otherthird_tv.setText(homepage.getOther_collections().get(2).getTitle());

            top_adapter = new T1RecycleViewAdapter(homepage.getTop_collections());
            top_collections_rv.setAdapter(top_adapter);
            hot_adapter = new T2RecycleViewAdapter(homepage.getHot_collections());
            hot_collections_rv.setAdapter(hot_adapter);
            gainers_adapter = new T3RecycleViewAdapter(homepage.getMovers_collections());
            gainers_collections_rv.setAdapter(gainers_adapter);
            otherfirst_adapter = new T4RecycleViewAdapter((homepage.getOther_collections()).get(0).getCollections());
            otherfirst_collections_rv.setAdapter(otherfirst_adapter);
            othersecond_adapter = new T4RecycleViewAdapter((homepage.getOther_collections()).get(1).getCollections());
            othersecond_collections_rv.setAdapter(othersecond_adapter);
            otherthird_adapter = new T4RecycleViewAdapter((homepage.getOther_collections()).get(2).getCollections());
            otherthird_collections_rv.setAdapter(otherthird_adapter);
            cool_items_vp.setAdapter(new CoolItemsAdapter(homepage.getCool_collection()));

            other_first_seeall_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(homepage.getOther_collections().get(0).getId() != null) {
                        other_fragment_transition(homepage.getOther_collections().get(0).getId());

                    }
                }
            });
            other_second_seeall_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(homepage.getOther_collections().get(1).getId() != null) {
                        other_fragment_transition(homepage.getOther_collections().get(1).getId());
                    }
                }
            });
            other_third_seeall_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(homepage.getOther_collections().get(2).getId() != null)
                    other_fragment_transition(homepage.getOther_collections().get(2).getId());
                }
            });

        }

        return view;
    }

    public void other_fragment_transition(String id){
        Log.i("huhhhhhhhhhhhhhhh","ASDASDASDASDASD"+id);
        OtherCollectionViewModel viewModel = new ViewModelProvider(requireActivity()).get(OtherCollectionViewModel.class);
        viewModel.setId(id);
        FragmentTransaction transaction = (requireActivity()).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter_right, R.anim.fragment_exit_left, R.anim.fragment_enter_left, R.anim.fragment_exit_right);
        transaction.addToBackStack("SEE_ALL");
        transaction.add(R.id.activity_main_fl_frame, new OtherCollectionFragment(),"SEE_ALL");
        transaction.commit();
    }

    public void getCollection() {
        disposableObserver = SolanaNFTRetrofit.getRetrofit().getHomepage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SNFTHomepage>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull SNFTHomepage snftHomepage) {

                        otherfirst_tv.setText(snftHomepage.getOther_collections().get(0).getTitle());
                        othersecond_tv.setText(snftHomepage.getOther_collections().get(1).getTitle());
                        otherthird_tv.setText(snftHomepage.getOther_collections().get(2).getTitle());

                        top_adapter = new T1RecycleViewAdapter(snftHomepage.getTop_collections());
                        top_collections_rv.setAdapter(top_adapter);
                        hot_adapter = new T2RecycleViewAdapter(snftHomepage.getHot_collections());
                        hot_collections_rv.setAdapter(hot_adapter);
                        gainers_adapter = new T3RecycleViewAdapter(snftHomepage.getMovers_collections());
                        gainers_collections_rv.setAdapter(gainers_adapter);
                        otherfirst_adapter = new T4RecycleViewAdapter((snftHomepage.getOther_collections()).get(0).getCollections());
                        otherfirst_collections_rv.setAdapter(otherfirst_adapter);
                        othersecond_adapter = new T4RecycleViewAdapter((snftHomepage.getOther_collections()).get(1).getCollections());
                        othersecond_collections_rv.setAdapter(othersecond_adapter);
                        otherthird_adapter = new T4RecycleViewAdapter((snftHomepage.getOther_collections()).get(2).getCollections());
                        otherthird_collections_rv.setAdapter(otherthird_adapter);
                        cool_items_vp.setAdapter(new CoolItemsAdapter(snftHomepage.getCool_collection()));
                        viewModel.setHomepage(snftHomepage);

                        other_first_seeall_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(snftHomepage.getOther_collections().get(0).getId() != null) {
                                    other_fragment_transition(snftHomepage.getOther_collections().get(0).getId());
                                }
                            }
                        });
                        other_second_seeall_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(snftHomepage.getOther_collections().get(1).getId() != null) {
                                    other_fragment_transition(snftHomepage.getOther_collections().get(1).getId());
                                }
                            }
                        });
                        other_third_seeall_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(snftHomepage.getOther_collections().get(2).getId() != null)
                                    other_fragment_transition(snftHomepage.getOther_collections().get(2).getId());
                            }
                        });

                        swipeRefreshLayout.setRefreshing(false);
                        if (error_layout != null) {
                            error_layout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                            swipeRefreshLayout.setRefreshing(false);
                            if (error_layout != null) {
                                error_layout.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*

        - Price of sol at the top
        - Top Collections (See All)
        - Hot
        - Trending
        - One of Ones

        2 things generative :
            - collection sales (collectionfrag)
                -> thingonother to s3 to lambda /sales
            - home screen options
                -> new .js to s3 to lambda /getHome

        define above list as name we can pull => search solanalysis doc for attribs and return those
            :top collec shows top 5-10 by mkt cap
            :hot show top 5-10 by vol change %


     */
    @Override
    public void onStop() {
        super.onStop();
        Log.i("HOME PAGE : ", "STOPPED");
        if (disposableObserver != null) {
            disposableObserver.dispose();
        }
    }
}

