package humeniuk.battleships;

import android.app.Application;

import humeniuk.battleships.di.components.ApplicationComponent;
import humeniuk.battleships.di.components.DaggerApplicationComponent;
import humeniuk.battleships.di.modules.ApplicationModule;
import humeniuk.battleships.di.modules.UtilModule;

public class BattleShipsApplication extends Application {

    private static ApplicationComponent applicationComponent;

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .utilModule(new UtilModule())
                .build();
    }
}
