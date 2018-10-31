package humeniuk.battleships.interactor;

import org.junit.Before;
import org.junit.Test;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.shipPlacementAvailabilityCheck.ShipPlacementAvailabilityCheckInteractor;
import humeniuk.battleships.interactor.shipPlacementAvailabilityCheck.ShipPlacementAvailabilityCheckInteractorImpl;
import humeniuk.battleships.testUtils.TestEntityFactory;
import io.reactivex.observers.TestObserver;

public class ShipPlacementAvailabilityCheckInteractorTest {

    private ShipPlacementAvailabilityCheckInteractor interactor;

    private TestObserver<Boolean> testObserver;

    private TestEntityFactory entityFactory;

    @Before
    public void init() {
        interactor = new ShipPlacementAvailabilityCheckInteractorImpl();
        entityFactory = new TestEntityFactory();
        testObserver = new TestObserver<>();
    }

    @Test
    public void testPlacementAvailableNewShip() {
        Table table = entityFactory.getEmptyTable();
        Cell cell = table.getCells()[0][0];
        Ship ship = new Ship(ShipType.SIZE_4);

        interactor.checkIfCellAvailable(table, ship, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertResult(true);
    }

    @Test
    public void testPlacementAvailableShip() {
        Table table = entityFactory.getEmptyTable();
        Cell cell = table.getCells()[0][0];
        Ship ship = new Ship(ShipType.SIZE_4);
        ship.addCell(cell);
        cell = table.getCells()[0][1];

        interactor.checkIfCellAvailable(table, ship, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertResult(true);
    }

    @Test
    public void testPlacementAvailableLongShip() {
        Table table = entityFactory.getEmptyTable();
        Cell cell = table.getCells()[0][0];
        Ship ship = new Ship(ShipType.SIZE_4);
        ship.addCell(cell);
        cell = table.getCells()[0][1];
        ship.addCell(cell);
        cell = table.getCells()[0][2];

        interactor.checkIfCellAvailable(table, ship, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertResult(true);
    }

    @Test
    public void testPlacementBlockedWrongShipShape() {
        Table table = entityFactory.getEmptyTable();
        Cell cell = table.getCells()[0][0];
        Ship ship = new Ship(ShipType.SIZE_4);
        ship.addCell(cell);
        cell = table.getCells()[0][1];
        ship.addCell(cell);
        cell = table.getCells()[1][0];

        interactor.checkIfCellAvailable(table, ship, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertResult(false);
    }

    @Test
    public void testPlacementBlockedNotAdjunced() {
        Table table = entityFactory.getEmptyTable();
        Cell cell = table.getCells()[0][0];
        Ship ship = new Ship(ShipType.SIZE_4);
        ship.addCell(cell);
        cell = table.getCells()[0][2];

        interactor.checkIfCellAvailable(table, ship, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertResult(false);
    }

    @Test
    public void testPlacementBlockedAdjunceOtherShip() {
        Table table = entityFactory.getEmptyTable();
        entityFactory.putSize4Ship(table);
        Ship ship = new Ship(ShipType.SIZE_3);
        Cell shipCell = table.getShips().get(0).getCells().get(0);
        Cell cell = table.getCells()[shipCell.getY() + 1][shipCell.getX()];

        interactor.checkIfCellAvailable(table, ship, cell.getX(), cell.getY()).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertResult(false);
    }
}
