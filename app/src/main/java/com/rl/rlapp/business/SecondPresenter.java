package com.rl.rlapp.business;

import com.arellomobile.mvp.InjectViewState;
import com.prof.rssparser.Article;
import com.rl.rlapp.business.base.BasePresenter;
import com.rl.rlapp.data.events.ItemClickedEvent;
import com.rl.rlapp.ui.dialog.RLDialog;
import com.rl.rlapp.ui.fragments.SecondView;
import com.rl.rlapp.utils.rx.RxBus;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SecondPresenter extends BasePresenter<SecondView> {

    private final RxBus rxBus;
    private final IInteractor interactor;

    public SecondPresenter(RxBus rxBus, IInteractor interactor) {
        this.rxBus = rxBus;
        this.interactor = interactor;
    }

    @Override
    public void attachView(SecondView view) {
        super.attachView(view);

        getPeriodicallyDataFromFirstSource();
        getPeriodicallyDataFromSecondThirdSources();
    }

    private void getPeriodicallyDataFromSecondThirdSources() {
        getDataFromSecondThirdSources();
        disposeOnDestroy(Flowable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> getViewState().showProgressSecond(true))
                .subscribeOn(Schedulers.io())
                .flatMap((Function<Long, Publisher<List<Article>>>) aLong ->
                        Observable.zip(interactor.getDataFromSecondSource(),
                                interactor.getDataFromThirdSource(), (articles, articles2) -> {
                                    articles.addAll(articles2);
                                    return articles;
                                }).toFlowable(BackpressureStrategy.LATEST))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    getViewState().onArticlesGetSecond(articles);
                    getViewState().showProgressSecond(false);
                }, throwable -> getViewState().showProgressSecond(false)));
    }

    private void getPeriodicallyDataFromFirstSource() {
        getDataFromFirstSource();
        disposeOnDestroy(Flowable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> getViewState().showProgressFirst(true))
                .subscribeOn(Schedulers.io())
                .flatMap((Function<Long, Flowable<List<Article>>>) aLong -> interactor.getDataFromFirstSource().toFlowable(BackpressureStrategy.LATEST))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    getViewState().onArticlesGetFirst(articles);
                    getViewState().showProgressFirst(false);
                }, throwable -> getViewState().showProgressFirst(false)));
    }

    public void getDataFromFirstSource() {
        disposeOnDestroy(interactor.getDataFromFirstSource()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    getViewState().onArticlesGetFirst(articles);
                    getViewState().showRefreshingFirst(false);
                }, throwable -> getViewState().showRefreshingFirst(false)));
    }

    public void getDataFromSecondThirdSources() {

        disposeOnDestroy(Observable.zip(interactor.getDataFromSecondSource(),
                interactor.getDataFromThirdSource(), (articles, articles2) -> {
                    articles.addAll(articles2);
                    return articles;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    getViewState().onArticlesGetSecond(articles);
                    getViewState().showRefreshingSecond(false);
                }, throwable -> getViewState().showRefreshingSecond(false)));
    }

    public void onItemClicked(Article article) {
        rxBus.send(new ItemClickedEvent(article));

        RLDialog dialog = RLDialog.newInstance(article);
        dialog.setListener(new RLDialog.DialogListener() {
            @Override
            public void onYes() {
                // noop
            }

            @Override
            public void onBackEvent() {
                // noop
            }

            @Override
            public void onDismiss() {
                // noop
            }
        });
        getViewState().showDialog(dialog, "details".concat(article.getLink()));
    }
}
