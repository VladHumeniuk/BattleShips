package humeniuk.battleships.interactor.initEmptyField;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

import static humeniuk.battleships.domain.Constants.TABLE_SIZE;

public class InitEmptyFieldInteractorImpl implements InitEmptyFieldInteractor {

    public InitEmptyFieldInteractorImpl() {
    }

    @Override
    public Observable<Table> initEmptyField() {
        return Observable.fromCallable(() -> {
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
        });
    }
}
