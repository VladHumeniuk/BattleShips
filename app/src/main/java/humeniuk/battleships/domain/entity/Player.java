package humeniuk.battleships.domain.entity;

public class Player {

    private String name;

    private Table table;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
