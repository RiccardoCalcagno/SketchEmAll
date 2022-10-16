package Widgets;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WordsInputModel {

    private Color currColor;

    public void setCurrColor(Color color){
        this.currColor = color;
        notifyChange();
    }

    public Color getCurrColor(){
        return this.currColor;
    }
    private boolean isActive = false;
    public boolean isActive(){
        return this.isActive;
    }
    public void setIsActive(boolean isActive){
        this.isActive = isActive;
        notifyChange();
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
