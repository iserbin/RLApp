package com.rl.rlapp.ui.adapters;

import android.support.v7.util.DiffUtil;

import com.prof.rssparser.Article;

import java.util.List;

public class SimpleDiffCallback extends DiffUtil.Callback {

    private final List<Article> oldList;
    private final List<Article> newList;

    public SimpleDiffCallback(List<Article> oldList, List<Article> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override public int getOldListSize() {
        return oldList.size();
    }

    @Override public int getNewListSize() {
        return newList.size();
    }

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Article objNew = newList.get(newItemPosition);
        Article objOld = oldList.get(oldItemPosition);

        if (objOld == null) {
            return false;
        }

        String link = objOld.getLink();
        if (link == null || link.isEmpty()) {
            return false;
        }

        return link.equals(objNew.getLink());
    }

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldItemPosition, newItemPosition);
    }
}
