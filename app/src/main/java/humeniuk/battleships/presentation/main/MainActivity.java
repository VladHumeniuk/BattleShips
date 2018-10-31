package humeniuk.battleships.presentation.main;

import android.os.Bundle;

import javax.inject.Inject;

import humeniuk.battleships.R;
import humeniuk.battleships.di.components.ActivityComponent;
import humeniuk.battleships.domain.entity.Player;
import humeniuk.battleships.presentation.base.BaseActivity;
import humeniuk.battleships.presentation.main.router.MainRouter;

public class MainActivity extends BaseActivity {

    @Inject
    protected MainRouter router;

    //TODO: remove hacks, store players data in db
    private Player player1;
    private Player player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router.openMainFragment();
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
