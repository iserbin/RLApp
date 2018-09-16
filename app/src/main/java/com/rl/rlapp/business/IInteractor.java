package com.rl.rlapp.business;

import com.prof.rssparser.Article;

import java.util.List;

import io.reactivex.Observable;

public interface IInteractor {
    Observable<List<Article>> getDataFromFirstSource();
    Observable<List<Article>> getDataFromSecondSource();
    Observable<List<Article>> getDataFromThirdSource();
}
