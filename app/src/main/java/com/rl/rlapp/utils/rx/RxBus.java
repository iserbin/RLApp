package com.rl.rlapp.utils.rx;

import com.jakewharton.rxrelay2.PublishRelay;

public class RxBus {

    private static final String TAG = RxBus.class.getSimpleName();
    private PublishRelay<Object> bus = PublishRelay.create();

    public void send(Object o) {
        bus.accept(o);
    }

    public PublishRelay<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
