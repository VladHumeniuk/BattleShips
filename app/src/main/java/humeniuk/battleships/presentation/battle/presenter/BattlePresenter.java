package humeniuk.battleships.presentation.battle.presenter;

import humeniuk.battleships.domain.entity.Player;
import humeniuk.battleships.presentation.battle.view.BattleView;

public interface BattlePresenter {

    void setView(BattleView view);

    void init(Player player1, Player player2);

    void onCellClick(int x, int y);

    void onEndTurnClick();

    void finish();
}
