package humeniuk.battleships.interactor.shipCount;

import java.util.Map;

import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public interface GetAvailableShipCountInteractor {

    Observable<Map<ShipType, Integer>> getAvailableShipCount(Table table);
}
