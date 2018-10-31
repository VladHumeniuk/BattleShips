package humeniuk.battleships.presentation.main.router;

import humeniuk.battleships.R;
import humeniuk.battleships.presentation.base.BaseActivity;
import humeniuk.battleships.presentation.base.BaseRouter;
import humeniuk.battleships.presentation.main.view.MainFragment;
import humeniuk.battleships.presentation.preparation.view.PreparationFragment;

public class MainRouterImpl extends BaseRouter implements MainRouter {

    public MainRouterImpl(BaseActivity activity) {
        super(activity, R.id.fragment_container);
    }

    @Override
    public void openPreparationFragment() {
        showFragment(PreparationFragment.newInstance(), false);
    }

    @Override
    public void openMainFragment() {
        clearBackStack();
        showFragment(MainFragment.newInstance(), false);
    }
}
