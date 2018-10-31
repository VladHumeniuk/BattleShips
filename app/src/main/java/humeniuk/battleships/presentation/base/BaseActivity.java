package humeniuk.battleships.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import humeniuk.battleships.BattleShipsApplication;
import humeniuk.battleships.di.components.ActivityComponent;
import humeniuk.battleships.di.components.DaggerActivityComponent;
import humeniuk.battleships.di.modules.ActivityModule;
import humeniuk.battleships.di.modules.InteractorModule;
import humeniuk.battleships.di.modules.UtilModule;
import humeniuk.battleships.di.modules.PresenterModule;
import humeniuk.battleships.di.modules.RouterModule;
import humeniuk.battleships.di.modules.SchedulerModule;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(BattleShipsApplication.getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .interactorModule(new InteractorModule())
                .presenterModule(new PresenterModule())
                .routerModule(new RouterModule())
                .schedulerModule(new SchedulerModule())
                .build();
        inject(activityComponent);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initViews();
    }

    protected void inject(ActivityComponent activityComponent) {
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            this.finish();
        } else {
            super.onBackPressed();
        }
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected abstract int getLayoutId();

    protected void initViews() {
    }
}
