package humeniuk.battleships.interactor.shipsLeftCount;

import java.util.Map;

import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public interface ShipsLeftCountInteractor {

    Observable<Map<ShipType, Integer>> getShipsLeftCount(Table table);
}
