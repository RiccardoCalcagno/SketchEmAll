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

    private ImageIcon timerIcon;
    private boolean isActive = false;
    public boolean isActive(){
        return this.isActive;
    }
    public void setIsActive(boolean isActive){
        this.isActive = isActive;
        notifyChange();
    }

    private boolean isGameOccasionallyInterrupted;
    public boolean isGameOccasionallyInterrupted() {
        return this.isGameOccasionallyInterrupted;
    }
    public void setGameOccasionallyInterrupted(boolean gameOccasionallyInterrupted) {
        this.isGameOccasionallyInterrupted = gameOccasionallyInterrupted;
        notifyChange();
    }

    public ArrayList<TimerSlice> timerSlices = new ArrayList<>();

    public void addTimerSlice(TimerSlice timerSlice){
        timerSlices.add(timerSlice);
        notifyChange();
    }
    public ArrayList<TimerSlice> getTimerSlices(){
        return timerSlices;
    }
    public void stopGrowthOfCurrentSlice(boolean isWon){
        if(timerSlices.size() > 0) {
            var currentSlice = timerSlices.get(timerSlices.size() - 1);
            currentSlice.isWonMode = isWon;
            currentSlice.isGrowing = false;
            notifyChange();
        }
    }
    public void setCurrentSliceEndPercentageTime(double endPercentageTime){
        if(timerSlices.size() > 0){
            timerSlices.get(timerSlices.size()-1).setEndingPercentageTime(endPercentageTime);
            notifyChange();
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
