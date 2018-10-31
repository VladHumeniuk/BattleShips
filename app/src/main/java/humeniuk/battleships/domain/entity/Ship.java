package humeniuk.battleships.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private List<Cell> cells;

    private ShipType type;

    public Ship(ShipType type) {
        this.type = type;
        cells = new ArrayList<>();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
    }

    public ShipType getType() {
        return type;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public boolean isComplete() {
        return type.getSize() == cells.size();
    }
}
