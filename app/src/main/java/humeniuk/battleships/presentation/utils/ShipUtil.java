package humeniuk.battleships.presentation.utils;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.Table;

public class ShipUtil {

    public boolean isShipAlive(Ship ship) {
        int destroyedCellCount = 0;
        for (Cell cell : ship.getCells()) {
            CellState state = cell.getCellState();
            if (CellState.SHIP_DAMAGED.equals(state)
                    || CellState.SHIP_DESTROYED.equals(state)) {
                destroyedCellCount++;
            }
        }
        return ship.getCells().size() != destroyedCellCount;
    }

    public void makeShipDestroyed(Ship ship) {
        for (Cell cell : ship.getCells()) {
            cell.setCellState(CellState.SHIP_DESTROYED);
        }
    }
}
