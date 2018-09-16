package com.rl.rlapp.ui.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.prof.rssparser.Article;
import com.rl.rlapp.R;

import java.util.List;

class ArticleDelegate extends AdapterDelegate<List<Article>> {

    private final LayoutInflater inflater;

    ArticleDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<Article> items, int position) {
        return true;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ArticleHolder(inflater.inflate(R.layout.item_article, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Article> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ArticleHolder vh = (ArticleHolder) holder;
        Article article = items.get(position);
        vh.bind(article);
    }

    private class ArticleHolder extends RecyclerView.ViewHolder {
        private final TextView tvHeader;
        private final TextView tvDetails;

        ArticleHolder(View view) {
            super(view);
            tvHeader = itemView.findViewById(R.id.tvHeader);
            tvDetails = itemView.findViewById(R.id.tvDetails);
        }

        void bind(Article article) {

            if (article == null) {
                hideUI();
                return;
            }

            String title = article.getTitle();
            if (title != null && !title.isEmpty()) {
                tvHeader.setVisibility(View.VISIBLE);
                tvHeader.setText(title);
            }
            else {
                tvHeader.setVisibility(View.INVISIBLE);
            }

            String description = article.getDescription();
            if (description != null && !description.isEmpty()) {
                tvDetails.setVisibility(View.VISIBLE);
                tvDetails.setText(description);
            }
            else {
                tvDetails.setVisibility(View.INVISIBLE);
            }
        }

        private void hideUI() {
            tvHeader.setVisibility(View.INVISIBLE);
            tvDetails.setVisibility(View.INVISIBLE);
        }
    }
}
