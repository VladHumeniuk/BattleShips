package humeniuk.battleships.interactor.shipCount;

import java.util.HashMap;
import java.util.Map;

import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public class GetAvailableShipCountInteractorImpl implements GetAvailableShipCountInteractor {

    public GetAvailableShipCountInteractorImpl() {
    }

    @Override
    public Observable<Map<ShipType, Integer>> getAvailableShipCount(Table table) {
        return Observable.fromCallable(() -> {
            Map<ShipType, Integer> availableShipCount = new HashMap<>();

            for (ShipType shipType : ShipType.values()) {
                availableShipCount.put(shipType, shipType.getAvailableCount());
            }

            for (Ship ship : table.getShips()) {
                ShipType type = ship.getType();
                availableShipCount.put(type, availableShipCount.get(type) - 1);
            }

            return availableShipCount;
        });
    }
}
