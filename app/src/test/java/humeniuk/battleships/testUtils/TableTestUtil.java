package humeniuk.battleships.testUtils;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Table;

public class TableTestUtil {

    public boolean isTableEmpty(Table table) {
        for (Cell[] row : table.getCells()) {
            for (Cell cell : row) {
                if (!CellState.EMPTY.equals(cell.getCellState())) {
                    return false;
                }
            }
        }
        return true;
    }
}
