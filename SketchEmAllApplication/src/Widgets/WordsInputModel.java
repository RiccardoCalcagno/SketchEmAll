package Widgets;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Abstraction class of Words Input
 @see WordsInputController
 */
public class WordsInputModel {

    private boolean isActive = false;
    /**
     * Chek if the input field is active
     @return active or not
     */
    public boolean isActive(){
        return this.isActive;
    }
    /**
     * Get if the input field is active or not
     @param isActive true or false
     */
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
