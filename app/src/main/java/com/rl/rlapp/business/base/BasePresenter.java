package com.rl.rlapp.business.base;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    private static final String TAG = BasePresenter.class.getSimpleName();
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void disposeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(View view) {
        super.attachView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
