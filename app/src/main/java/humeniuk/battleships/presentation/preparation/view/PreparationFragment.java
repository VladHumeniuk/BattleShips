package humeniuk.battleships.presentation.preparation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import humeniuk.battleships.R;
import humeniuk.battleships.di.components.ActivityComponent;
import humeniuk.battleships.domain.entity.Cell;
import humeniuk.battleships.domain.entity.ShipType;
import humeniuk.battleships.presentation.base.BaseFragment;
import humeniuk.battleships.presentation.preparation.presenter.PreparationPresenter;
import humeniuk.battleships.presentation.preparation.view.adapter.CellAdapter;
import humeniuk.battleships.presentation.utils.CellViewUtil;

public class PreparationFragment extends BaseFragment implements PreparationView {

    @Inject
    protected PreparationPresenter presenter;

    @Inject
    protected CellViewUtil cellViewUtil;

    @BindView(R.id.grid)
    protected GridView gridView;

    @BindView(R.id.button_ship_4)
    protected AppCompatButton buttonShip4;

    @BindView(R.id.button_ship_3)
    protected AppCompatButton buttonShip3;

    @BindView(R.id.button_ship_2)
    protected AppCompatButton buttonShip2;

    @BindView(R.id.button_ship_1)
    protected AppCompatButton buttonShip1;

    @BindView(R.id.player_label)
    protected AppCompatTextView playerLabel;

    @BindView(R.id.button_ready)
    protected AppCompatButton readyButton;

    private Map<ShipType, AppCompatButton> shipButtonsMap;

    private CellAdapter adapter;

    public static PreparationFragment newInstance() {

        Bundle args = new Bundle();

        PreparationFragment fragment = new PreparationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_preparation;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.init();
        return view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        shipButtonsMap = new HashMap<>();
        shipButtonsMap.put(ShipType.SIZE_1, buttonShip1);
        shipButtonsMap.put(ShipType.SIZE_2, buttonShip2);
        shipButtonsMap.put(ShipType.SIZE_3, buttonShip3);
        shipButtonsMap.put(ShipType.SIZE_4, buttonShip4);
        for (ShipType shipType : shipButtonsMap.keySet()) {
            shipButtonsMap.get(shipType).setOnClickListener(v -> presenter.onShipButtonClick(shipType));
        }
    }

    @Override
    public void initField(Cell[][] tableCells) {
        adapter = new CellAdapter(getActivity(), tableCells, cellViewUtil, cellClickListener);
        gridView.setAdapter(adapter);
    }

    @Override
    public void updateCellView(Cell cell) {
        adapter.getCellViews()[cell.getY()][cell.getX()].setImageResource(cellViewUtil.getCellBackgroundByState(cell.getCellState()));
    }

    @Override
    public void updateAvailableShipCount(Map<ShipType, Integer> shipCountMap) {
        for (ShipType shipType : shipCountMap.keySet()) {
            setAvailableShipText(shipButtonsMap.get(shipType), shipType.getSize(), shipCountMap.get(shipType));
        }
    }

    @Override
    public void updateShipButtonSelected(ShipType shipType, boolean selected) {
        shipButtonsMap.get(shipType).setSelected(selected);
    }

    @Override
    public void setPlayerName(String name) {
        playerLabel.setText(name);
    }

    @Override
    public void updateReadyButtonVisibility(boolean visible) {
        readyButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick(R.id.button_ready)
    protected void onReadyClick() {
        presenter.onReadyClick();
    }

    private void setAvailableShipText(AppCompatButton button, int size, int availableCount) {
        button.setText(getString(R.string.ship_count, size, availableCount));
        button.setClickable(availableCount != 0);
    }

    private CellClickListener cellClickListener = new CellClickListener() {
        @Override
        public void onCellClick(int x, int y) {
            presenter.onCellClick(x, y);
        }

        @Override
        public void onCellLongClick(int x, int y) {
            presenter.onCellLongClick(x, y);
        }
    };
}
