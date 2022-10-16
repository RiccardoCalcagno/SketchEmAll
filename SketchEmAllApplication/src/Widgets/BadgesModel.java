package Widgets;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of the Widget responsible for displaying Badges
 */
public class BadgesModel {
    private ImageIcon currentBadge;
    private Color currentPaintMode;
    private String currentWord;

    private List<ImageIcon> listBadges = new ArrayList<>();
    private List<Color> listColors = new ArrayList<>();
    private List<String> listWords = new ArrayList<>();

    /**
     * setter for the current badge
     *
     * @param badgeIcon   sets the screenshot of current image drawn on the canvas
     * @param modeColor   sets the color of the turn
     * @param wordGuessed sets the word that was guessed
     *                    also adds the badge to a list so it can later display all badges
     */
    public void setCurrentBadge(ImageIcon badgeIcon, Color modeColor, String wordGuessed) {
        this.currentBadge = badgeIcon;
        this.currentPaintMode = modeColor;
        this.currentWord = wordGuessed;
        this.addBadgeToList(currentBadge, currentPaintMode, currentWord);
        notifyChange();
    }

    /**
     * Getter for all badges
     *
     * @return list of badges
     */
    public List<ImageIcon> getListBadges() {
        return this.listBadges;
    }

    /**
     * Getter for all colors of badges
     *
     * @return list of colors
     */
    public List<Color> getListColors() {
        return this.listColors;
    }

    /**
     * Getter for all words guessed
     *
     * @return list of words
     */
    public List<String> getListWords() {
        return this.listWords;
    }

    /**
     * Addes new badge to list
     *
     * @param currI current image of badge
     * @param currC current color of badge frame
     * @param currW current word guessed
     */
    public void addBadgeToList(ImageIcon currI, Color currC, String currW) {
        this.listBadges.add(currI);
        this.listColors.add(currC);
        this.listWords.add(currW);
        this.notifyChange();
    }

    private List<ChangeListener> changeListeners = new ArrayList<>();
    public void addChangeListener(ChangeListener changeListenerToAdd) {
        changeListeners.add(changeListenerToAdd);
    }
    public void removeChangeListener(ChangeListener changeListenerToRemove) {
        changeListeners.remove(changeListenerToRemove);
    }
    public void notifyChange() {
        ChangeEvent changeEvent = new ChangeEvent(this);

        for (ChangeListener changeListener : changeListeners) {
            changeListener.stateChanged(changeEvent);
        }
    }
}
