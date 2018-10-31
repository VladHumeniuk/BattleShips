package humeniuk.battleships.di.modules;

import dagger.Module;
import dagger.Provides;
import humeniuk.battleships.di.scopes.UserScope;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractor;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractorImpl;
import humeniuk.battleships.interactor.initEmptyField.InitEmptyFieldInteractor;
import humeniuk.battleships.interactor.initEmptyField.InitEmptyFieldInteractorImpl;
import humeniuk.battleships.interactor.removeShip.RemoveShipInteractor;
import humeniuk.battleships.interactor.removeShip.RemoveShipInteractorImpl;
import humeniuk.battleships.interactor.shipCount.GetAvailableShipCountInteractor;
import humeniuk.battleships.interactor.shipCount.GetAvailableShipCountInteractorImpl;
import humeniuk.battleships.interactor.shipPlacementAvailabilityCheck.ShipPlacementAvailabilityCheckInteractor;
import humeniuk.battleships.interactor.shipPlacementAvailabilityCheck.ShipPlacementAvailabilityCheckInteractorImpl;
import humeniuk.battleships.interactor.shipsLeftCount.ShipsLeftCountInteractor;
import humeniuk.battleships.interactor.shipsLeftCount.ShipsLeftCountInteractorImpl;
import humeniuk.battleships.presentation.utils.ShipUtil;

@Module
public class InteractorModule {

    @Provides
    @UserScope
    ShipPlacementAvailabilityCheckInteractor provideShipPlacementAvailabilityCheckInteractor() {
        return new ShipPlacementAvailabilityCheckInteractorImpl();
    }

    @Provides
    @UserScope
    GetAvailableShipCountInteractor provideGetAvailableShipCountInteractor() {
        return new GetAvailableShipCountInteractorImpl();
    }

    @Provides
    @UserScope
    InitEmptyFieldInteractor provideInitEmptyFieldInteractor() {
        return new InitEmptyFieldInteractorImpl();
    }

    @Provides
    @UserScope
    RemoveShipInteractor provideRemoveShipInteractor() {
        return new RemoveShipInteractorImpl();
    }

    @Provides
    @UserScope
    FindShipOnTableInteractor provideFindShipOnTableInteractor() {
        return new FindShipOnTableInteractorImpl();
    }

    @Provides
    @UserScope
    ShipsLeftCountInteractor provideShipsLeftCountInteractor(ShipUtil shipUtil) {
        return new ShipsLeftCountInteractorImpl(shipUtil);
    }
}
