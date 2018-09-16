package com.rl.rlapp.dagger.modules;

import com.rl.rlapp.business.FirstPresenter;
import com.rl.rlapp.business.IInteractor;
import com.rl.rlapp.business.InteractorImpl;
import com.rl.rlapp.business.SecondPresenter;
import com.rl.rlapp.business.SecondPresenterCache;
import com.rl.rlapp.data.IRepository;
import com.rl.rlapp.data.RepositoryImpl;
import com.rl.rlapp.utils.rx.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    FirstPresenter provideFirstPresenter(RxBus rxBus) {
        return new FirstPresenter(rxBus);
    }

    @Provides
    @Singleton
    SecondPresenter provideSecondPresenter(RxBus rxBus,
                                           SecondPresenterCache presenterCache,
                                           IInteractor interactor) {
        return new SecondPresenter(rxBus, interactor);
    }

    @Provides
    @Singleton
    SecondPresenterCache provideSecondPresenterCache() {
        return new SecondPresenterCache();
    }

    @Provides
    @Singleton
    IInteractor provideIRegisterInteractor(IRepository repository) {
        return new InteractorImpl(repository);
    }

    @Provides
    @Singleton
    IRepository provideIStackRepository() {
        return new RepositoryImpl();
    }
}
