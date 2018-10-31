package humeniuk.battleships.interactor;

import org.junit.Before;
import org.junit.Test;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.removeShip.RemoveShipInteractor;
import humeniuk.battleships.interactor.removeShip.RemoveShipInteractorImpl;
import humeniuk.battleships.testUtils.TestEntityFactory;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;

public class RemoveShipInteractorTest {

    private RemoveShipInteractor interactor;

    private TestObserver<Boolean> testObserver;

    private TestEntityFactory entityFactory;

    @Before
    public void init() {
        interactor = new RemoveShipInteractorImpl();
        testObserver = new TestObserver<>();
        entityFactory = new TestEntityFactory();
    }

    @Test
    public void testRemoveShip() {
        Table table = entityFactory.getEmptyTable();
        entityFactory.putSize4Ship(table);
        Ship ship = table.getShips().get(0);

        interactor.removeShip(ship).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(result -> {
            for (Cell cell : ship.getCells()) {
                assertEquals(CellState.EMPTY, cell.getCellState());
            }
            return result;
        });
    }
}
