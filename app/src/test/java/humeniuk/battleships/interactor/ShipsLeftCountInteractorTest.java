package humeniuk.battleships.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.shipsLeftCount.ShipsLeftCountInteractor;
import humeniuk.battleships.interactor.shipsLeftCount.ShipsLeftCountInteractorImpl;
import humeniuk.battleships.presentation.utils.ShipUtil;
import humeniuk.battleships.testUtils.TestEntityFactory;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;

public class ShipsLeftCountInteractorTest {

    private ShipsLeftCountInteractor interactor;

    private TestObserver<Map<ShipType, Integer>> testObserver;

    private TestEntityFactory entityFactory;

    private ShipUtil shipUtil;

    @Before
    public void init() {
        interactor = new ShipsLeftCountInteractorImpl(new ShipUtil());
        testObserver = new TestObserver<>();
        entityFactory = new TestEntityFactory();
        shipUtil = new ShipUtil();
    }

    @Test
    public void testNoShips() {
        Table table = entityFactory.getEmptyTable();
        interactor.getShipsLeftCount(table).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(map -> {
            for (ShipType shipType : ShipType.values()) {
                assertEquals(0, map.get(shipType).intValue());
            }
            return true;
        });
    }

    @Test
    public void testAllShipsDestroyed() {
        Table table = entityFactory.getCompleteTable();
        for (Ship ship : table.getShips()) {
            shipUtil.makeShipDestroyed(ship);
        }

        interactor.getShipsLeftCount(table).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(map -> {
            for (ShipType shipType : ShipType.values()) {
                assertEquals(0, map.get(shipType).intValue());
            }
            return true;
        });
    }

    @Test
    public void testAllShipsAlive() {
        Table table = entityFactory.getCompleteTable();

        interactor.getShipsLeftCount(table).subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(map -> {
            for (ShipType shipType : ShipType.values()) {
                assertEquals(shipType.getAvailableCount(), map.get(shipType).intValue());
            }
            return true;
        });
    }
}
