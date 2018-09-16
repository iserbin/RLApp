package com.rl.rlapp.business;

import com.prof.rssparser.Article;
import com.rl.rlapp.data.IRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class InteractorImpl implements IInteractor {

    private final IRepository repository;

    public InteractorImpl(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<Article>> getDataFromFirstSource() {
        return repository.getDataFromFirstSource()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Article>> getDataFromSecondSource() {
        return repository.getDataFromSecondSource()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Article>> getDataFromThirdSource() {
        return repository.getDataFromThirdSource()
                .subscribeOn(Schedulers.io());
    }
}
