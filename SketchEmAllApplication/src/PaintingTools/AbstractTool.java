package PaintingTools;

import Widgets.CanvasController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public abstract class AbstractTool extends MouseAdapter implements KeyListener {

    protected CanvasController canvasWhereToDraw;
    public void setCanvasWhereToDraw(CanvasController canvasWhereToDraw){
        if(this.canvasWhereToDraw != null){
            this.canvasWhereToDraw.removeKeyListener(this);
            this.canvasWhereToDraw.removeMouseListener(this);
            this.canvasWhereToDraw.removeMouseMotionListener(this);
        }
        this.canvasWhereToDraw = canvasWhereToDraw;
        if(this.canvasWhereToDraw != null){
            this.canvasWhereToDraw.addMouseListener(this);
            this.canvasWhereToDraw.addMouseMotionListener(this);
            this.canvasWhereToDraw.addKeyListener(this);
        }
    }


    public abstract void applyCurrentTransformationOnSubject(AbstractDrawing subject);

    public abstract AbstractDrawing getNewDrawing();

    public abstract PaintingToolsEnum getPaintingToolsEnum();



    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
