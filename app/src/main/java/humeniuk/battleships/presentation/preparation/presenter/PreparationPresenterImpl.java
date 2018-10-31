package humeniuk.battleships.presentation.preparation.presenter;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.CellState;
import humeniuk.battleships.domain.entity.Player;
import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.findShipOnTable.FindShipOnTableInteractor;
import humeniuk.battleships.interactor.initEmptyField.InitEmptyFieldInteractor;
import humeniuk.battleships.interactor.removeShip.RemoveShipInteractor;
import humeniuk.battleships.interactor.shipCount.GetAvailableShipCountInteractor;
import humeniuk.battleships.interactor.shipPlacementAvailabilityCheck.ShipPlacementAvailabilityCheckInteractor;
import humeniuk.battleships.presentation.base.RxPresenter;
import humeniuk.battleships.presentation.preparation.router.PreparationRouter;
import humeniuk.battleships.presentation.preparation.view.PreparationView;
import io.reactivex.Scheduler;

public class PreparationPresenterImpl extends RxPresenter implements PreparationPresenter {

    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    private PreparationRouter router;

    private PreparationView view;

    private ShipPlacementAvailabilityCheckInteractor shipPlacementAvailabilityCheckInteractor;
    private GetAvailableShipCountInteractor getAvailableShipCountInteractor;
    private InitEmptyFieldInteractor initEmptyFieldInteractor;
    private RemoveShipInteractor removeShipInteractor;
    private FindShipOnTableInteractor findShipOnTableInteractor;

    private Ship currentShip;

    private Table table;

    private Player player1;
    private Player player2;

    private int playersReady;

    public PreparationPresenterImpl(Scheduler ioScheduler,
                                    Scheduler uiScheduler,
                                    PreparationRouter router,
                                    ShipPlacementAvailabilityCheckInteractor shipPlacementAvailabilityCheckInteractor,
                                    GetAvailableShipCountInteractor getAvailableShipCountInteractor,
                                    InitEmptyFieldInteractor initEmptyFieldInteractor,
                                    RemoveShipInteractor removeShipInteractor,
                                    FindShipOnTableInteractor findShipOnTableInteractor) {
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        this.router = router;
        this.shipPlacementAvailabilityCheckInteractor = shipPlacementAvailabilityCheckInteractor;
        this.getAvailableShipCountInteractor = getAvailableShipCountInteractor;
        this.initEmptyFieldInteractor = initEmptyFieldInteractor;
        this.removeShipInteractor = removeShipInteractor;
        this.findShipOnTableInteractor = findShipOnTableInteractor;
    }

    @Override
    public void setView(PreparationView view) {
        this.view = view;
    }

    @Override
    public void init() {
        //TODO: fix to support custom player names
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        playersReady = 0;
        initPlayer(player1);
    }

    @Override
    public void onCellClick(int x, int y) {
        if (currentShip != null) {
            placeShipCell(x, y);
        }
    }

    @Override
    public void onCellLongClick(int x, int y) {
        if (currentShip == null) {
            findAndRemoveShip(x, y);
        }
    }

    @Override
    public void onShipButtonClick(ShipType shipType) {
        boolean sameType = false;
        if (currentShip != null) {
            sameType = shipType.equals(currentShip.getType());
            removeShip(currentShip);
            view.updateShipButtonSelected(currentShip.getType(), false);
            currentShip = null;
        }
        if (!sameType) {
            currentShip = new Ship(shipType);
            view.updateShipButtonSelected(shipType, true);
        }
    }

    @Override
    public void onReadyClick() {
        if (playersReady == 0) {
            playersReady++;
            initPlayer(player2);
        } else {
            router.openBattle(player1, player2);
        }
    }

    private void initPlayer(Player player) {
        subscribe(0, initEmptyFieldInteractor.initEmptyField()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(newTable -> {
                    unsubscribe(0);
                    player.setTable(newTable);
                    this.table = player.getTable();
                    view.setPlayerName(player.getName());
                    view.initField(table.getCells());
                    view.updateReadyButtonVisibility(table.isComplete());
                    updateShipCount();
                }));
    }

    private void removeShip(Ship ship) {
        subscribe(1, removeShipInteractor.removeShip(ship)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(result -> {
                    unsubscribe(1);
                    updateShipCount();
                    for (Cell cell : ship.getCells()) {
                        view.updateCellView(cell);
                    }
                }));
    }

    private void updateShipCount() {
        subscribe(2, getAvailableShipCountInteractor.getAvailableShipCount(table)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(shipMap -> {
                    unsubscribe(2);
                    view.updateAvailableShipCount(shipMap);
                }));
    }

    private void placeShipCell(int x, int y) {
        subscribe(3, shipPlacementAvailabilityCheckInteractor.checkIfCellAvailable(table, currentShip, x, y)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(placed -> {
                    unsubscribe(3);
                    if (placed) {
                        Cell cell = table.getCells()[y][x];
                        cell.setCellState(CellState.SHIP);
                        view.updateCellView(cell);
                        currentShip.addCell(cell);
                        if (currentShip.isComplete()) {
                            table.addShip(currentShip);
                            view.updateShipButtonSelected(currentShip.getType(), false);
                            updateShipCount();
                            currentShip = null;
                            view.updateReadyButtonVisibility(table.isComplete());
                        }
                    }
                }));
    }

    private void findAndRemoveShip(int x, int y) {
        subscribe(4, findShipOnTableInteractor.findShipOnTable(table, x, y)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(ship -> {
                    unsubscribe(4);
                    if (ship != null) {
                        table.removeShip(ship);
                        removeShip(ship);
                    }
                }));
    }
}
