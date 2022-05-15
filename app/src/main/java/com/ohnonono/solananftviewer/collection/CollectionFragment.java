package com.ohnonono.solananftviewer.collection;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ohnonono.solananftviewer.NumConfig;
import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.data.network.S3Retrofit;
import com.ohnonono.solananftviewer.data.network.S3Url;
import com.ohnonono.solananftviewer.data.network.SolanaNFTRetrofit;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionInfo;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionMint;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionSales;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionFragment extends Fragment {

    private final ArrayList<SNFTCollectionMint> mint_list = new ArrayList<>();
    private final ArrayList<SNFTCollectionMint> display_list = new ArrayList<>();
    private SNFTCollectionSales our_snftCollectionSales;
    private DisposableObserver<SNFTCollectionSales> disposableObserver;
    private CollectionRecycleviewAdapter collection_adapter;

    private final ArrayList<String> attributes_sort_sort = new ArrayList<>();
    private final ArrayList<SNFTCollectionInfo.NFTAttribute> whatwesearchin = new ArrayList<>();
    private final ArrayList<SNFTCollectionInfo.NFTAttribute> sort_list = new ArrayList<>();
    private final SortAttributeAdapter sortAttributeAdapter = new SortAttributeAdapter(sort_list);
    private final SortAttributeSortAdapter sortAttributeSortAdapter = new SortAttributeSortAdapter(attributes_sort_sort);
    private TextView attribute_name;

    private CollectionViewModel viewModel;
    private ImageView pfp;
    private ImageView background;
    private TextView name;
    private TextView description;
    private TextView listed;
    private TextView floor;
    private TextView total;

    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner sort_spn;

    private final static int MAX_PER_PAGE = 20;
    private final static int MAX_ATTRIBUTES_TO_SHOW = 1500;
    private static FirebaseAnalytics mFirebaseAnalytics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());

        swipeRefreshLayout = view.findViewById(R.id.fragment_collection_srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCollection();
            }
        });

        ImageView backbutton_cv = view.findViewById(R.id.partial_toolbar_imgv_backbutton);
        backbutton_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();

            }
        });

        Button clearsort_btn = view.findViewById(R.id.fragment_collection_btn_clearsort);
        total = view.findViewById(R.id.fragment_collection_tv_totalminted);
        attribute_name = view.findViewById(R.id.fragment_sort_tv_attributename);
        ImageView sortbutton_cv = view.findViewById(R.id.partial_toolbar_imgv_sort);
        FrameLayout sort_frame = view.findViewById(R.id.aaaaaaaa);
        sort_frame.setVisibility(View.INVISIBLE);
        sort_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        sort_spn = view.findViewById(R.id.fragment_collection_spn_sortby);
        List<String> sort_spinner_list = new ArrayList<>();
        sort_spinner_list.add("Price Asc.");
        sort_spinner_list.add("Price Dec.");
        sort_spinner_list.add("Rank Asc.");
        sort_spinner_list.add("Rank Dec.");

        ArrayAdapter<String> sort_spinner_list_adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sort_spinner_list);
        sort_spn.setAdapter(sort_spinner_list_adapter);

        sort_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doSpinnerSort(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FrameLayout frameLayout = view.findViewById(R.id.fragment_collection_fl_layout);

        Animation enter_animation = AnimationUtils.loadAnimation(getContext(), R.anim.cardview_y_linear_enter_bottom);
        enter_animation.setDuration(500);
        enter_animation.setFillEnabled(true);
        enter_animation.setFillAfter(true);


        ValueAnimator colorAnimStart = ObjectAnimator.ofInt(frameLayout, "backgroundColor", requireContext().getResources().getColor(R.color.translucent, null), requireContext().getResources().getColor(R.color.fragment_collection_framelayout_endcolor, null));
        colorAnimStart.setDuration(500);
        colorAnimStart.setEvaluator(new ArgbEvaluator());

        ValueAnimator colorAnimEnd = ObjectAnimator.ofInt(frameLayout, "backgroundColor", requireContext().getResources().getColor(R.color.fragment_collection_framelayout_endcolor, null), requireContext().getResources().getColor(R.color.translucent, null));
        colorAnimEnd.setDuration(500);
        colorAnimEnd.setEvaluator(new ArgbEvaluator());


        sortbutton_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colorAnimStart.start();
                sort_frame.startAnimation(enter_animation);

            }
        });


        viewModel = new ViewModelProvider(requireActivity()).get(CollectionViewModel.class);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SNFT-Collection");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, viewModel.getId());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        pfp = view.findViewById(R.id.fragment_collection_img_pfp);
        background = view.findViewById(R.id.fragment_collection_img_background);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_collection_rv_nfts);
        collection_adapter = new CollectionRecycleviewAdapter(display_list);

