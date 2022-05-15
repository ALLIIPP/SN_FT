package com.ohnonono.solananftviewer.collection;

import androidx.lifecycle.ViewModel;

import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionMint;

public class ShowNFTViewModel extends ViewModel {

    private SNFTCollectionMint mint;

    public SNFTCollectionMint getMint() {
        return mint;
    }

    public void setMint(SNFTCollectionMint mint) {
        this.mint = mint;
    }
}
