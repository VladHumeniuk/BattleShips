package humeniuk.battleships.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractor;
import humeniuk.battleships.interactor.initEmptyField.InitEmptyFieldInteractor;
import humeniuk.battleships.interactor.removeShip.RemoveShipInteractor;
import humeniuk.battleships.interactor.shipCount.GetAvailableShipCountInteractor;
import humeniuk.battleships.interactor.shipPlacementAvailabilityCheck.ShipPlacementAvailabilityCheckInteractor;
import humeniuk.battleships.presentation.preparation.presenter.PreparationPresenter;
import humeniuk.battleships.presentation.preparation.presenter.PreparationPresenterImpl;
import humeniuk.battleships.presentation.preparation.router.PreparationRouter;
import humeniuk.battleships.presentation.preparation.view.PreparationView;
import humeniuk.battleships.testUtils.TestEntityFactory;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreparationPresenterTest {

    private PreparationPresenter presenter;

    @Mock
    private PreparationView view;
    @Mock
    private PreparationRouter router;
    @Mock
    private ShipPlacementAvailabilityCheckInteractor shipPlacementAvailabilityCheckInteractor;
    @Mock
    private GetAvailableShipCountInteractor getAvailableShipCountInteractor;
    @Mock
    private InitEmptyFieldInteractor initEmptyFieldInteractor;
    @Mock
    private RemoveShipInteractor removeShipInteractor;
    @Mock
    private FindShipOnTableInteractor findShipOnTableInteractor;

    private TestEntityFactory entityFactory;

    private TestScheduler testScheduler;

    @Before
    public void init() {
        testScheduler = new TestScheduler();
        presenter = new PreparationPresenterImpl(testScheduler, testScheduler,
                router, shipPlacementAvailabilityCheckInteractor, getAvailableShipCountInteractor,
                initEmptyFieldInteractor, removeShipInteractor, findShipOnTableInteractor);
        presenter.setView(view);
        entityFactory = new TestEntityFactory();
    }

    @Test
    public void testInit() {
        Table table = entityFactory.getEmptyTable();
        Map<ShipType, Integer> availableShipCount = new HashMap<>();

        for (ShipType shipType : ShipType.values()) {
            availableShipCount.put(shipType, shipType.getAvailableCount());
        }
        when(initEmptyFieldInteractor.initEmptyField()).thenReturn(Observable.just(table));
        when(getAvailableShipCountInteractor.getAvailableShipCount(table)).thenReturn(Observable.just(availableShipCount));

        presenter.init();
        testScheduler.triggerActions();
        testScheduler.triggerActions();
        verify(initEmptyFieldInteractor).initEmptyField();
        verify(view).setPlayerName("Player1");
        verify(view).initField(table.getCells());
        verify(view).updateReadyButtonVisibility(false);
        verify(getAvailableShipCountInteractor).getAvailableShipCount(table);
        verify(view).updateAvailableShipCount(availableShipCount);
    }

    @Test
    public void testOnShipButtonClick() {
        prepareTable();

        presenter.onShipButtonClick(ShipType.SIZE_4);
        verify(view).updateShipButtonSelected(ShipType.SIZE_4, true);

        presenter.onShipButtonClick(ShipType.SIZE_4);
        testScheduler.triggerActions();
        testScheduler.triggerActions();
        verify(removeShipInteractor).removeShip(any());
        verify(view).updateShipButtonSelected(ShipType.SIZE_4, false);

        presenter.onShipButtonClick(ShipType.SIZE_4);
        presenter.onShipButtonClick(ShipType.SIZE_3);
        testScheduler.triggerActions();
        testScheduler.triggerActions();
        verify(view, times(2)).updateShipButtonSelected(ShipType.SIZE_4, false);
        verify(view).updateShipButtonSelected(ShipType.SIZE_3, true);
    }

    @Test
    public void testOnCellLongClick() {
        Table table = prepareTable();

        entityFactory.putSize4Ship(table);
        Ship ship = table.getShips().get(0);
        Cell shipCell = ship.getCells().get(0);

        when(findShipOnTableInteractor.findShipOnTable(table, shipCell.getX(), shipCell.getY())).thenReturn(Observable.just(ship));

        presenter.onCellLongClick(shipCell.getX(), shipCell.getY());
        testScheduler.triggerActions();
        testScheduler.triggerActions();
        testScheduler.triggerActions();
        verify(findShipOnTableInteractor).findShipOnTable(table, shipCell.getX(), shipCell.getY());
        verify(removeShipInteractor).removeShip(any());
    }

    @Test
    public void testOnCellClick() {
        Table table = prepareTable();
        int x = 0, y =0;
        presenter.onShipButtonClick(ShipType.SIZE_4);
        when(shipPlacementAvailabilityCheckInteractor.checkIfCellAvailable(any(), any(), anyInt(), anyInt())).thenReturn(Observable.just(true));

        presenter.onCellClick( x, y);
        testScheduler.triggerActions();
        verify(view).updateCellView(table.getCells()[y][x]);

        presenter.onCellClick( x+1, y);
        testScheduler.triggerActions();
        presenter.onCellClick( x+2, y);
        testScheduler.triggerActions();
        presenter.onCellClick( x+3, y);
        testScheduler.triggerActions();
        verify(view).updateShipButtonSelected(ShipType.SIZE_4, false);
        verify(view, atLeast(1)).updateReadyButtonVisibility(false);
    }

    private Table prepareTable() {
        Table table = entityFactory.getEmptyTable();
        Map<ShipType, Integer> availableShipCount = new HashMap<>();

        for (ShipType shipType : ShipType.values()) {
            availableShipCount.put(shipType, shipType.getAvailableCount());
        }
        when(initEmptyFieldInteractor.initEmptyField()).thenReturn(Observable.just(table));
        when(getAvailableShipCountInteractor.getAvailableShipCount(table)).thenReturn(Observable.just(availableShipCount));
        when(removeShipInteractor.removeShip(any())).thenReturn(Observable.just(true));

        presenter.init();
        testScheduler.triggerActions();
        testScheduler.triggerActions();

        return table;
    }
}
