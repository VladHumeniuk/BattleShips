package humeniuk.battleships.domain.entity;

public class Cell {

    private int x;

    private int y;

    private CellState cellState;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        cellState = CellState.EMPTY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }
}
