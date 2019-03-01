package com.akash.todoapp.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class RxAsyncTask<T> {


    public ObservableEmitter<T> mSubscriber;

    public ObservableEmitter<T> getSubscriber() {
        return mSubscriber;
    }

    public abstract T run() throws Exception;

    public Observable<T> returnObservable() {
        return Observable.create(
                (ObservableOnSubscribe<T>) subscriber -> {
                    mSubscriber = subscriber;
                    try {
                        subscriber.onNext(run());
                        subscriber.onComplete();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}