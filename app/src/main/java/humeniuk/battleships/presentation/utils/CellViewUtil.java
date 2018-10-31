package humeniuk.battleships.presentation.utils;

import android.support.annotation.DrawableRes;

import humeniuk.battleships.R;
import humeniuk.battleships.domain.entity.CellState;

public class CellViewUtil {

    @DrawableRes
    public int getCellBackgroundByState(CellState cellState) {
        switch (cellState) {
            case MISS:
                return R.drawable.cell_miss;
            case SHIP:
                return R.drawable.cell_ship;
            case SHIP_DAMAGED:
                return R.drawable.cell_ship_damaged;
            case SHIP_DESTROYED:
                return R.drawable.cell_ship_destroyed;
            default:
                return R.drawable.cell_empty;
        }
    }

    @DrawableRes
    public int getCellBattleBackgroundByState(CellState cellState) {
        switch (cellState) {
            case MISS:
                return R.drawable.cell_miss;
            case SHIP_DAMAGED:
                return R.drawable.cell_ship_damaged;
            case SHIP_DESTROYED:
                return R.drawable.cell_ship_destroyed;
            default:
                return R.drawable.cell_empty;
        }
    }
}
