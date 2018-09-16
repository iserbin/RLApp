package com.rl.rlapp.ui.fragments;

import android.support.v4.app.DialogFragment;

import com.arellomobile.mvp.MvpView;
import com.prof.rssparser.Article;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface SecondView extends MvpView {
    void showProgressFirst(boolean b);
    void showProgressSecond(boolean b);
    void showRefreshingFirst(boolean b);
    void showRefreshingSecond(boolean b);
    void onArticlesGetFirst(List<Article> articles);
    void onArticlesGetSecond(List<Article> articles);
    void showDialog(DialogFragment dialog, String tag);
}
