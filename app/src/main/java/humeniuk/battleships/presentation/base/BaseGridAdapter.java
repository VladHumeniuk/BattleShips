package humeniuk.battleships.presentation.base;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.presentation.preparation.view.CellClickListener;
import humeniuk.battleships.presentation.utils.CellViewUtil;

import static humeniuk.battleships.domain.Constants.TABLE_SIZE;

public abstract class BaseGridAdapter extends BaseAdapter {

    private Context context;
    private Cell[][] cells;
    private ImageView[][] cellViews = new ImageView[TABLE_SIZE][TABLE_SIZE];
    private CellViewUtil cellViewUtil;

    private CellClickListener listener;

    public BaseGridAdapter(Context context, Cell[][] cells, CellViewUtil cellViewUtil, CellClickListener listener) {
        this.context = context;
        this.cells = cells;
        this.cellViewUtil = cellViewUtil;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return cells.length * cells[0].length;
    }

    @Override
    public Cell getItem(int i) {
        return cells[i/cells.length][i%cells.length];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public ImageView[][] getCellViews() {
        return cellViews;
    }

    protected Context getContext() {
        return context;
    }

    protected CellViewUtil getCellViewUtil() {
        return cellViewUtil;
    }

    protected CellClickListener getListener() {
        return listener;
    }
}
