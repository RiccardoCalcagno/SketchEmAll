package Widgets;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class WordPickerModel {

    enum State {
        END_TURN_TEXT,
        CHEST,
        WORD_PICKER
    };
    private final WordPickerController wordPickerController;

    private boolean isFlipped;

    private boolean isReadyToExitProcedure;
    public boolean getIsReadyToExitProcedure(){
        return this.isReadyToExitProcedure;
    }

    public void setIsReadyToExitProcedure(boolean isReadyToExitProcedure){
        if(!this.isReadyToExitProcedure){
            this.isReadyToExitProcedure = true;
            notifyChange();
        }
    }


    public WordPickerModel(WordPickerController controller){
        wordPickerController = controller;
        this.isFlipped = false;
        this.isReadyToExitProcedure = false;
    }

    public boolean getIsFlipped(){
        return this.isFlipped;
    }
    public void setIsFlipped(boolean isFlipped){
        if(this.isFlipped != isFlipped){
            this.isFlipped = isFlipped;
            notifyChange();
        }
    }


    private final List<ChangeListener> changeListeners = new ArrayList<>();

    public void addChangeListener(ChangeListener changeListenerToAdd){
        changeListeners.add(changeListenerToAdd);
    }
    public void removeChangeListener(ChangeListener changeListenerToRemove){
        changeListeners.add(changeListenerToRemove);
    }
    public void notifyChange(){
        ChangeEvent changeEvent = new ChangeEvent(this);

        for (ChangeListener changeListener: changeListeners){
            changeListener.stateChanged(changeEvent);
        }
    }


}
