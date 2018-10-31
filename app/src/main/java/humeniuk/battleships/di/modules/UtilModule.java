package humeniuk.battleships.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import humeniuk.battleships.presentation.utils.CellViewUtil;
import humeniuk.battleships.presentation.utils.ShipUtil;

@Module
public class UtilModule {

    @Provides
    @Singleton
    CellViewUtil provideCellViewUtil() {
        return new CellViewUtil();
    }

    @Provides
    @Singleton
    ShipUtil provideShipUtil() {
        return new ShipUtil();
    }
}
