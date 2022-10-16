package PaintingTools;

import PaintingDrawings.AbstractDrawing;
import Widgets.CanvasController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public abstract class AbstractTool extends MouseAdapter{

    protected CanvasController canvasWhereToDraw;
    public void setCanvasWhereToDraw(CanvasController canvasWhereToDraw){
        if(this.canvasWhereToDraw != null){
            this.canvasWhereToDraw.removeMouseListener(this);
            this.canvasWhereToDraw.removeMouseMotionListener(this);
        }
        this.canvasWhereToDraw = canvasWhereToDraw;
        if(this.canvasWhereToDraw != null){
            this.canvasWhereToDraw.addMouseListener(this);
            this.canvasWhereToDraw.addMouseMotionListener(this);
        }
    }


    public abstract void applyCurrentTransformationOnSubject(AbstractDrawing subject);

    public abstract AbstractDrawing getNewDrawing();

    public abstract PaintingToolsEnum getPaintingToolsEnum();
}
