package humeniuk.battleships.interactor.shipPlacementAvailabilityCheck;

import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public interface ShipPlacementAvailabilityCheckInteractor {

    Observable<Boolean> checkIfCellAvailable(Table table, Ship ship, int x, int y);
}
