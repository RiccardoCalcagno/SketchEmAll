package PaintingTools;

import PaintingDrawings.AbstractDrawing;
import PaintingDrawings.PointExtendable;
import PaintingDrawings.PolylineDrawing;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PencilTool extends AbstractTool {

    protected Point nextPointToAdd;

    @Override
    public void applyCurrentTransformationOnSubject(AbstractDrawing subject) {

        if(nextPointToAdd !=null){
            ((PointExtendable)subject).addPoint(nextPointToAdd);
        }
    }

    protected void addNewAlteredPointFromPointer(Point pointerLocation){
        nextPointToAdd = pointerLocation;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        addNewAlteredPointFromPointer(e.getPoint());

        canvasWhereToDraw.editCurrentDrawing(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        canvasWhereToDraw.closeCurrentDrawing();
    }


    @Override
    public AbstractDrawing getNewDrawing() {
        return new PolylineDrawing();
    }
    @Override
    public PaintingToolsEnum getPaintingToolsEnum() {
        return PaintingToolsEnum.PENCIL;
    }
}
