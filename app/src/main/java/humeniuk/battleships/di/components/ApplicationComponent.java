package humeniuk.battleships.di.components;

import javax.inject.Singleton;

import dagger.Component;
import humeniuk.battleships.di.modules.ApplicationModule;
import humeniuk.battleships.di.modules.UtilModule;
import humeniuk.battleships.presentation.utils.CellViewUtil;
import humeniuk.battleships.presentation.utils.ShipUtil;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        UtilModule.class})
public interface ApplicationComponent {

    CellViewUtil getCellViewUtil();

    ShipUtil getShipUtil();
}
