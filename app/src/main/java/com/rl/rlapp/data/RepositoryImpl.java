package com.rl.rlapp.data;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RepositoryImpl implements IRepository {

    private static final String TAG = RepositoryImpl.class.getSimpleName();

    public RepositoryImpl() {
    }

    @Override
    public Observable<List<Article>> getDataFromFirstSource() {

        return Observable.fromPublisher(observer -> {
            Parser parser = new Parser();
            parser.execute(Sources.FIRST);
            parser.onFinish(new Parser.OnTaskCompleted() {

                @Override
                public void onTaskCompleted(ArrayList<Article> list) {
                    observer.onNext(list);
                }

                @Override
                public void onError() {
                    observer.onError(new Throwable("Fetching data error"));
                }
            });
        });
    }

    @Override
    public Observable<List<Article>> getDataFromSecondSource() {
        return Observable.fromPublisher(observer -> {
            Parser parser = new Parser();
            parser.execute(Sources.SECOND);
            parser.onFinish(new Parser.OnTaskCompleted() {

                @Override
                public void onTaskCompleted(ArrayList<Article> list) {
                    observer.onNext(list);
                }

                @Override
                public void onError() {
                    observer.onError(new Throwable("Fetching data error"));
                }
            });
        });
    }

    @Override
    public Observable<List<Article>> getDataFromThirdSource() {
        return Observable.fromPublisher(observer -> {
            Parser parser = new Parser();
            parser.execute(Sources.THIRD);
            parser.onFinish(new Parser.OnTaskCompleted() {

                @Override
                public void onTaskCompleted(ArrayList<Article> list) {
                    observer.onNext(list);
                }

                @Override
                public void onError() {
                    observer.onError(new Throwable("Fetching data error"));
                }
            });
        });
    }
}
