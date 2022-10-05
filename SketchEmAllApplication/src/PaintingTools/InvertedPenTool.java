package PaintingTools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Pen tool that draws backwards. Up is down, Left is right
 */
public class InvertedPenTool extends PencilTool{

    private Point pencilStart;

    /**
     * Calculates the point symmetric to the provided point with {@link #pencilStart}
     * @param mousePosition
     *  Current position of the mouse cursor
     * @return
     *  Symmetric point
     */
    private Point findInvertedPosition(Point mousePosition){
        int deltaX = mousePosition.x - pencilStart.x;
        int deltaY = mousePosition.y - pencilStart.y;
        Dimension limits = canvasWhereToDraw.getSize();
        int newX = Math.min(limits.width, pencilStart.x-deltaX);
        newX = Math.max(0, newX);
        int newY = Math.min(limits.height, pencilStart.y-deltaY);
        newY = Math.max(0, newY);
        return new Point(newX, newY);
    }

    /**
     * Defines the next point to draw
     * @param pointerLocation
     *  Current location of the mouse pointer
     */
    @Override
    public void addNewAlteredPointFromPointer(Point pointerLocation){
        nextPointToAdd = findInvertedPosition(pointerLocation);
    }

    @Override
    public PaintingToolsEnum getPaintingToolsEnum() {
        return PaintingToolsEnum.INVERTED_PEN;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pencilStart = e.getPoint();
        super.mousePressed(e);
    }
}
