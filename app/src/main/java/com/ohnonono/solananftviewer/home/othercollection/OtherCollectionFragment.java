package com.ohnonono.solananftviewer.home.othercollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ohnonono.solananftviewer.R;
import com.ohnonono.solananftviewer.data.network.SolanaNFTRetrofit;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTOtherCollection;
import com.ohnonono.solananftviewer.home.HomeViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class OtherCollectionFragment extends Fragment {
    private DisposableObserver<SNFTOtherCollection> disposableObserver;
    private TextView title_tv;
    private TextView description_tv;
    private RecyclerView collection_rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_othercollection, container, false);
        title_tv = view.findViewById(R.id.fragment_othercollection_tv_title);
        description_tv = view.findViewById(R.id.fragment_othercollection_tv_description);
        collection_rv = view.findViewById(R.id.fragment_othercollection_rv_collection);
        collection_rv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        OtherCollectionViewModel viewModel = new ViewModelProvider(requireActivity()).get(OtherCollectionViewModel.class);
        getCollection(viewModel.getId());
        return view;
    }

    public void getCollection(String id) {
        disposableObserver = SolanaNFTRetrofit.getRetrofit().getOtherCollection(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SNFTOtherCollection>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull SNFTOtherCollection snftOtherCollection) {

                        title_tv.setText(snftOtherCollection.getTitle());
                        description_tv.setText(snftOtherCollection.getDescription());
                        OtherCollectionAdapter adapter = new OtherCollectionAdapter(snftOtherCollection.getCollection());
                        collection_rv.setAdapter(adapter);

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onStop() {
        super.onStop();
        if(disposableObserver != null){
            disposableObserver.dispose();
        }
    }
}
