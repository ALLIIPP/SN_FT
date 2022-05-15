package com.ohnonono.solananftviewer.data.network;

import com.google.gson.annotations.SerializedName;

public class S3Url {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
