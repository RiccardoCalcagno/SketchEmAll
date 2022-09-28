package Widgets;

import java.awt.*;

public class CanvasPresentation{

    private static final Dimension PREFERRED_SIZE_CANVAS = new Dimension(600, 400);

    public void installUI(CanvasController canvas) {
        installListeners(canvas);
    }

    protected void installListeners(CanvasController canvas) {

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
        return PREFERRED_SIZE_CANVAS;
    }
    public Dimension getMaximumSize() {
        return PREFERRED_SIZE_CANVAS;
    }
    public Dimension getMinimumSize() {
        return PREFERRED_SIZE_CANVAS;
    }
}
