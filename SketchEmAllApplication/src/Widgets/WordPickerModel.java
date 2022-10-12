package Widgets;

import InternModels.TurnEndReason;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class WordPickerModel {
    private boolean isFlipped;

    private final boolean isReadyToExitProcedure;
    public boolean getIsReadyToExitProcedure(){
        return this.isReadyToExitProcedure;
    }

    public WordPickerModel(WordPickerController controller, TurnEndReason callReason){
        this.isFlipped = false;
        this.isReadyToExitProcedure = false;
        switch (callReason){
            case NONE -> controller.setDescription(controller.FRONT_SIDE_CARD_DESCRIPTION);

            case NO_MORE_ATTEMPTS -> controller.setDescription(controller.THIRD_FAIL_DESCRIPTION);

            case TURN_TIMER_EXPIRATION -> controller.setDescription(controller.OUT_OF_TIME_DESCRIPTION);

            case WORD_GUESSED -> controller.setDescription(controller.WIN_DESCRIPTION);
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

    public void flip(){
        this.isFlipped = !this.isFlipped;
        notifyChange();
    }

    public boolean isFlipped() {
        return isFlipped;
    }
}
