package com.ohnonono.solananftviewer.data.network;

import com.ohnonono.solananftviewer.data.network.returntypes.SNFTCollectionSales;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface S3Client {

    @GET
    Observable<SNFTCollectionSales> getFromURL(@Url String url);

}
