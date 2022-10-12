package Widgets;

import InternModels.TurnEndReason;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for the mode picking utility
 * @see WordPickerController
 */
public class WordPickerModel {
    private boolean isFlipped;
    private final List<ChangeListener> changeListeners = new ArrayList<>();

    /**
     * Model for the mode picking utility
     * @param controller
     *  Controller of the word picking utility
     * @param callReason
     *  Parameter to know the previous state of the game that led to the word picking
     */
    public WordPickerModel(WordPickerController controller, TurnEndReason callReason){
        this.isFlipped = false;
        switch (callReason){
            case NONE -> controller.setDescription(controller.FRONT_SIDE_CARD_DESCRIPTION);

            case NO_MORE_ATTEMPTS -> controller.setDescription(controller.THIRD_FAIL_DESCRIPTION);

            case TURN_TIMER_EXPIRATION -> controller.setDescription(controller.OUT_OF_TIME_DESCRIPTION);

            case WORD_GUESSED -> controller.setDescription(controller.WIN_DESCRIPTION);
        }
    }


    public void addChangeListener(ChangeListener changeListenerToAdd){
        changeListeners.add(changeListenerToAdd);
    }
    public void removeChangeListener(ChangeListener changeListenerToRemove){
        changeListeners.add(changeListenerToRemove);
    }

    /**
     * Notifies the change listeners about a change in the model.
     */
    public void notifyChange(){
        ChangeEvent changeEvent = new ChangeEvent(this);

        for (ChangeListener changeListener: changeListeners){
            changeListener.stateChanged(changeEvent);
        }
    }

    /**
     * Flips the card of the word picker
     */
    public void flip(){
        this.isFlipped = !this.isFlipped;
        notifyChange();
    }

    /**
     * Returns whether the card of the word picker is flipped
     * @return
     */
    public boolean isFlipped() {
        return isFlipped;
    }
}
