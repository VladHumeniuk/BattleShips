package humeniuk.battleships.presentation.preparation.router;

import humeniuk.battleships.domain.entity.Player;

public interface PreparationRouter {

    void openBattle(Player player1, Player player2);
}
