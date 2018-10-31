package humeniuk.battleships.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import humeniuk.battleships.BattleShipsApplication;

@Module
public class ApplicationModule {

    private final BattleShipsApplication application;

    public ApplicationModule(BattleShipsApplication application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    BattleShipsApplication provideBattleShipsApplication() {
        return application;
    }

}
