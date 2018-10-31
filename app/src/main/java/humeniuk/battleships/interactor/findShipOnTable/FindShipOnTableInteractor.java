package humeniuk.battleships.interactor.findShipOnTable;

import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public interface FindShipOnTableInteractor {

    Observable<Ship> findShipOnTable(Table table, int x, int y);
}
