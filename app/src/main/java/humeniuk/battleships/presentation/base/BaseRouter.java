package humeniuk.battleships.presentation.base;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class BaseRouter {

    private BaseActivity activityContext;

    private int containerId;

    public BaseRouter(BaseActivity activity, int containerId) {
        this.activityContext = activity;
        this.containerId = containerId;
    }

    protected void showFragment(BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }

    protected void clearBackStack() {
        getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected void startActivity(Intent intent) {
        activityContext.startActivity(intent);
    }

    protected void startActivityForResult(Intent intent, int requestCode) {
        activityContext.startActivityForResult(intent, requestCode);
    }

    protected BaseActivity getActivityContext() {
        return activityContext;
    }

    protected FragmentManager getFragmentManager() {
        return activityContext.getSupportFragmentManager();
    }

    protected int getContainerId() {
        return containerId;
    }
}
