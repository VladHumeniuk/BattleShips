package humeniuk.battleships.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.shipCount.GetAvailableShipCountInteractor;
import humeniuk.battleships.interactor.shipCount.GetAvailableShipCountInteractorImpl;
import humeniuk.battleships.testUtils.TestEntityFactory;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;

public class GetAvailableShipCountInteractorTest {

    private GetAvailableShipCountInteractor interactor;

    private TestEntityFactory testEntityFactory;

    private TestObserver<Map<ShipType, Integer>> observer;

    @Before
    public void init() {
        interactor = new GetAvailableShipCountInteractorImpl();
        testEntityFactory = new TestEntityFactory();
        observer = new TestObserver<>();
    }

    @Test
    public void testEmptyField() {
        Table table = testEntityFactory.getEmptyTable();
        interactor.getAvailableShipCount(table).subscribe(observer);
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValue(map -> {
           for (ShipType type : ShipType.values()) {
               assertEquals(type.getAvailableCount(), map.get(type).intValue());
           }
           return true;
        });
    }

    @Test
    public void testCompleteField() {
        Table table = testEntityFactory.getCompleteTable();
        interactor.getAvailableShipCount(table).subscribe(observer);
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValue(map -> {
            for (ShipType type : ShipType.values()) {
                assertEquals(0, map.get(type).intValue());
            }
            return true;
        });
    }
}
