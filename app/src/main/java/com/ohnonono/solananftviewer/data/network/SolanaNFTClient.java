package com.ohnonono.solananftviewer.data.network;

import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTOtherCollection;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SolanaNFTClient {

    @GET("stage1/solananft/homepage")
    Observable<SNFTHomepage> getHomepage();

    @GET("stage1/solananft/rundown")
    Observable<ArrayList<SNFTHomepage.DescriptiveCollection>> getRunDowm();


    @GET("stage1/solananft/sales")
    Call<S3Url> getCollection(
            @Query("nftid") String nftid
    );

    @GET("stage1/solananft/othercollection")
    Observable<SNFTOtherCollection> getOtherCollection(
            @Query("other_collection_id") String collection_id
    );

}
