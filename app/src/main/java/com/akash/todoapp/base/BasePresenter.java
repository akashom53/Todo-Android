package com.akash.todoapp.base;

import android.util.Log;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T extends  BaseView>{
    WeakReference<T> view;

    public BasePresenter(T view) {
        this.view = new WeakReference<>(view);
    }

    public static <T extends BaseView, P extends BasePresenter> P constructPresenter(T view, Class<P> presenterClass) {
        try {
            return presenterClass.getConstructor(view.getClass()).newInstance(view);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected T getView() {
        return view.get();
    }

    public abstract void ready();

}
