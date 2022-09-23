package PaintingTools;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PencilTool extends AbstractTool {

    private Point lastPointOfPointerDown;

    @Override
    public void applyCurrentTransformationOnSubject(AbstractDrawing subject) {

        if(lastPointOfPointerDown !=null){
            ((PointExtendable)subject).addPoint(lastPointOfPointerDown);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        lastPointOfPointerDown = e.getPoint();

        canvasWhereToDraw.editCurrentDrawing(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        canvasWhereToDraw.closeCurrentDrawing();
    }


    @Override
    public AbstractDrawing getNewDrawing() {
        return new PencilDrawing();
    }
    @Override
    public PaintingToolsEnum getPaintingToolsEnum() {
        return PaintingToolsEnum.PENCIL;
    }
}
