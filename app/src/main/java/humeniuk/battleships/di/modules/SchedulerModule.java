package humeniuk.battleships.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulerModule {

    public static final String IO_SCHEDULER = "io_scheduler";

    public static final String UI_SCHEDULER = "ui_scheduler";

    @Provides
    @Named(IO_SCHEDULER)
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named(UI_SCHEDULER)
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
