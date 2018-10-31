package humeniuk.battleships.interactor.shipsLeftCount;

import java.util.HashMap;
import java.util.Map;

import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.presentation.utils.ShipUtil;
import io.reactivex.Observable;

public class ShipsLeftCountInteractorImpl implements ShipsLeftCountInteractor {

    private ShipUtil shipUtil;

    public ShipsLeftCountInteractorImpl(ShipUtil shipUtil) {
        this.shipUtil = shipUtil;
    }

    @Override
    public Observable<Map<ShipType, Integer>> getShipsLeftCount(Table table) {
        return Observable.fromCallable(() -> {
            Map<ShipType, Integer> availableShipCount = new HashMap<>();

            for (ShipType shipType : ShipType.values()) {
                availableShipCount.put(shipType,0);
            }

            for (Ship ship : table.getShips()) {
                if (shipUtil.isShipAlive(ship)) {
                    ShipType type = ship.getType();
                    availableShipCount.put(type, availableShipCount.get(type) + 1);
                }
            }

            return availableShipCount;
        });
    }
}
