package humeniuk.battleships.presentation.battle.presenter;

import java.util.Map;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Player;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractor;
import humeniuk.battleships.interactor.shipsLeftCount.ShipsLeftCountInteractor;
import humeniuk.battleships.presentation.base.RxPresenter;
import humeniuk.battleships.presentation.battle.router.BattleRouter;
import humeniuk.battleships.presentation.battle.view.BattleView;
import humeniuk.battleships.presentation.utils.ShipUtil;
import io.reactivex.Scheduler;

public class BattlePresenterImpl extends RxPresenter implements BattlePresenter {

    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    private BattleRouter router;

    private ShipsLeftCountInteractor shipsLeftCountInteractor;
    private FindShipOnTableInteractor findShipOnTableInteractor;

    private ShipUtil shipUtil;

    private BattleView view;

    private Player activePlayer;
    private Player defendingPlayer;

    private boolean boardActive = true;

    public BattlePresenterImpl(Scheduler ioScheduler,
                               Scheduler uiScheduler,
                               BattleRouter router,
                               ShipsLeftCountInteractor shipsLeftCountInteractor,
                               FindShipOnTableInteractor findShipOnTableInteractor,
                               ShipUtil shipUtil) {
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        this.router = router;
        this.shipsLeftCountInteractor = shipsLeftCountInteractor;
        this.findShipOnTableInteractor = findShipOnTableInteractor;
        this.shipUtil = shipUtil;
    }

    @Override
    public void setView(BattleView view) {
        this.view = view;
    }

    @Override
    public void init(Player player1, Player player2) {
        this.activePlayer = player1;
        this.defendingPlayer = player2;
        startTurn();
    }

    @Override
    public void onCellClick(int x, int y) {
        if (!boardActive) {
            return;
        }

        Cell cell = defendingPlayer.getTable().getCells()[y][x];
        CellState state = cell.getCellState();

        if (CellState.MISS.equals(state)
                || CellState.SHIP_DAMAGED.equals(state)
                || CellState.SHIP_DESTROYED.equals(state)) {
            return;
        }

        if (CellState.EMPTY.equals(state)) {
            miss(cell);
        } else {
            hitTheShip(cell);
        }
    }

    @Override
    public void onEndTurnClick() {
        endTurn();
    }

    @Override
    public void finish() {
        router.openMainFragment();
    }

    private void startTurn() {
        boardActive = true;
        updateShipsLeftCount(defendingPlayer.getTable());
        view.initField(defendingPlayer.getTable().getCells());
        view.startTurn(activePlayer.getName());
    }

    private void endTurn() {
        Player player = defendingPlayer;
        defendingPlayer = activePlayer;
        activePlayer = player;
        startTurn();
    }

    private void miss(Cell cell) {
        boardActive = false;
        cell.setCellState(CellState.MISS);
        view.updateCellView(cell);
        view.notifyMiss();
    }

    private boolean checkWinConditions(Map< ShipType, Integer> shipCountMap) {
        int totalShipsLeftCount = 0;
        for (Integer count : shipCountMap.values()) {
            totalShipsLeftCount += count;
        }
        return totalShipsLeftCount == 0;
    }

    private void updateShipsLeftCount(Table table) {
        subscribe(0, shipsLeftCountInteractor.getShipsLeftCount(table)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(map -> {
                    unsubscribe(0);
                    view.updateShipsLeftCount(map);
                    if (checkWinConditions(map)) {
                        view.endGame(activePlayer.getName());
                    }
                }));
    }

    private void hitTheShip(Cell cell) {
        subscribe(1, findShipOnTableInteractor.findShipOnTable(defendingPlayer.getTable(), cell.getX(), cell.getY())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(ship -> {
                    if (ship == null) {
                        miss(cell);
                    } else {
                        cell.setCellState(CellState.SHIP_DAMAGED);
                        if (!shipUtil.isShipAlive(ship)) {
                            shipUtil.makeShipDestroyed(ship);
                            updateShipsLeftCount(defendingPlayer.getTable());
                        }
                        for (Cell shipCell : ship.getCells()) {
                            view.updateCellView(shipCell);
                        }
                    }
                }));
    }
}
