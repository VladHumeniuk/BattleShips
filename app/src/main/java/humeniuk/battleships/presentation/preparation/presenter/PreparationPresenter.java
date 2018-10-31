package humeniuk.battleships.presentation.preparation.presenter;

import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.presentation.preparation.view.PreparationView;

public interface PreparationPresenter {

    void setView(PreparationView view);

    void init();

    void onCellClick(int x, int y);

    void onCellLongClick(int x, int y);

    void onShipButtonClick(ShipType shipType);

    void onReadyClick();
}
