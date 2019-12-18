package com.rl.rlapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.rl.rlapp.R;
import com.rl.rlapp.business.FirstPresenter;
import com.rl.rlapp.dagger.application.RLApp;
import com.rl.rlapp.ui.base.BaseRestoreFragment;

public class FirstFragment extends BaseRestoreFragment implements FirstView {

    @InjectPresenter
    FirstPresenter presenter;

    @ProvidePresenter
    FirstPresenter provideFirstPresenter() {
        return RLApp.getAppComponent().getFirstPresenter();
    }

    private TextView tvDateTime;
    private TextView tvFeedTitle;

    public static Fragment getInstance() {
        return new FirstFragment();
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    protected void initView(View root, Bundle savedInstanceState) {
        tvDateTime = root.findViewById(R.id.tvDateTime);
        tvFeedTitle = root.findViewById(R.id.tvFeedTitle);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        
    }

    @Override
    public void showDateTime(String s) {
        tvDateTime.setText(s);
    }

    @Override
    public void showItemTitle(String title) {
        tvFeedTitle.setText(title);
    }
}