package Widgets;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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


    public ImageIcon getTimerImage(){
        return  this.timerIcon;
    }
    public void setTimerImage(ImageIcon timerImage){
        this.timerIcon = timerImage;
    }

    public  void updateTimerImage(ImageIcon timerImgage){
        this.timerIcon = timerImgage;
    }
}
