package com.akash.todoapp.base;

import java.lang.ref.WeakReference;

class BasePresenterInternal<T extends  BaseView> {

    WeakReference<T> view;

    public static <T extends BaseView, P extends BasePresenter> P constructPresenter(T view) {
//        P presenter = (P) new BasePresenterInternal<T>();
//        presenter.view = new WeakReference<>(view);
//        return presenter;
        return null;
    }

    protected T getView() {
        return view.get();
    }

}
