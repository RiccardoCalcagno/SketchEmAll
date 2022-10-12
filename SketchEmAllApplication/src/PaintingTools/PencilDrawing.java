package PaintingTools;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PencilDrawing extends AbstractDrawing implements PointExtendable{

    public PencilDrawing() {
        super();
    }

    @Override
    protected void paintTemplateMethod(Graphics2D pen) {
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        pen.setRenderingHints(rh);
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
