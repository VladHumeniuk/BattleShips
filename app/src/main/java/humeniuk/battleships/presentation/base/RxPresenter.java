package humeniuk.battleships.presentation.base;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class RxPresenter {

    private Map<Integer, Disposable> subscriptions = new HashMap<>();

    protected void subscribe(int id, Disposable observable) {
        if (subscriptions.containsKey(id)) {
            subscriptions.remove(id);
        }
        subscriptions.put(id, observable);
    }

    protected void unsubscribe(int id) {
        subscriptions.remove(id);
    }
}
