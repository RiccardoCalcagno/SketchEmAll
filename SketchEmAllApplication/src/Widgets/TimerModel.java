package Widgets;

import InternModels.PaintMode;
import InternModels.TimerSlice;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimerModel {

    private boolean isActive = false;

    public boolean isActive(){
        return this.isActive;
    }
    public void setIsActive(boolean isActive){
        this.isActive = isActive;
        notifyChange();
    }

    private boolean isGameOccasionallyInterrupted;

    /**
     * ture if the user has interrupted the timer by pressing the button
     */
    public boolean isGameOccasionallyInterrupted() {
        return this.isGameOccasionallyInterrupted;
    }
    public void setGameOccasionallyInterrupted(boolean gameOccasionallyInterrupted) {
        this.isGameOccasionallyInterrupted = gameOccasionallyInterrupted;
        notifyChange();
    }

    private final ArrayList<TimerSlice> timerSlices = new ArrayList<>();

    public void addTimerSlice(TimerSlice timerSlice){
        timerSlices.add(timerSlice);
        notifyChange();
    }
    public ArrayList<TimerSlice> getTimerSlices(){
        return timerSlices;
    }

    /**
     * This operation will prevent the last slice to grow more, so that its paramiter like isWonMode and isGrowing will get
     * their final value
     * @param isWon
     *  in order to stop the growth of a slice you need to explaining the reason (the turn is won or is loosed)
     */
    public void stopGrowthOfCurrentSlice(boolean isWon){
        if(timerSlices.size() > 0) {
            var currentSlice = timerSlices.get(timerSlices.size() - 1);
            currentSlice.isWonMode = isWon;
            currentSlice.isGrowing = false;
            notifyChange();
        }
    }

    /**
     * To set to the last slice of the sliced timer a new end (figurative by a new angle that may enlarge the slice,
     * pratically by a new percentage of the whole circle)
     * @param endPercentageTime
     *  percentage of the sliced timer where to put the end of the last slice
     */
    public void setCurrentSliceEndPercentageTime(double endPercentageTime){
        if(timerSlices.size() > 0){
            timerSlices.get(timerSlices.size()-1).setEndingPercentageTime(endPercentageTime);
            notifyChange();
        }
    }

    public void resetTimerSlices(){
        timerSlices.clear();
        notifyChange();
    }

    private final List<ChangeListener> changeListeners = new ArrayList<>();
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
