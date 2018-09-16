package com.rl.rlapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.prof.rssparser.Article;
import com.rl.rlapp.R;
import com.rl.rlapp.business.SecondPresenter;
import com.rl.rlapp.dagger.application.RLApp;
import com.rl.rlapp.ui.adapters.ArticleAdapter;
import com.rl.rlapp.ui.adapters.ItemClickSupport;
import com.rl.rlapp.ui.base.BaseRestoreFragment;

import java.util.List;

public class SecondFragment extends BaseRestoreFragment implements SecondView {

    private ProgressBar pbFirst;
    private ProgressBar pbSecond;
    private SwipeRefreshLayout srlFirst;
    private SwipeRefreshLayout srlSecond;
    private RecyclerView rvFirst;
    private RecyclerView rvSecond;
    private ArticleAdapter adapterFirst;
    private ArticleAdapter adapterSecond;

    @InjectPresenter
    SecondPresenter presenter;

    @ProvidePresenter
    SecondPresenter provideSecondPresenter() {
        return RLApp.getAppComponent().getSecondPresenter();
    }

    public static Fragment getInstance() {
        return new SecondFragment();
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    protected void initView(View root, Bundle savedInstanceState) {
        pbFirst = root.findViewById(R.id.pbFirst);
        srlFirst = root.findViewById(R.id.srlFirst);
        rvFirst = root.findViewById(R.id.rvFirst);
        pbSecond = root.findViewById(R.id.pbSecond);
        srlSecond = root.findViewById(R.id.srlSecond);
        rvSecond = root.findViewById(R.id.rvSecond);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        initFirstList();
        srlFirst.setOnRefreshListener(() -> presenter.getDataFromFirstSource());

        initSecondList();
        srlSecond.setOnRefreshListener(() -> presenter.getDataFromSecondThirdSources());
    }

    private void initSecondList() {
        adapterSecond = new ArticleAdapter(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvSecond.setLayoutManager(llm);
        rvSecond.setAdapter(adapterSecond);

        ItemClickSupport.addTo(rvSecond)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Article item = adapterSecond.getItem(position);
                    if (item != null) {
                        presenter.onItemClicked(item);
                    }
                });
    }

    private void initFirstList() {
        adapterFirst = new ArticleAdapter(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFirst.setLayoutManager(llm);
        rvFirst.setAdapter(adapterFirst);

        ItemClickSupport.addTo(rvFirst)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Article item = adapterFirst.getItem(position);
                    if (item != null) {
                        presenter.onItemClicked(item);
                    }
                });
    }

    @Override
    public void showProgressFirst(boolean b) {
        pbFirst.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressSecond(boolean b) {
        pbSecond.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onArticlesGetFirst(List<Article> articles) {
        adapterFirst.setArticles(articles);
    }

    @Override
    public void onArticlesGetSecond(List<Article> articles) {
        adapterSecond.setArticles(articles);
    }

    @Override
    public void showRefreshingFirst(boolean b) {
        if (!b) {
            srlFirst.setRefreshing(false);
        }
    }

    @Override
    public void showRefreshingSecond(boolean b) {
        if (!b) {
            srlSecond.setRefreshing(false);
        }
    }
}
