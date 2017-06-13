package com.showfew.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class RxOperationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_operation);
        findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationMap();
            }
        });
        findViewById(R.id.btn_flat_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationFlatMap();
            }
        });
        findViewById(R.id.btn_from_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationFilter();
            }
        });
        findViewById(R.id.btn_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationTake();
            }
        });
        findViewById(R.id.btn_doonnext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationDoOnNext();
            }
        });
    }

    //map()操作符，就是把原来的Observable对象转换成另一个Observable对象，同时将传输的数据进行一些灵活的操作，方便Observer获得想要的数据形式。
    private void operationMap(){
        Observable<Integer> observable = Observable.just("hello").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return s.length();
            }
        });
        subscribeInteger(observable);
    }

    //flatMap()对于数据的转换比map()更加彻底，如果发送的数据是集合，flatmap()重新生成一个Observable对象，
    // 并把数据转换成Observer想要的数据形式。它可以返回任何它想返回的Observable对象。
    private void operationFlatMap(){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }
        Observable<Object> observable = Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        });
        subscribe(observable);
    }

    //filter()操作符根据test()方法中，根据自己想过滤的数据加入相应的逻辑判断，返回true则表示数据满足条件，
    //返回false则表示数据需要被过滤。最后过滤出的数据将加入到新的Observable对象中，方便传递给Observer想要的数据形式。
    private void operationFilter(){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }
        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).filter(new Predicate<Object>() {
            @Override
            public boolean test(Object s) throws Exception {
                String newStr = (String) s;
                if (newStr.charAt(5) - '0' > 5) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d("zxf","accept(RxOperationActivity:77)-->>" + o);
            }
        });
    }

    //take()操作符：输出最多指定数量的结果。
    private void operationTake(){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }
        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).take(5).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object s) throws Exception {
                Log.d("zxf","accept(RxOperationActivity:97)-->>" + s);
            }
        });
    }

    //doOnNext()允许我们在每次输出一个元素之前做一些额外的事情。
    private void operationDoOnNext(){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }
        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).take(5).doOnNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d("zxf","准备工作");
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object s) throws Exception {
                Log.d("zxf","accept(RxOperationActivity:121)-->>" + s);
            }
        });
    }

    private void subscribe(Observable observable) {
        //observer创建
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d("zxf","onNext(RxOperationActivity:60)-->>" + s);
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
                Log.d("zxf","onNext(RxOperationActivity:114)-->>" + integer);
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
