package Widgets;

import ManagersAndServices.AppLayoutManager;
import ManagersAndServices.StyleUtility;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class CanvasPresentation{

    private static final Dimension PREFERRED_SIZE_CANVAS = new Dimension(600, 400);
    private static final Dimension MINIMUM_SIZE_CANVAS = new Dimension(100, 70);

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

        if(canvasController.isActive()){
            StyleUtility.drawPaintModeFrame(
                    pen,
                    canvasController.getCurrentPaintMode(),
                    new Point(0,0),
                    canvasController.getSize(),
                    AppLayoutManager.BACKGROUND_APPLICATION
            );
        }
    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_CANVAS;
    }
    public Dimension getMaximumSize() {
        return PREFERRED_SIZE_CANVAS;
    }
    public Dimension getMinimumSize() {
        return MINIMUM_SIZE_CANVAS;
    }
}
