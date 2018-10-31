package humeniuk.battleships.presentation.preparation.router;

import humeniuk.battleships.R;
import humeniuk.battleships.domain.entity.Player;
import humeniuk.battleships.presentation.base.BaseActivity;
import humeniuk.battleships.presentation.base.BaseRouter;
import humeniuk.battleships.presentation.battle.view.BattleFragment;
import humeniuk.battleships.presentation.main.MainActivity;

public class PreparationRouterImpl extends BaseRouter implements PreparationRouter {

    public PreparationRouterImpl(BaseActivity activity) {
        super(activity, R.id.fragment_container);
    }

    @Override
    public void openBattle(Player player1, Player player2) {
        if (getActivityContext() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivityContext();
            activity.setPlayer1(player1);
            activity.setPlayer2(player2);
            showFragment(BattleFragment.newInstance(), false);
        }

    }
}
