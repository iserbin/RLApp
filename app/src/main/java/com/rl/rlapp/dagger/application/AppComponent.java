package com.rl.rlapp.dagger.application;

import com.rl.rlapp.business.FirstPresenter;
import com.rl.rlapp.business.SecondPresenter;
import com.rl.rlapp.dagger.modules.AppModule;
import com.rl.rlapp.dagger.modules.RxBusModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        RxBusModule.class,
        AppModule.class}
)

public interface AppComponent {
    FirstPresenter getFirstPresenter();
    SecondPresenter getSecondPresenter();
}
