package humeniuk.battleships.presentation.main.presenter;

import humeniuk.battleships.presentation.main.router.MainRouter;
import humeniuk.battleships.presentation.main.view.MainView;

public class MainPresenterImpl implements MainPresenter {

    private MainRouter router;

    private MainView view;

    public MainPresenterImpl(MainRouter router) {
        this.router = router;
    }

    @Override
    public void setView(MainView mainView) {
        this.view = view;
    }

    @Override
    public void onNewGameClick() {
        router.openPreparationFragment();
    }
}
