package humeniuk.battleships.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import humeniuk.battleships.di.components.ActivityComponent;

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        inject(getBaseActivity().getActivityComponent());
        unbinder = ButterKnife.bind(this, view);
        setActionBar();
        setHasOptionsMenu(showOptionsMenu());
        initViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void setActionBar() {
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        if (getTitle() != 0) {
            actionBar.setTitle(getTitle());
        }
        if (showActionBar()) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    protected void inject(ActivityComponent activityComponent) {

    }

    protected void initViews() {

    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @StringRes
    protected int getTitle() {
        return 0;
    }

    protected boolean showActionBar() {
        return true;
    }

    protected boolean showOptionsMenu() {
        return false;
    }
}
