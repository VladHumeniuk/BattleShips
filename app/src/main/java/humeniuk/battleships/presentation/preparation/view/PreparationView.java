package humeniuk.battleships.presentation.preparation.view;

import java.util.Map;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.ShipType;

public interface PreparationView {

    void initField(Cell[][] tableCells);

    void updateCellView(Cell cell);

    void updateAvailableShipCount(Map<ShipType, Integer> shipCountMap);

    void updateShipButtonSelected(ShipType shipType, boolean selected);

    void setPlayerName(String name);

    void updateReadyButtonVisibility(boolean visible);
}