//         recyclerView.setHasFixedSize(true);
        //    recyclerView.setItemViewCacheSize(20);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i("REACHED END ", "TRUE");
                    // add more -> notify changed

                    if (display_list.size() < mint_list.size()) {
                        if (display_list.size() + MAX_PER_PAGE > mint_list.size()) {
                            display_list.addAll(mint_list.subList(display_list.size(), mint_list.size()));
                        } else {
                            display_list.addAll(mint_list.subList(display_list.size(), display_list.size() + MAX_PER_PAGE));
                        }
                    }
                    collection_adapter.notifyDataSetChanged();
                }

            }
        });

        recyclerView.setAdapter(collection_adapter);
        EditText search_et = view.findViewById(R.id.partial_searchbar_et);
        clearsort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (our_snftCollectionSales != null) {
                            mint_list.clear();
                            mint_list.addAll(our_snftCollectionSales.getMints());

                            display_list.clear();
                            if (mint_list.size() < MAX_PER_PAGE) {
                                display_list.addAll(mint_list);
                            } else {
                                display_list.addAll(mint_list.subList(0, MAX_PER_PAGE));
                            }


                            whatwesearchin.clear();

                            sort_list.clear();
                            if (our_snftCollectionSales.getInfo().getAttrib_count().size() <= MAX_ATTRIBUTES_TO_SHOW) {
                                sort_list.addAll(our_snftCollectionSales.getInfo().getAttrib_count());

                            } else {
                                sort_list.addAll(our_snftCollectionSales.getInfo().getAttrib_count().subList(0, MAX_ATTRIBUTES_TO_SHOW));
                            }

                        }
                    }
                });

                try {
                    executor.shutdown();
                    executor.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {

                } finally {
                    executor.shutdownNow();
                }
                sortAttributeAdapter.notifyDataSetChanged();
                sortAttributeSortAdapter.notifyDataSetChanged();
                collection_adapter.notifyDataSetChanged();
                search_et.setText("");
            }
        });

        name = view.findViewById(R.id.fragment_collections_tv_name);
        description = view.findViewById(R.id.fragment_collection_tv_description);
        floor = view.findViewById(R.id.fragment_collection_tv_floorvalue);
        listed = view.findViewById(R.id.fragment_collection_tv_listedvalue);

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (our_snftCollectionSales != null) {
                    mint_list.clear();
                    for (int j = 0; j < our_snftCollectionSales.getMints().size(); j++) {
                        if (our_snftCollectionSales.getMints().get(j).getName().contains(charSequence)) {
                            mint_list.add(our_snftCollectionSales.getMints().get(j));
                        }
                    }
                    display_list.clear();
                    if (mint_list.size() < MAX_PER_PAGE) {
                        display_list.addAll(mint_list);
                    } else {
                        display_list.addAll(mint_list.subList(0, MAX_PER_PAGE));
                    }

                    collection_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        RecyclerView attribute_sort_rv = view.findViewById(R.id.fragment_sort_rv_attributes_sort);

        attribute_sort_rv.setAdapter(sortAttributeSortAdapter);

        RecyclerView attributes_list_rv = view.findViewById(R.id.fragment_sort_rv_attributes);

        attributes_list_rv.setAdapter(sortAttributeAdapter);

        Button search_btn = view.findViewById(R.id.fragment_sort_button_search);


        Animation end_animation = AnimationUtils.loadAnimation(getContext(), R.anim.cardview_y_linear_exit_bottom);
        end_animation.setDuration(500);
        end_animation.setFillEnabled(true);
        end_animation.setFillBefore(true);
        end_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sort_frame.clearAnimation();
                sort_frame.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ImageView exit_img = view.findViewById(R.id.fragment_sort_img_exit);
        exit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colorAnimEnd.start();
                sort_frame.startAnimation(end_animation);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colorAnimEnd.start();
                sort_frame.startAnimation(end_animation);
                sort_frame.setVisibility(View.INVISIBLE);
                //

                if (whatwesearchin.size() > 0) {
                    mint_list.clear();
                    boolean bool;

                    ArrayList<ArrayList<SNFTCollectionInfo.NFTAttribute>> our_search = new ArrayList<>();
                    for (int i = 0; i < whatwesearchin.size(); i++) {
                        bool = false;
                        for (int j = 0; j < our_search.size(); j++) {
                            if (whatwesearchin.get(i).getAttribute().equals(our_search.get(j).get(0).getAttribute())) {
                                bool = true;
                                our_search.get(j).add(whatwesearchin.get(i));
                                break;
                            }
                        }
                        if (!bool) {
                            ArrayList<SNFTCollectionInfo.NFTAttribute> attrib = new ArrayList<>();
                            attrib.add(whatwesearchin.get(i));
                            our_search.add(attrib);
                        }
                    }

                    getWhatWeSearchinList(our_snftCollectionSales.getMints(), our_search, 0);

                } else {
                    if (mint_list != null) {
                        mint_list.clear();
                        if (our_snftCollectionSales != null) {
                            mint_list.addAll(our_snftCollectionSales.getMints());
                        }
                    }
                    if (display_list != null) {
                        display_list.clear();
                    }
                    if (mint_list.size() < MAX_PER_PAGE) {
                        display_list.addAll(mint_list);
                    } else {
                        display_list.addAll(mint_list.subList(0, MAX_PER_PAGE));
                    }
                    if (collection_adapter != null) {
                        collection_adapter.notifyDataSetChanged();
                    }
                }
            }
        });


        return view;
    }

    public void getWhatWeSearchinList(ArrayList<SNFTCollectionMint> collection, ArrayList<ArrayList<SNFTCollectionInfo.NFTAttribute>> searches, int count) {


        ArrayList<SNFTCollectionMint> array = new ArrayList<>();

        for (int i = 0; i < searches.get(count).size(); i++) {

            for (int j = 0; j < collection.size(); j++) {
                for (int k = 0; k < collection.get(j).getRank_explain().size(); k++) {
                    if (searches.get(count).get(i).getAttribute().equals(collection.get(j).getRank_explain().get(k).getAttribute()) && searches.get(count).get(i).getType().equals(collection.get(j).getRank_explain().get(k).getValue())) {
                        array.add(collection.get(j));
                        break;
                    }
                }
            }
        }

        if (count < searches.size() - 1) {
            count++;
            getWhatWeSearchinList(array, searches, count);

        } else {

            mint_list.addAll(array);
            doSpinnerSortMints(sort_spn.getSelectedItemPosition());
        }


    }

    public void doSpinnerSortMints(int i) {
        if (mint_list != null) {
            switch (i) {
                case 0:
                    mint_list.sort((i1, i2) -> Double.compare(i1.getPrice(), i2.getPrice()));
                    break;

                case 1:
                    mint_list.sort((i1, i2) -> Double.compare(i2.getPrice(), i1.getPrice()));
                    break;

                case 3:
                    mint_list.sort((i1, i2) -> Integer.compare(i1.getRank(), i2.getRank()));
                    break;

                case 2:
                    mint_list.sort((i1, i2) -> Integer.compare(i2.getRank(), i1.getRank()));
                    break;

            }
            display_list.clear();
            if (mint_list.size() < MAX_PER_PAGE) {
                display_list.addAll(mint_list);
            } else {
                display_list.addAll(mint_list.subList(0, MAX_PER_PAGE));
            }
            collection_adapter.notifyDataSetChanged();
        } else {
            sort_spn.setSelection(0);
        }
    }

    public void doSpinnerSort(int i) {
        if (our_snftCollectionSales != null) {
            switch (i) {
                case 0:
                    our_snftCollectionSales.getMints().sort((i1, i2) -> Double.compare(i1.getPrice(), i2.getPrice()));
                    mint_list.sort((i1, i2) -> Double.compare(i1.getPrice(), i2.getPrice()));
                    break;

                case 1:
                    our_snftCollectionSales.getMints().sort((i1, i2) -> Double.compare(i2.getPrice(), i1.getPrice()));
                    mint_list.sort((i1, i2) -> Double.compare(i2.getPrice(), i1.getPrice()));
                    break;

                case 3:
                    our_snftCollectionSales.getMints().sort((i1, i2) -> Integer.compare(i1.getRank(), i2.getRank()));
                    mint_list.sort((i1, i2) -> Integer.compare(i1.getRank(), i2.getRank()));
                    break;

                case 2:
                    our_snftCollectionSales.getMints().sort((i1, i2) -> Integer.compare(i2.getRank(), i1.getRank()));
                    mint_list.sort((i1, i2) -> Integer.compare(i2.getRank(), i1.getRank()));
                    break;

            }
            display_list.clear();
            if (mint_list.size() < MAX_PER_PAGE) {
                display_list.addAll(mint_list);
            } else {
                display_list.addAll(mint_list.subList(0, MAX_PER_PAGE));
            }
            collection_adapter.notifyDataSetChanged();
        } else {
            sort_spn.setSelection(0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disposableObserver != null) {
            disposableObserver.dispose();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if (mint_list.isEmpty()) {
            getCollection();
        }

    }

    public void getCollection() {
        mint_list.clear();
        display_list.clear();
        sort_list.clear();
        attributes_sort_sort.clear();

        SolanaNFTRetrofit.getRetrofit().getCollection(viewModel.getId()).enqueue(new Callback<S3Url>() {
            @Override
            public void onResponse(Call<S3Url> call, Response<S3Url> response) {
                if (response.code() == 200) {
                    disposableObserver = S3Retrofit.getRetrofit().getFromURL(response.body().getUrl())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<SNFTCollectionSales>() {
                                @Override
                                public void onNext(@io.reactivex.annotations.NonNull SNFTCollectionSales snftCollectionSales) {
                                    Activity activity = getActivity();
                                    if (activity != null) {
                                        our_snftCollectionSales = snftCollectionSales;
                                        Glide.with(requireActivity().getApplicationContext())
                                                .load(snftCollectionSales.getInfo().getImage())
                                                .centerCrop()
                                                .override(500)
                                                .into(background);

                                        if (snftCollectionSales.getMints().isEmpty()) {
                                            floor.setText("0");
                                        } else {
                                            floor.setText(getString(R.string.sol_symbol, String.valueOf(our_snftCollectionSales.getMints().get(0).getPrice())));
                                            Glide.with(requireActivity().getApplicationContext())
                                                    .load(snftCollectionSales.getMints().get(snftCollectionSales.getMints().size() - 1).getImage())
                                                    .circleCrop()
                                                    .placeholder(R.color.translucent)
                                                    .into(pfp);

                                        }
                                        name.setText(snftCollectionSales.getInfo().getName());
                                        description.setText(snftCollectionSales.getInfo().getDescription());
                                        listed.setText(NumConfig.integer_format(snftCollectionSales.getMints().size()));
                                        total.setText(NumConfig.integer_format(snftCollectionSales.getInfo().getTotal_mints()));
                                        mint_list.addAll(snftCollectionSales.getMints());

                                        if (mint_list.size() < MAX_PER_PAGE) {
                                            display_list.addAll(mint_list);
                                        } else {
                                            display_list.addAll(mint_list.subList(0, MAX_PER_PAGE));
                                        }

                                        if (snftCollectionSales.getInfo().getAttrib_count().size() <= MAX_ATTRIBUTES_TO_SHOW) {
                                            //
                                            sort_list.addAll(snftCollectionSales.getInfo().getAttrib_count());
                                        } else {
                                            sort_list.addAll(snftCollectionSales.getInfo().getAttrib_count().subList(0, MAX_ATTRIBUTES_TO_SHOW));
                                        }


                                        attributes_sort_sort.addAll(snftCollectionSales.getInfo().getAttrib_titles());
                                        // sortAttributeAdapter.notifyDataSetChanged();
                                        if (snftCollectionSales.getInfo().getAttrib_titles() != null && snftCollectionSales.getInfo().getAttrib_titles().size() != 0) {
                                            sort(snftCollectionSales.getInfo().getAttrib_titles().get(0), false);
                                        }

                                        sortAttributeSortAdapter.notifyDataSetChanged();

                                        if (sort_spn != null) {
                                            doSpinnerSort(sort_spn.getSelectedItemPosition());
                                        }

                                        swipeRefreshLayout.setRefreshing(false);
                                        // TODO update all adapters
                                    }
                                }

                                @Override
                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                    if (requireActivity() != null) {
                                        Toast.makeText(requireActivity(), getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                                    }
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {

                    Toast.makeText(requireContext(), getString(R.string.request_failed), Toast.LENGTH_SHORT).show();

                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<S3Url> call, Throwable t) {

                Toast.makeText(requireContext(), getString(R.string.request_failed), Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void sort(String attribute, boolean append) {

        sort_list.clear();

        Log.i("APPPPPPPPPPPPPPPPPPPPPPPend", "   : " + append);

//        if (append) {

        for (int i = 0; i < our_snftCollectionSales.getInfo().getAttrib_count().size(); i++) {
            if (our_snftCollectionSales.getInfo().getAttrib_count().get(i).getAttribute().equals(attribute)) {
                sort_list.add(our_snftCollectionSales.getInfo().getAttrib_count().get(i));
            }
        }

        attribute_name.setText(requireContext().getString(R.string.fragment_sort_attribute_name, attribute));

 /*       } else {

            if (our_snftCollectionSales.getInfo().getAttrib_count().size() <= MAX_ATTRIBUTES_TO_SHOW) {
                sort_list.addAll(our_snftCollectionSales.getInfo().getAttrib_count());
            } else {
                sort_list.addAll(our_snftCollectionSales.getInfo().getAttrib_count().subList(0, MAX_ATTRIBUTES_TO_SHOW));
            }
            attribute_name.setText("");
        }*/

        sortAttributeAdapter.notifyDataSetChanged();

    }


    public void addSearchItem(SNFTCollectionInfo.NFTAttribute item, boolean bool) {
        if (bool) {
            whatwesearchin.add(item);
        } else {
            for (int i = 0; i < whatwesearchin.size(); i++) {
                if (whatwesearchin.get(i).getAttribute().equals(item.getAttribute()) && whatwesearchin.get(i).getType().equals(item.getType())) {
                    whatwesearchin.remove(i);
                    break;
                }
            }
        }

    }


    class SortAttributeSortAdapter extends RecyclerView.Adapter<CollectionFragment.SortAttributeSortAdapter.MyViewHolder> {

        private final ArrayList<String> list;

        public SortAttributeSortAdapter(ArrayList<String> list) {
            this.list = list;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private final ToggleButton attribute_button;


            public MyViewHolder(View v) {
                super(v);
                //    attribute_textView = v.findViewById(R.id.item_fragment_sort_tv_attrivutes_sort);
                attribute_button = v.findViewById(R.id.item_fragment_sort_attributesort_btn_sort);

            }
        }


        @NonNull
        @Override
        public CollectionFragment.SortAttributeSortAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MyViewHolder(inflater.inflate(R.layout.item_fragment_sort_attributesort, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull CollectionFragment.SortAttributeSortAdapter.MyViewHolder holder, int position) {
            holder.attribute_button.setText(list.get(position));
            holder.attribute_button.setTextOn(list.get(position));
            holder.attribute_button.setTextOff(list.get(position));

            holder.attribute_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    sort(list.get(position), b);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    class SortAttributeAdapter extends RecyclerView.Adapter<CollectionFragment.SortAttributeAdapter.MyViewHolder> {
        private final ArrayList<SNFTCollectionInfo.NFTAttribute> list;
        private Context context;

        public SortAttributeAdapter(ArrayList<SNFTCollectionInfo.NFTAttribute> list) {
            this.list = list;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            private final ToggleButton attribute_button;


            public MyViewHolder(View v) {
                super(v);
                attribute_button = v.findViewById(R.id.item_fragment_sort_attribute_btn);
            }
        }

        @NonNull
        @Override
        public CollectionFragment.SortAttributeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
            return new MyViewHolder(inflater.inflate(R.layout.item_fragment_sort_attribute, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull CollectionFragment.SortAttributeAdapter.MyViewHolder holder, int position) {
            holder.attribute_button.setText(context.getString(R.string.sortfragment_attribute_item_append_attribute, list.get(position).getType(), list.get(position).getAttribute()));
            holder.attribute_button.setTextOn(context.getString(R.string.sortfragment_attribute_item_append_attribute, list.get(position).getType(), list.get(position).getAttribute()));
            holder.attribute_button.setTextOff(context.getString(R.string.sortfragment_attribute_item_append_attribute, list.get(position).getType(), list.get(position).getAttribute()));
            holder.attribute_button.setOnCheckedChangeListener(null);
            holder.attribute_button.setChecked(false);

            for (int i = 0; i < whatwesearchin.size(); i++) {
                if (whatwesearchin.get(i).getAttribute().equals(list.get(position).getAttribute()) && whatwesearchin.get(i).getType().equals(list.get(position).getType())) {
                    holder.attribute_button.setChecked(true);
                    break;
                }
            }

            CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    addSearchItem(list.get(position), b);
                }
            };
            holder.attribute_button.setOnCheckedChangeListener(listener);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

}

