package com.showfew.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BasicSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_sample);
        findViewById(R.id.btn_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservable();
            }
        });
        findViewById(R.id.btn_from_iterable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservableByFromIterable();
            }
        });
        findViewById(R.id.btn_from_defer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservableByDefer();
            }
        });
        findViewById(R.id.btn_from_interval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservableByInterval();
            }
        });
        findViewById(R.id.btn_from_range).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservableByRange();
            }
        });
        findViewById(R.id.btn_from_timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservableByTimer();
            }
        });
        findViewById(R.id.btn_from_repeat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservableByRepeat();
            }
        });
        findViewById(R.id.btn_from_simple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObservableBySimple();
            }
        });
    }


    private void createObservable() {
        //observable创建
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //执行一些操作
                //....
                //执行完毕触发回调，通知观察者
                e.onNext("我来发射数据");
            }
        });
        subscribe(observable);
    }

    private void createObservableByFromIterable() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }
        //相当于多次回调onNext方法，每次传入一个item
        //Collection接口是Iterable接口的子接口，所以所有Collection接口的实现类都可以作为Iterable对象直接传入fromIterable()方法。
        Observable<String> observable = Observable.fromIterable((Iterable<String>) list);
        subscribe(observable);
    }

    //当观察者订阅时，才创建Observable，并且针对每个观察者创建都是一个新的Observable。
    // 以何种方式创建这个Observable对象，当满足回调条件后，就会进行相应的回调。
    private void createObservableByDefer() {
        Observable<String> observable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("hello");
            }
        });
        subscribe(observable);
    }

    //创建一个按固定时间间隔发射整数序列的Observable，可用作定时器。即按照固定2秒一次调用onNext()方法。
    private void createObservableByInterval() {
        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
        subscribeLong(observable);
    }

    //创建一个发射特定整数序列的Observable，第一个参数为起始值，第二个为发送的个数，如果为0则不发送，
    // 负数则抛异常。上述表示发射1到20的数。即调用20次nNext()方法，依次传入1-20数字。
    private void createObservableByRange() {
        Observable<Integer> observable = Observable.range(1, 20);
        subscribeInteger(observable);
    }

    //创建一个Observable，它在一个给定的延迟后发射一个特殊的值，即表示延迟2秒后，调用onNext()方法。
    private void createObservableByTimer() {
        Observable<Long> observable = Observable.timer(2, TimeUnit.SECONDS);
        subscribeLong(observable);
    }

    //创建一个Observable，该Observable的事件可以重复调用。
    private void createObservableByRepeat() {
        Observable<Integer> observable = Observable.just(123).repeat();
        subscribeInteger(observable);
    }

    private void createObservableBySimple() {
        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("zxf", "accept(BasicSampleActivity:144)-->>" + s);

            }
        });

    }

//    Observable (被观察者)只有在被Observer (观察者)订阅后才能执行其内部的相关逻辑,下面代码证实了这一点：


    private void subscribe(Observable observable) {
        //observer创建
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d("zxf", "onNext(BasicSampleActivity:145)-->>" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        //订阅
        observable.subscribe(observer);
    }

    private void subscribeLong(Observable observable) {
        //observer创建
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d("zxf", "onNext(BasicSampleActivity:172)-->>" + aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        //订阅
        observable.subscribe(observer);
    }

    private void subscribeInteger(Observable observable) {
        //observer创建
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d("zxf", "onNext(BasicSampleActivity:199)-->>" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        //订阅
        observable.subscribe(observer);
    }
}
