package PaintingDrawings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InkBlotDrawing extends AbstractDrawing {
    @Override
    protected void paintTemplateMethod(Graphics2D pen) {
        pen.fillPolygon(finalShape);
    }

    public InkBlotDrawing() {
        super();
    }


    private Polygon finalShape;


    private List<Point> points = new ArrayList<>();

    private int getNumberOfPoints() {
        return points.size();
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

