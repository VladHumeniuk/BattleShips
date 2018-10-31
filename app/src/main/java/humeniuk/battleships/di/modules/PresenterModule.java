package humeniuk.battleships.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import humeniuk.battleships.di.scopes.UserScope;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractor;
import humeniuk.battleships.interactor.initEmptyField.InitEmptyFieldInteractor;
import humeniuk.battleships.interactor.removeShip.RemoveShipInteractor;
import humeniuk.battleships.interactor.shipCount.GetAvailableShipCountInteractor;
import humeniuk.battleships.interactor.shipPlacementAvailabilityCheck.ShipPlacementAvailabilityCheckInteractor;
import humeniuk.battleships.interactor.shipsLeftCount.ShipsLeftCountInteractor;
import humeniuk.battleships.presentation.battle.presenter.BattlePresenter;
import humeniuk.battleships.presentation.battle.presenter.BattlePresenterImpl;
import humeniuk.battleships.presentation.battle.router.BattleRouter;
import humeniuk.battleships.presentation.main.presenter.MainPresenter;
import humeniuk.battleships.presentation.main.presenter.MainPresenterImpl;
import humeniuk.battleships.presentation.main.router.MainRouter;
import humeniuk.battleships.presentation.preparation.presenter.PreparationPresenter;
import humeniuk.battleships.presentation.preparation.presenter.PreparationPresenterImpl;
import humeniuk.battleships.presentation.preparation.router.PreparationRouter;
import humeniuk.battleships.presentation.utils.ShipUtil;
import io.reactivex.Scheduler;

import static humeniuk.battleships.di.modules.SchedulerModule.IO_SCHEDULER;
import static humeniuk.battleships.di.modules.SchedulerModule.UI_SCHEDULER;

@Module
public class PresenterModule {

    @Provides
    @UserScope
    PreparationPresenter provideFieldPresenter(@Named(IO_SCHEDULER)Scheduler ioScheduler,
                                               @Named(UI_SCHEDULER)Scheduler uiScheduler,
                                               PreparationRouter router,
                                               ShipPlacementAvailabilityCheckInteractor shipPlacementAvailabilityCheckInteractor,
                                               GetAvailableShipCountInteractor getAvailableShipCountInteractor,
                                               InitEmptyFieldInteractor initEmptyFieldInteractor,
                                               RemoveShipInteractor removeShipInteractor,
                                               FindShipOnTableInteractor findShipOnTableInteractor) {
        return new PreparationPresenterImpl(
                ioScheduler,
                uiScheduler,
                router,
                shipPlacementAvailabilityCheckInteractor,
                getAvailableShipCountInteractor,
                initEmptyFieldInteractor,
                removeShipInteractor,
                findShipOnTableInteractor);
    }

    @Provides
    @UserScope
    BattlePresenter provideBattlePresenter (@Named(IO_SCHEDULER)Scheduler ioScheduler,
                                            @Named(UI_SCHEDULER)Scheduler uiScheduler,
                                            BattleRouter router,
                                            ShipsLeftCountInteractor shipsLeftCountInteractor,
                                            FindShipOnTableInteractor findShipOnTableInteractor,
                                            ShipUtil shipUtil) {
        return new BattlePresenterImpl(ioScheduler,
                uiScheduler,
                router,
                shipsLeftCountInteractor,
                findShipOnTableInteractor,
                shipUtil);
    }

    @Provides
    @UserScope
    MainPresenter provideMainPresenter(MainRouter mainRouter) {
        return new MainPresenterImpl(mainRouter);
    }
}
