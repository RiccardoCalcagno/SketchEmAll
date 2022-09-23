package Widgets;

import PaintingTools.AbstractDrawing;

import javax.management.OperationsException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CanvasModel {

    private HashSet<AbstractDrawing> drawings = new HashSet<>();
    public HashSet<AbstractDrawing> getDrawings(){
        return drawings;
    }
    public void removeDrawing(AbstractDrawing drawingToRemove){
        if(currentDrawing == drawingToRemove){
            closeEditOfDrawing();
        }
        drawings.remove(drawingToRemove);
        notifyChange();
    }
    public void addNewDrawing(AbstractDrawing drawing) {
        drawing.addChangeListener(e -> notifyChange());
        drawings.add(currentDrawing);
        notifyChange();
    }


    private AbstractDrawing currentDrawing = null;
    public AbstractDrawing getCurrentDrawing(){
        return this.currentDrawing;
    }
    public boolean isDrawing(){
        return this.currentDrawing != null;
    }
    public void chooseDrawingToEdit(AbstractDrawing drawingToOpenInEditMode){
        if(currentDrawing!= null){
            closeEditOfDrawing();
        }
        if(drawings.contains(drawingToOpenInEditMode) == false){
            return;
        }

        currentDrawing = drawingToOpenInEditMode;
    }
    public void closeEditOfDrawing(){
        if(currentDrawing != null){
            currentDrawing.closeDrawing();
            currentDrawing = null;
        }
    }



    private List<ChangeListener> changeListeners = new ArrayList<>();
    public void addChangeListener(ChangeListener changeListenerToAdd){
        changeListeners.add(changeListenerToAdd);
    }
    public void removeChangeListener(ChangeListener changeListenerToRemove){
        changeListeners.remove(changeListenerToRemove);
    }
    public void notifyChange(){
        ChangeEvent changeEvent = new ChangeEvent(this);

        for (ChangeListener changeListener: changeListeners){
            changeListener.stateChanged(changeEvent);
        }
    }
}
