package humeniuk.battleships.domain.entity;

public enum ShipType {

    SIZE_4(4, 1),
    SIZE_3(3, 2),
    SIZE_2(2, 3),
    SIZE_1(1, 4);

    private int size;
    private int availableCount;

    ShipType(int size, int availableCount) {
        this.size = size;
        this.availableCount = availableCount;
    }

    public int getSize() {
        return size;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public static int getTotalCount() {
        int count = 0;
        for (ShipType shipType : values()) {
            count += shipType.getAvailableCount();
        }
        return count;
    }
}
