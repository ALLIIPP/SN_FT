package com.ohnonono.solananftviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ohnonono.solananftviewer.data.network.SolanaNFTRetrofit;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends Activity {

    private DisposableObserver<SNFTHomepage> disposableObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getCollection();

    }

    public void getCollection() {
        disposableObserver = SolanaNFTRetrofit.getRetrofit().getHomepage()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<SNFTHomepage>()  {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull SNFTHomepage snftHomepage) {

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        if(snftHomepage!=null) {
                            intent.putExtra("homepage", snftHomepage);
                        }

                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                        finish();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                        finish();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
