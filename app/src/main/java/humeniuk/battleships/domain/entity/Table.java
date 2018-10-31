package humeniuk.battleships.domain.entity;

import java.util.ArrayList;
import java.util.List;

import static humeniuk.battleships.domain.Constants.TABLE_SIZE;

public class Table {

    private List<Ship> ships;

    private Cell[][] cells;

    public Table() {
        ships = new ArrayList<>();
        cells = new Cell[TABLE_SIZE][TABLE_SIZE];
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    public boolean isComplete() {
        return ships.size() == ShipType.getTotalCount();
    }
}
