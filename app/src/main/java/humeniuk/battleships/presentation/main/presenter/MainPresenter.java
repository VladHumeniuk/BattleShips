package humeniuk.battleships.presentation.main.presenter;

import humeniuk.battleships.presentation.main.view.MainView;

public interface MainPresenter {

    void setView(MainView mainView);

    void onNewGameClick();
}
