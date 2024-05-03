package it.polimi.ingsw.gc31.view.tui.tuiObj;

public class PointTUI {
    public int x;
    public int y;

    public PointTUI(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PointTUI(PointTUI point) {
        x = point.x;
        y = point.y;
    }

    @Override
    public boolean equals(Object obj) {
        PointTUI object = (PointTUI) obj;
        if (object.x == this.x && object.y == this.y) {
            return true;
        } else
            return false;
    }

}
