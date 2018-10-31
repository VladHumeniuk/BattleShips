package humeniuk.battleships.di.components;

import dagger.Component;
import humeniuk.battleships.di.modules.ActivityModule;
import humeniuk.battleships.di.modules.InteractorModule;
import humeniuk.battleships.di.modules.PresenterModule;
import humeniuk.battleships.di.modules.RouterModule;
import humeniuk.battleships.di.modules.SchedulerModule;
import humeniuk.battleships.di.scopes.UserScope;
import humeniuk.battleships.presentation.battle.view.BattleFragment;
import humeniuk.battleships.presentation.main.view.MainFragment;
import humeniuk.battleships.presentation.preparation.view.PreparationFragment;
import humeniuk.battleships.presentation.main.MainActivity;

@UserScope
@Component(dependencies = ApplicationComponent.class,
        modules = {InteractorModule.class,
                ActivityModule.class,
                RouterModule.class,
                PresenterModule.class,
                SchedulerModule.class
        })
public interface ActivityComponent {

        void inject(MainActivity activity);

        void inject(PreparationFragment fragment);

        void inject(BattleFragment fragment);

        void inject(MainFragment fragment);
}
