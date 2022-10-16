package PaintingDrawings;

import PaintingDrawings.AbstractDrawing;
import PaintingDrawings.PointExtendable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PolylineDrawing extends AbstractDrawing implements PointExtendable {

    public PolylineDrawing() {
        super();
    }

    @Override
    protected void paintTemplateMethod(Graphics2D pen) {
        pen.drawPolyline(getXPoints(), getYPoints(), getNumberOfPoints());
    }

    private final List<Point> points = new ArrayList<>();

    private int getNumberOfPoints() {
        return points.size();
    }

    @Override
    public void addPoint(Point newPointToAdd) {
        points.add(newPointToAdd);
        setIsEmpty(false);
        notifyChange();
    }

    private int[] getXPoints() {
        int[] outPut = new int[getNumberOfPoints()];

        for (int i=0; i<points.size(); i++) {
            outPut[i] = points.get(i).x;
        }
        return outPut;
    }

    private int[] getYPoints() {
        int[] outPut = new int[getNumberOfPoints()];

        for (int i=0; i<points.size(); i++) {
            outPut[i] = points.get(i).y;
        }
        return outPut;
    }
}
