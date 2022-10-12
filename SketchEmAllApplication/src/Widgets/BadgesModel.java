package Widgets;

import InternModels.PaintMode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BadgesModel {
    private ImageIcon currBadge;
    private Color currPaintMode;
    private String currWord;

    private List<ImageIcon> listBadges = new ArrayList<>();
    private List<Color> listColors = new ArrayList<>();
    private List<String> listWords = new ArrayList<>();

    public void setCurrBadge(ImageIcon badgeIcon, Color modeColor, String wordGuessed) {
        this.currBadge = badgeIcon;
        this.currPaintMode = modeColor;
        this.currWord = wordGuessed;
        this.addBadgeToList(currBadge,currPaintMode,currWord);
        notifyChange();
    }

    public ImageIcon getCurrBadge(){
        return  this.currBadge;
    }
    public Color getCurrPaintMode() {return this.currPaintMode;}
    public String getCurrWord() {return this.currWord;}

    public List<ImageIcon> getListBadges(){
        return  this.listBadges;
    }
    public List<Color> getListColors(){
        return  this.listColors;
    }
    public List<String> getListWords(){
        return  this.listWords;
    }

    public void addBadgeToList(ImageIcon currI, Color currC, String currW){
        this.listBadges.add(currI);
        this.listColors.add(currC);
        this.listWords.add(currW);
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
