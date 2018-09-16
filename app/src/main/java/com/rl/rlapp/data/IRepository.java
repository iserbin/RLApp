package com.rl.rlapp.data;

import com.prof.rssparser.Article;

import java.util.List;

import io.reactivex.Observable;

public interface IRepository {
    Observable<List<Article>> getDataFromFirstSource();
    Observable<List<Article>> getDataFromSecondSource();
    Observable<List<Article>> getDataFromThirdSource();
}
