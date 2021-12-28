package controller;

import java.awt.*;

public class PointLine {
    public Point p1, p2;

    //mỗi Icon mình coi là một Point với 2 tọa độ x,y, class PointLine này được tạo ra để định nghĩa việc
    // kết nối 2 Icon với nhau. Cụ thể, một PointLine sẽ gồm hai Point p1, p2, tương ứng là hai Icon trong game.
    public PointLine(Point p1, Point p2) {
        super();
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return "(" + p1.x + "," + p1.y + ") and (" + p2.x + "," + p2.y + ")";
    }
}
