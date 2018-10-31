package humeniuk.battleships.interactor.removeShip;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Ship;
import io.reactivex.Observable;

public class RemoveShipInteractorImpl implements RemoveShipInteractor {

    public RemoveShipInteractorImpl() {
    }

    @Override
    public Observable<Boolean> removeShip(Ship ship) {
        return Observable.fromCallable(() -> {
            for (Cell cell : ship.getCells()) {
                cell.setCellState(CellState.EMPTY);
            }
            return true;
        });
    }
}
