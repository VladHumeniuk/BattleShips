package humeniuk.battleships.presentation.preparation.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.presentation.base.BaseGridAdapter;
import humeniuk.battleships.presentation.preparation.view.CellClickListener;
import humeniuk.battleships.presentation.utils.CellViewUtil;

public class CellAdapter extends BaseGridAdapter {

    public CellAdapter(Context context, Cell[][] cells, CellViewUtil cellViewUtil, CellClickListener listener) {
        super(context, cells, cellViewUtil, listener);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Cell cell = getItem(i);
        ImageView cellView = new ImageView(getContext());
        cellView.setAdjustViewBounds(true);
        cellView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        cellView.setImageResource(getCellViewUtil().getCellBackgroundByState(cell.getCellState()));
        getCellViews()[cell.getY()][cell.getX()] = cellView;
        cellView.setOnClickListener(v -> getListener().onCellClick(cell.getX(), cell.getY()));
        cellView.setOnLongClickListener(v -> {
            getListener().onCellLongClick(cell.getX(), cell.getY());
            return true;
        });
        return cellView;
    }
}
