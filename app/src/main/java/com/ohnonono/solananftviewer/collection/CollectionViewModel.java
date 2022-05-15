package com.ohnonono.solananftviewer.collection;

import androidx.lifecycle.ViewModel;

import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionInfo;

import java.util.ArrayList;

public class CollectionViewModel extends ViewModel {

    private String id;
    private final ArrayList<SNFTCollectionInfo.NFTAttribute> search_list = new ArrayList<>();

    public ArrayList<SNFTCollectionInfo.NFTAttribute> getSearch_list() {
        return search_list;
    }

    public void setSearch_list(ArrayList<SNFTCollectionInfo.NFTAttribute> search_list) {
        this.search_list.clear();
        this.search_list.addAll(search_list);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
