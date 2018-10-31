package humeniuk.battleships.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.Player;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractor;
import humeniuk.battleships.interactor.shipsLeftCount.ShipsLeftCountInteractor;
import humeniuk.battleships.presentation.battle.presenter.BattlePresenter;
import humeniuk.battleships.presentation.battle.presenter.BattlePresenterImpl;
import humeniuk.battleships.presentation.battle.router.BattleRouter;
import humeniuk.battleships.presentation.battle.view.BattleView;
import humeniuk.battleships.presentation.utils.ShipUtil;
import humeniuk.battleships.testUtils.TestEntityFactory;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static humeniuk.battleships.domain.Constants.TABLE_SIZE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BattlePresenterTest {

    @Mock
    private BattleView view;

    @Mock
    private BattleRouter router;
    @Mock
    private ShipsLeftCountInteractor shipsLeftCountInteractor;
    @Mock
    private FindShipOnTableInteractor findShipOnTableInteractor;

    private ShipUtil shipUtil;

    private BattlePresenter presenter;

    private TestScheduler testScheduler;

    private TestEntityFactory entityFactory;

    @Before
    public void init() {
        testScheduler = new TestScheduler();
        shipUtil = new ShipUtil();
        presenter = new BattlePresenterImpl(testScheduler, testScheduler, router,
                shipsLeftCountInteractor, findShipOnTableInteractor, shipUtil);
        presenter.setView(view);
        entityFactory = new TestEntityFactory();
    }

    @Test
    public void testInit() {
        Player player1 = new Player("Player1");
        player1.setTable(entityFactory.getCompleteTable());
        Player player2 = new Player("Player2");
        player2.setTable(entityFactory.getCompleteTable());

        Map<ShipType, Integer> shipCount = new HashMap<>();

        for (ShipType shipType : ShipType.values()) {
            shipCount.put(shipType, shipType.getAvailableCount());
        }
        when(shipsLeftCountInteractor.getShipsLeftCount(any())).thenReturn(Observable.just(shipCount));

        presenter.init(player1, player2);
        testScheduler.triggerActions();
        verify(shipsLeftCountInteractor).getShipsLeftCount(player2.getTable());
        verify(view).updateShipsLeftCount(shipCount);
        verify(view).initField(player2.getTable().getCells());
        verify(view).startTurn(player1.getName());
    }

    @Test
    public void testOnCellClickMiss() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        initGame(player1, player2);

        presenter.onCellClick(TABLE_SIZE - 1, TABLE_SIZE - 1);
        verify(view).updateCellView(player2.getTable().getCells()[TABLE_SIZE - 1][TABLE_SIZE - 1]);
        verify(view).notifyMiss();
    }

    @Test
    public void testOnCellClickHit() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        initGame(player1, player2);

        Ship ship = player2.getTable().getShips().get(0);
        Cell cell = ship.getCells().get(0);
        when(findShipOnTableInteractor.findShipOnTable(player2.getTable(), cell.getX(),
                cell.getY())).thenReturn(Observable.just(ship));

        presenter.onCellClick(cell.getX(), cell.getY());
        testScheduler.triggerActions();
        verify(findShipOnTableInteractor).findShipOnTable(player2.getTable(), cell.getX(), cell.getY());
        verify(view, atLeast(1)).updateCellView(any());
    }

    @Test
    public void testOnCellClickDestroy() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        initGame(player1, player2);

        Ship ship = player2.getTable().getShips().get(0);
        when(findShipOnTableInteractor.findShipOnTable(any(), anyInt(), anyInt())).thenReturn(Observable.just(ship));
        for (Cell cell : ship.getCells()) {
            presenter.onCellClick(cell.getX(), cell.getY());
            testScheduler.triggerActions();
        }

        verify(shipsLeftCountInteractor, times(2)).getShipsLeftCount(player2.getTable());
        verify(view, times(2)).updateShipsLeftCount(any());
    }

    @Test
    public void testEndTurn() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        initGame(player1, player2);

        presenter.onCellClick(TABLE_SIZE - 1, TABLE_SIZE - 1);
        presenter.onEndTurnClick();

        verify(shipsLeftCountInteractor).getShipsLeftCount(player1.getTable());
        verify(view).initField(player1.getTable().getCells());
        verify(view).startTurn(player2.getName());
    }

    @Test
    public void testEndGame() {
        Player player1 = new Player("Player1");
        player1.setTable(entityFactory.getCompleteTable());
        Player player2 = new Player("Player2");
        player2.setTable(entityFactory.getCompleteTable());

        Map<ShipType, Integer> shipCount = new HashMap<>();

        for (ShipType shipType : ShipType.values()) {
            shipCount.put(shipType, 0);
        }
        when(shipsLeftCountInteractor.getShipsLeftCount(any())).thenReturn(Observable.just(shipCount));

        presenter.init(player1, player2);
        testScheduler.triggerActions();

        verify(view).endGame(player1.getName());
    }

    private void initGame(Player player1, Player player2) {
        player1.setTable(entityFactory.getCompleteTable());
        player2.setTable(entityFactory.getCompleteTable());

        Map<ShipType, Integer> shipCount = new HashMap<>();

        for (ShipType shipType : ShipType.values()) {
            shipCount.put(shipType, shipType.getAvailableCount());
        }
        when(shipsLeftCountInteractor.getShipsLeftCount(any())).thenReturn(Observable.just(shipCount));

        presenter.init(player1, player2);
        testScheduler.triggerActions();
    }
}
