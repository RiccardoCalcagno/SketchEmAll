package InternModels;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class PointToPointComparator implements Comparator<Point2D> {

    public Point2D pointToGetNear;

    @Override
    public int compare(Point2D o1, Point2D o2) {
        return (o1.distance(pointToGetNear) < o2.distance(pointToGetNear)) ? -1 : 1;
    }
}
