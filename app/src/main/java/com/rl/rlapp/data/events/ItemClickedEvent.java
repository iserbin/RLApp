package com.rl.rlapp.data.events;

import com.prof.rssparser.Article;

public class ItemClickedEvent {

    private final Article article;

    public ItemClickedEvent(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}
