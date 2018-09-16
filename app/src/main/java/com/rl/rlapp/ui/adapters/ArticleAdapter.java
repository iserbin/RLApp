package com.rl.rlapp.ui.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.prof.rssparser.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter {

    private static final String TAG = ArticleAdapter.class.getSimpleName();
    private final AdapterDelegatesManager<List<Article>> delegatesManager;
    private List<Article> items = new ArrayList<>();

    public ArticleAdapter(Activity activity) {
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new ArticleDelegate(activity));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setArticles(List<Article> articles) {
        Log.d(TAG, "setArticles() called with: articles = [" + articles + "]");
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SimpleDiffCallback(articles, items));
        items.clear();
        items.addAll(articles);
        diffResult.dispatchUpdatesTo(this);
    }

    public Article getItem(int position) {
        if (position < items.size())
            return items.get(position);
        return null;
    }
}
