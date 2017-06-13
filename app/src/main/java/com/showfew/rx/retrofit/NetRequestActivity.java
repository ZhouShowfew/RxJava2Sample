package com.showfew.rx.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.showfew.rx.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRequestActivity extends AppCompatActivity {

    String appKey = "082a8832de5f815154314f5825f3f42f";
    private static String baseUrl = "http://v.juhe.cn/weather/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_request);
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });

    }

    private static Retrofit create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);

        return new Retrofit.Builder().baseUrl(baseUrl)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())//
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
            .build();
    }

    private void request() {
        Retrofit retrofit = create();
        JuheApi api = retrofit.create(JuheApi.class);
        Observable<AllCity> observable = api.getAllCity(appKey);
        observable.subscribeOn(Schedulers.io())
            .flatMap(new Function<AllCity, ObservableSource<City>>() {
                @Override
                public ObservableSource<City> apply(AllCity city) throws Exception {
                    ArrayList<City> result = city.getResult();
                    return Observable.fromIterable(result);
                }
            })
            .filter(new Predicate<City>() {
                @Override
                public boolean test(City city) throws Exception {
                    String id = city.getId();
                    if (Integer.parseInt(id) < 5) {
                        return true;
                    }
                    return false;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<City>() {
                @Override
                public void accept(City city) throws Exception {
                    Log.d("zxf","accept(NetRequestActivity:80)-->>" + city);
                }
            });
    }
}
