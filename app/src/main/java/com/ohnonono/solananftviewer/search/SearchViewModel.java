package com.ohnonono.solananftviewer.search;

import androidx.lifecycle.ViewModel;

import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    private ArrayList<SNFTHomepage.DescriptiveCollection> collections;

    public ArrayList<SNFTHomepage.DescriptiveCollection> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<SNFTHomepage.DescriptiveCollection> collections) {
        this.collections = collections;
    }
}
