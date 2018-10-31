package humeniuk.battleships.presentation.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.OnClick;
import humeniuk.battleships.R;
import humeniuk.battleships.di.components.ActivityComponent;
import humeniuk.battleships.presentation.base.BaseFragment;
import humeniuk.battleships.presentation.main.presenter.MainPresenter;

public class MainFragment extends BaseFragment implements MainView {

    @Inject
    protected MainPresenter presenter;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.setView(this);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @OnClick(R.id.button_new_game)
    protected void onNewGameClick() {
        presenter.onNewGameClick();
    }
}
