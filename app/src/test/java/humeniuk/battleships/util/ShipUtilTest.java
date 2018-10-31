package humeniuk.battleships.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import humeniuk.battleships.domain.entity.Ship;
import humeniuk.battleships.presentation.utils.ShipUtil;
import humeniuk.battleships.testUtils.TestEntityFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipUtilTest {

    private ShipUtil shipUtil;

    private TestEntityFactory entityFactory;

    @Before
    public void init() {
        shipUtil = new ShipUtil();
        entityFactory = new TestEntityFactory();
    }

    @Test
    public void testIsShipAlive() {
        Ship ship = entityFactory.getAliveShip();
        assertTrue(shipUtil.isShipAlive(ship));
    }

    @Test
    public void testIsDamagedShipAlive() {
        Ship ship = entityFactory.getDamagedShip();
        assertTrue(shipUtil.isShipAlive(ship));
    }

    @Test
    public void testIsFullyDamagedShipAlive() {
        Ship ship = entityFactory.getFullDamagedShip();
        assertFalse(shipUtil.isShipAlive(ship));
    }

    @Test
    public void testIsDestroyedShipAlive() {
        Ship ship = entityFactory.getDestroyedShip();
        assertFalse(shipUtil.isShipAlive(ship));
    }

    @Test
    public void testDestroyShip() {
        Ship ship = entityFactory.getAliveShip();
        shipUtil.makeShipDestroyed(ship);
        assertFalse(shipUtil.isShipAlive(ship));
    }
}
