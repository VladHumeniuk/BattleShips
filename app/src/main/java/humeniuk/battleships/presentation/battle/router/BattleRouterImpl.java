package humeniuk.battleships.presentation.battle.router;

import humeniuk.battleships.R;
import humeniuk.battleships.presentation.base.BaseActivity;
import humeniuk.battleships.presentation.base.BaseRouter;
import humeniuk.battleships.presentation.main.view.MainFragment;

public class BattleRouterImpl extends BaseRouter implements BattleRouter {

    public BattleRouterImpl(BaseActivity activity) {
        super(activity, R.id.fragment_container);
    }

    @Override
    public void openMainFragment() {
        clearBackStack();
        showFragment(MainFragment.newInstance(), false);
    }
}
