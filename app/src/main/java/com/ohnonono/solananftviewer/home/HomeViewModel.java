package com.ohnonono.solananftviewer.home;

import androidx.lifecycle.ViewModel;

import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;

public class HomeViewModel extends ViewModel  {

    private SNFTHomepage homepage;

    public SNFTHomepage getHomepage() {
        return homepage;
    }

    public void setHomepage(SNFTHomepage homepage) {
        this.homepage = homepage;
    }
}
