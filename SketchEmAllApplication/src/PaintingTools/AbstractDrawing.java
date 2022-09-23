package PaintingTools;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDrawing{


    public AbstractDrawing(){

        this.isEmpty = true;
    }

    private boolean isEmpty;
    protected void setIsEmpty(boolean isEmpty){
        if(this.isEmpty != isEmpty){
            this.isEmpty = isEmpty;
            notifyChange();
        }
    }
    public boolean IsEmpty(){
        return this.isEmpty;
    }

    public final void paint(Graphics2D pen){

        if(this.isEmpty == false){
            paintTemplateMethod(pen);
        }
    }


    public void closeDrawing(){}


    protected abstract void paintTemplateMethod(Graphics2D pen);




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
