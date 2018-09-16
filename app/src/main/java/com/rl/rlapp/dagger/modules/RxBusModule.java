package com.rl.rlapp.dagger.modules;

import com.rl.rlapp.utils.rx.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RxBusModule {

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return new RxBus();
    }
}
