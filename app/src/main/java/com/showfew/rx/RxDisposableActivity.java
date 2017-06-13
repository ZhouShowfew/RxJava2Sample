package com.showfew.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxDisposableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_disposable);
        findViewById(R.id.btn_base_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disposable1();
            }
        });
        findViewById(R.id.btn_rx_operation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disposable2();
            }
        });
    }

    //在RxJava中,用它来切断Observer(观察者)与Observable(被观察者)之间的连接，当调用它的dispose()方法时,
    // 它就会将Observer(观察者)与Observable(被观察者)之间的连接切断, 从而导致Observer(观察者)收不到事件。
    private void disposable1(){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //执行一些操作
                //....
                //执行完毕触发回调，通知观察者
                e.onNext("我来发射数据");
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                d.dispose();
            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    private void disposable2(){
        Disposable disposable = Observable.just("你好").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
        disposable.dispose();
    }


}
