package Widgets;

import PaintingTools.AbstractDrawing;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.util.HashSet;

public class CanvasPresentation{

    private static final Dimension PREFERED_SIZE_CANVAS = new Dimension(500, 300);

    public void installUI(CanvasController canvas) {
        installListeners(canvas);
    }

    protected void installListeners(CanvasController canvast)
    {

    }

    public void paint(Graphics2D pen, CanvasController canvasController) {
        CanvasModel model = canvasController.getModel();

        pen.setColor(Color.white);
        pen.fillRect(0,0, canvasController.getWidth(), canvasController.getHeight());

        pen.setColor(Color.black);
        var drawAnnotations = model.getDrawings();
        for (var drawAnnotation: drawAnnotations) {

            drawAnnotation.paint(pen);
        }
    }


    public Dimension getPreferredSize() {
        return PREFERED_SIZE_CANVAS;
    }
    public Dimension getMaximumSize() {
        return PREFERED_SIZE_CANVAS;
    }
    public Dimension getMinimumSize() {
        return PREFERED_SIZE_CANVAS;
    }
}
