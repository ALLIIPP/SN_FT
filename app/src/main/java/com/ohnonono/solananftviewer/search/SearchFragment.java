package com.ohnonono.solananftviewer.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.data.network.SolanaNFTRetrofit;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends Fragment {

    private DisposableObserver<ArrayList<SNFTHomepage.DescriptiveCollection>> disposableObserver;
    private final ArrayList<SNFTHomepage.DescriptiveCollection> display_list = new ArrayList<>();
    private final ArrayList<SNFTHomepage.DescriptiveCollection> collection_list = new ArrayList<>();
    private final ArrayList<SNFTHomepage.DescriptiveCollection> final_list = new ArrayList<>();
    private SearchAdapter adapter = new SearchAdapter(display_list);
    private SwipeRefreshLayout swipeRefreshLayout;
    private final static int MAX_LOADED = 20;
    private SearchViewModel viewModel;
    private LinearLayout error_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        error_layout = view.findViewById(R.id.partial_error_search_layout_ll_container);
        CardView retry_cv = view.findViewById(R.id.partial_error_cv_retrycv);
        retry_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCollection();
            }
        });

        TextView title_tv = view.findViewById(R.id.partial_title_tv_title);
        title_tv.setText(getString(R.string.fragment_search_title));

        RecyclerView recyclerView = view.findViewById(R.id.fragment_search_rv_collections);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (display_list.size() < collection_list.size()) {
                        if (display_list.size() + MAX_LOADED > collection_list.size()) {
                            display_list.addAll(collection_list.subList(display_list.size(), collection_list.size()));
                        } else {
                            display_list.addAll(collection_list.subList(display_list.size(), display_list.size() + MAX_LOADED));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);

        EditText search_et = view.findViewById(R.id.partial_searchbar_et);
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                display_list.clear();
                collection_list.clear();
                for (int j = 0; j < final_list.size(); j++) {
                    if (final_list.get(j).getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        collection_list.add(final_list.get(j));
                    }
                }
                if (collection_list.size() < MAX_LOADED) {
                    display_list.addAll(collection_list);
                } else {
                    display_list.addAll(collection_list.subList(0, MAX_LOADED));
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        swipeRefreshLayout = view.findViewById(R.id.fragment_search_srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCollection();
            }
        });

        viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        if (viewModel.getCollections() == null) {
            getCollection();
        } else {
            ArrayList<SNFTHomepage.DescriptiveCollection> collections = viewModel.getCollections();
            final_list.addAll(collections);
            collection_list.addAll(collections);
            if (collection_list.size() < MAX_LOADED) {
                display_list.addAll(collection_list);
            } else {
                display_list.addAll(collection_list.subList(0, MAX_LOADED));
            }
            adapter.notifyDataSetChanged();
        }

        return view;
    }


    public void getCollection() {
        final_list.clear();
        collection_list.clear();
        display_list.clear();

        disposableObserver = SolanaNFTRetrofit.getRetrofit().getRunDowm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<SNFTHomepage.DescriptiveCollection>>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ArrayList<SNFTHomepage.DescriptiveCollection> snftRundowns) {
                        final_list.addAll(snftRundowns);
                        collection_list.addAll(snftRundowns);
                        if (collection_list.size() < MAX_LOADED) {
                            display_list.addAll(collection_list);
                        } else {
                            display_list.addAll(collection_list.subList(0, MAX_LOADED));
                        }
                        adapter.notifyDataSetChanged();
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        viewModel.setCollections(snftRundowns);

                        if (error_layout != null) {
                            error_layout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        if(requireActivity() != null) {
                            Toast.makeText(requireActivity(), getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                        }
                        if (error_layout != null) {
                            error_layout.setVisibility(View.VISIBLE);
                        }
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disposableObserver != null) {
            disposableObserver.dispose();
        }
    }
}
