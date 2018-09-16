package com.rl.rlapp.business;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.prof.rssparser.Article;
import com.rl.rlapp.business.base.BasePresenter;
import com.rl.rlapp.data.events.ItemClickedEvent;
import com.rl.rlapp.ui.fragments.FirstView;
import com.rl.rlapp.utils.rx.RxBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class FirstPresenter extends BasePresenter<FirstView> {

    private static final String TAG = FirstPresenter.class.getSimpleName();
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final RxBus rxBus;

    public FirstPresenter(RxBus rxBus) {
        this.rxBus = rxBus;
    }

    @Override
    public void attachView(FirstView view) {
        super.attachView(view);
        updateDateTime();
        listenBus();
    }

    private void listenBus() {
        disposeOnDestroy(rxBus.toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    boolean b = o instanceof ItemClickedEvent;
                    if (b) {
                        ItemClickedEvent itemClickedEvent = (ItemClickedEvent) o;
                        Article article = itemClickedEvent.getArticle();
                        String title = article.getTitle();
                        if (title != null && !title.isEmpty()) {
                            getViewState().showItemTitle(title);
                        }
                    }
                }));
    }

    private void updateDateTime() {
        disposeOnDestroy(Flowable.interval(1, TimeUnit.SECONDS)
                        .timeInterval()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(l -> getViewState().showDateTime(getDateString()),
                                throwable -> Log.e(TAG, "updateDateTime: error["+throwable.getMessage()+"]", throwable)));
    }

    private String getDateString() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault());
        return df.format(c.getTime());
    }
}
