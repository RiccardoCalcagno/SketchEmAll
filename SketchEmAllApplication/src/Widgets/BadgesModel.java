package Widgets;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class BadgesModel {
    private ImageIcon currBadge;
    private List<ImageIcon> listBadges = new ArrayList<>();

    public void setCurrBadge(ImageIcon badgeIcon) {
        this.currBadge = badgeIcon;
        this.addBadgeToList(currBadge);
        notifyChange();
    }
    public ImageIcon getCurrBadge(){
        return  this.currBadge;
    }

    public List<ImageIcon> getListBadges(){
        return  this.listBadges;
    }
    public void addBadgeToList(ImageIcon curr){
        this.listBadges.add(curr);
        this.notifyChange();
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
