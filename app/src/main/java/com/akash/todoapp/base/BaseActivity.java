package com.akash.todoapp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private ProgressDialog progressDialog;
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        findViews();
        if (!(this instanceof BaseView)) {
            throw new RuntimeException("View must implement BaseView or an interface extending BaseView");
        } else {
            this.presenter = T.constructPresenter((BaseView) this, getPresenterClass());
        }
        onViewReady();
        this.presenter.ready();
    }

    protected abstract Class<T> getPresenterClass();

    protected abstract void onViewReady();

    protected abstract void findViews();

    protected abstract int getContentView();

    public final void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public final void showLoader(String title, String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public final void hideLoader() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

}
