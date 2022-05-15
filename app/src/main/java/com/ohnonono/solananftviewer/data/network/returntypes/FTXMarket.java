package com.ohnonono.solananftviewer.data.network.returntypes;

import com.google.gson.annotations.SerializedName;

public class FTXMarket {

    @SerializedName("result")
    private FTXResult result;

    public FTXResult getResult() {
        return result;
    }

    public void setResult(FTXResult result) {
        this.result = result;
    }

    public static class FTXResult{
        @SerializedName("last")
        private double price;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }


}
