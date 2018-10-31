package humeniuk.battleships.di.modules;

import dagger.Module;
import dagger.Provides;
import humeniuk.battleships.di.scopes.UserScope;
import humeniuk.battleships.presentation.base.BaseActivity;
import humeniuk.battleships.presentation.battle.router.BattleRouter;
import humeniuk.battleships.presentation.battle.router.BattleRouterImpl;
import humeniuk.battleships.presentation.main.router.MainRouter;
import humeniuk.battleships.presentation.main.router.MainRouterImpl;
import humeniuk.battleships.presentation.preparation.router.PreparationRouter;
import humeniuk.battleships.presentation.preparation.router.PreparationRouterImpl;

@Module
public class RouterModule {

    @Provides
    @UserScope
    MainRouter provideMainRouter(BaseActivity baseActivity) {
        return new MainRouterImpl(baseActivity);
    }

    @Provides
    @UserScope
    PreparationRouter providePreparationRouter(BaseActivity baseActivity) {
        return new PreparationRouterImpl(baseActivity);
    }

    @Provides
    @UserScope
    BattleRouter provideBattleRouter(BaseActivity baseActivity) {
        return new BattleRouterImpl(baseActivity);
    }
}
