package humeniuk.battleships.presentation.battle.view;

import java.util.Map;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.ShipType;

public interface BattleView {

    void initField(Cell[][] tableCells);

    void startTurn(String playerName);

    void updateShipsLeftCount(Map<ShipType, Integer> shipCountMap);

    void updateCellView(Cell cell);

    void notifyMiss();

    void endGame(String winner);
}
