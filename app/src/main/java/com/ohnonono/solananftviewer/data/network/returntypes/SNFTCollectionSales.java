package com.ohnonono.solananftviewer.data.network.returntypes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SNFTCollectionSales {

    @SerializedName("info")
    private SNFTCollectionInfo info;
    @SerializedName("mints")
    private ArrayList<SNFTCollectionMint> mints;

    public SNFTCollectionInfo getInfo() {
        return info;
    }

    public void setInfo(SNFTCollectionInfo info) {
        this.info = info;
    }

    public ArrayList<SNFTCollectionMint> getMints() {
        return mints;
    }

    public void setMints(ArrayList<SNFTCollectionMint> mints) {
        this.mints = mints;
    }
}
