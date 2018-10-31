package humeniuk.battleships.testUtils;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;

import static humeniuk.battleships.domain.Constants.TABLE_SIZE;

public class TestEntityFactory {

    public Ship getAliveShip() {
        Ship ship = new Ship(ShipType.SIZE_3);
        for (int i = 0; i < 3; i++) {
            Cell cell = new Cell(i, 1);
            cell.setCellState(CellState.SHIP);
            ship.addCell(cell);
        }
        return ship;
    }

    public Ship getDamagedShip() {
        Ship ship = new Ship(ShipType.SIZE_3);
        for (int i = 0; i < 3; i++) {
            Cell cell = new Cell(i, 1);
            cell.setCellState(CellState.SHIP);
            ship.addCell(cell);
        }
        ship.getCells().get(0).setCellState(CellState.SHIP_DAMAGED);
        return ship;
    }

    public Ship getFullDamagedShip() {
        Ship ship = new Ship(ShipType.SIZE_3);
        for (int i = 0; i < 3; i++) {
            Cell cell = new Cell(i, 1);
            cell.setCellState(CellState.SHIP_DAMAGED);
            ship.addCell(cell);
        }
        return ship;
    }

    public Ship getDestroyedShip() {
        Ship ship = new Ship(ShipType.SIZE_3);
        for (int i = 0; i < 3; i++) {
            Cell cell = new Cell(i, 1);
            cell.setCellState(CellState.SHIP_DESTROYED);
            ship.addCell(cell);
        }
        return ship;
    }

    public Table getEmptyTable() {
        Table table = new Table();
        Cell[][] cells = new Cell[TABLE_SIZE][TABLE_SIZE];
        for (int y = 0; y < TABLE_SIZE; y++) {
            for (int x = 0; x < TABLE_SIZE; x++) {
                Cell cell = new Cell(x, y);
                cells[y][x] = cell;
            }
        }
        table.setCells(cells);
        return table;
    }

    public Table getCompleteTable() {
        Table table = getEmptyTable();
        putSize4Ship(table);
        putSize3Ships(table);
        putSize2Ships(table);
        putSize1Ships(table);
        return table;
    }

    private void putShipOnTable(Table table, ShipType shipType, int x, int y) {
        Ship ship = new Ship(shipType);
        for (int i = x; i < x + shipType.getSize(); i++) {
            Cell cell = table.getCells()[y][i];
            cell.setCellState(CellState.SHIP);
            ship.addCell(cell);
        }
        table.addShip(ship);
    }

    public void putSize4Ship(Table table) {
        putShipOnTable(table, ShipType.SIZE_4, 0, 0);
    }

    public void putSize3Ships(Table table) {
        putShipOnTable(table, ShipType.SIZE_3, 0, 2);
        putShipOnTable(table, ShipType.SIZE_3, 4, 2);
    }

    public void putSize2Ships(Table table) {
        putShipOnTable(table, ShipType.SIZE_2, 0, 4);
        putShipOnTable(table, ShipType.SIZE_2, 3, 4);
        putShipOnTable(table, ShipType.SIZE_2, 6, 4);
    }

    public void putSize1Ships(Table table) {
        putShipOnTable(table, ShipType.SIZE_1, 0, 6);
        putShipOnTable(table, ShipType.SIZE_1, 2, 6);
        putShipOnTable(table, ShipType.SIZE_1, 4, 6);
        putShipOnTable(table, ShipType.SIZE_1, 6, 6);
    }
}
