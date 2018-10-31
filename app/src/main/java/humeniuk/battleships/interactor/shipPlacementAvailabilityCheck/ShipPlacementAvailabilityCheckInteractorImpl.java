package humeniuk.battleships.interactor.shipPlacementAvailabilityCheck;

import java.util.List;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public class ShipPlacementAvailabilityCheckInteractorImpl implements ShipPlacementAvailabilityCheckInteractor {

    public ShipPlacementAvailabilityCheckInteractorImpl() {
    }

    @Override
    public Observable<Boolean> checkIfCellAvailable(Table table, Ship ship, int x, int y) {
        return Observable.fromCallable(() -> {
            Cell cell = table.getCells()[y][x];
            if (!CellState.EMPTY.equals(cell.getCellState())) {
                return false;
            }

            boolean collisionWtihOtherShips = false;
            for (Ship tableShip : table.getShips()) {
                collisionWtihOtherShips |= collisionWithShip(tableShip, x, y);
            }

            List<Cell> shipCells = ship.getCells();
            if (shipCells.size() == 0) {
                return !collisionWtihOtherShips;
            }

            if (shipCells.size() == 1) {
                Cell shipCell = shipCells.get(0);
                int deltaX = Math.abs(shipCell.getX() - x);
                int deltaY = Math.abs(shipCell.getY() - y);
                int delta = deltaX + deltaY;
                return !collisionWtihOtherShips && delta == 1;
            }

            int shipX = shipCells.get(0).getX();
            int shipY = shipCells.get(0).getY();
            boolean adjanced = false;
            for (Cell shipCell : shipCells) {
                if (shipX != shipCell.getX()) {
                    shipX = -1;
                }
                if (shipY != shipCell.getY()) {
                    shipY = -1;
                }
                int deltaX = shipCell.getX() - x;
                int deltaY = shipCell.getY() - y;
                int delta = Math.abs(deltaX + deltaY);
                adjanced |= delta == 1;
            }
            boolean inLine = shipX != -1 ? x == shipX : y == shipY;

            return adjanced && inLine && !collisionWtihOtherShips;
        });
    }

    private boolean collisionWithShip(Ship ship, int x, int y) {
        for (Cell cell : ship.getCells()) {
            int deltaX = Math.abs(cell.getX() - x);
            int deltaY = Math.abs(cell.getY() - y);
            if (deltaX <= 1 && deltaY <= 1) {
                return true;
            }
        }
        return false;
    }
}
