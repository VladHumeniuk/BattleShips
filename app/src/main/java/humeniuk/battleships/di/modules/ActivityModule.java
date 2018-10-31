package humeniuk.battleships.di.modules;

import dagger.Module;
import dagger.Provides;
import humeniuk.battleships.di.scopes.UserScope;
import humeniuk.battleships.presentation.base.BaseActivity;

@Module
public class ActivityModule {

    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    @UserScope
    BaseActivity provideBaseActivity() {
        return baseActivity;
    }
}
