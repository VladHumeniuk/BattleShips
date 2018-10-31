package humeniuk.battleships.interactor.findShipOnTable;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public class FindShipOnTableInteractorImpl implements FindShipOnTableInteractor {

    public FindShipOnTableInteractorImpl() {
    }

    @Override
    public Observable<Ship> findShipOnTable(Table table, int x, int y) {
        return Observable.fromCallable(() -> {
            CellState cellState = table.getCells()[y][x].getCellState();
            if (CellState.EMPTY.equals(cellState) || CellState.MISS.equals(cellState)) {
                return null;
            }
            for (Ship ship : table.getShips()) {
                for (Cell shipCell : ship.getCells()) {
                    if (shipCell.getX() == x && shipCell.getY() == y) {
                        return ship;
                    }
                }
            }
            return null;
        });
    }
}
