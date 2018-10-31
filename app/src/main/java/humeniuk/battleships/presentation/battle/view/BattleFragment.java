package humeniuk.battleships.presentation.battle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import humeniuk.battleships.presentation.battle.presenter.BattlePresenter;
import humeniuk.battleships.presentation.battle.view.adapter.BattleGridAdapter;
import humeniuk.battleships.presentation.main.MainActivity;
import humeniuk.battleships.presentation.preparation.view.CellClickListener;
import humeniuk.battleships.presentation.utils.CellViewUtil;

public class BattleFragment extends BaseFragment implements BattleView {

    @Inject
    BattlePresenter presenter;

    @Inject
    protected CellViewUtil cellViewUtil;

    @BindView(R.id.grid_container)
    protected View gridContainer;

    @BindView(R.id.grid)
    protected GridView gridView;

    @BindView(R.id.player_label)
    protected AppCompatTextView playerLabelView;

    @BindView(R.id.label_ship_4)
    protected AppCompatTextView ship4Label;

    @BindView(R.id.label_ship_3)
    protected AppCompatTextView ship3Label;

    @BindView(R.id.label_ship_2)
    protected AppCompatTextView ship2Label;

    @BindView(R.id.label_ship_1)
    protected AppCompatTextView ship1Label;

    @BindView(R.id.button_end_turn)
    protected AppCompatButton endTurnButton;

    private Map<ShipType, AppCompatTextView> shipLabelsMap;

    private BattleGridAdapter adapter;

    public static BattleFragment newInstance() {

        Bundle args = new Bundle();

        BattleFragment fragment = new BattleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        presenter.setView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_battle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //TODO: remove hacks, load players from db
        MainActivity activity = (MainActivity) getBaseActivity();
        presenter.init(activity.getPlayer1(), activity.getPlayer2());
        return view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        shipLabelsMap = new HashMap<>();
        shipLabelsMap.put(ShipType.SIZE_1, ship1Label);
        shipLabelsMap.put(ShipType.SIZE_2, ship2Label);
        shipLabelsMap.put(ShipType.SIZE_3, ship3Label);
        shipLabelsMap.put(ShipType.SIZE_4, ship4Label);
    }

    @Override
    public void initField(Cell[][] tableCells) {
        gridContainer.setVisibility(View.INVISIBLE);
        adapter = new BattleGridAdapter(getActivity(), tableCells, cellViewUtil, cellClickListener);
        gridView.setAdapter(adapter);
    }

    @Override
    public void startTurn(String playerName) {
        playerLabelView.setText(getString(R.string.shoot_label, playerName));
        gridView.setFocusable(true);
        endTurnButton.setVisibility(View.INVISIBLE);
        new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage(getString(R.string.player_turn_label, playerName))
                .setPositiveButton(R.string.ok_label, (v, i) -> gridContainer.setVisibility(View.VISIBLE))
                .show();
    }

    @Override
    public void updateShipsLeftCount(Map<ShipType, Integer> shipCountMap) {
        for (ShipType shipType : shipCountMap.keySet()) {
            shipLabelsMap.get(shipType).setText(getString(R.string.ship_count, shipType.getSize(), shipCountMap.get(shipType)));
        }
    }

    @Override
    public void updateCellView(Cell cell) {
        adapter.getCellViews()[cell.getY()][cell.getX()].setImageResource(cellViewUtil.getCellBattleBackgroundByState(cell.getCellState()));
        gridView.setAdapter(adapter);
    }

    @Override
    public void notifyMiss() {
        playerLabelView.setText(R.string.miss_label);
        gridView.setFocusable(false);
        endTurnButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void endGame(String winner) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.congratulations_label)
                .setMessage(getString(R.string.win_label, winner))
                .setCancelable(false)
                .setPositiveButton(R.string.ok_label, (v, i) -> presenter.finish())
                .show();
    }

    @OnClick(R.id.button_end_turn)
    protected void onEndTurnClick() {
        presenter.onEndTurnClick();
    }

    private CellClickListener cellClickListener = new CellClickListener() {

        @Override
        public void onCellClick(int x, int y) {
            presenter.onCellClick(x, y);
        }

        @Override
        public void onCellLongClick(int x, int y) {
        }
    };
}
