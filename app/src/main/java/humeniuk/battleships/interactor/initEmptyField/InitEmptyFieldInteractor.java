package humeniuk.battleships.interactor.initEmptyField;

import humeniuk.battleships.domain.entity.Table;
import io.reactivex.Observable;

public interface InitEmptyFieldInteractor {

    Observable<Table> initEmptyField();
}
