package humeniuk.battleships.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractor;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractorImpl;
import humeniuk.battleships.testUtils.TestEntityFactory;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FindShipOnTableInteractorTest {

    private FindShipOnTableInteractor interactor;

    private TestObserver<Ship> testObserver;

    private TestEntityFactory entityFactory;

    @Before
    public void init() {
        interactor = new FindShipOnTableInteractorImpl();
        testObserver = new TestObserver<>();
        entityFactory = new TestEntityFactory();
    }

    @Test
    public void testFindShip() {
        Table table = entityFactory.getCompleteTable();
        Ship ship = table.getShips().get(0);
        Cell cell = ship.getCells().get(0);

        interactor.findShipOnTable(table, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertResult(ship);
    }

    @Test
    public void testFindShipFailed() {
        Table table = entityFactory.getEmptyTable();
        Cell cell = table.getCells()[0][0];

        interactor.findShipOnTable(table, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertError(NullPointerException.class);
    }
}
