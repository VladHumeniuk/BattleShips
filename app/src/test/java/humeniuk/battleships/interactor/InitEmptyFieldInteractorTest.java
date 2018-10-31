package humeniuk.battleships.interactor;

import org.junit.Before;
import org.junit.Test;

import humeniuk.battleships.domain.entity.Table;
import humeniuk.battleships.interactor.initEmptyField.InitEmptyFieldInteractor;
import humeniuk.battleships.interactor.initEmptyField.InitEmptyFieldInteractorImpl;
import humeniuk.battleships.testUtils.TableTestUtil;
import io.reactivex.observers.TestObserver;

public class InitEmptyFieldInteractorTest {

    private InitEmptyFieldInteractor interactor;

    private TableTestUtil tableTestUtil;

    private TestObserver<Table> testObserver;

    @Before
    public void init() {
        tableTestUtil = new TableTestUtil();
        interactor = new InitEmptyFieldInteractorImpl();
        testObserver = new TestObserver<>();
    }

    @Test
    public void testInitEmptyField() {
        interactor.initEmptyField().subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(table -> tableTestUtil.isTableEmpty(table));
    }
}
