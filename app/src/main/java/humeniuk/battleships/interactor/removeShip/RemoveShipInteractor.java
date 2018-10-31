package humeniuk.battleships.interactor.removeShip;

import humeniuk.battleships.domain.entity.Ship;
import io.reactivex.Observable;

public interface RemoveShipInteractor {

    Observable<Boolean> removeShip(Ship ship);
}
