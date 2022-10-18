package Widgets;

import ManagersAndServices.TurnsManager;

import javax.swing.*;
import java.awt.*;

/**
 * Controller of the Widget responsible for displaying Badges
 */
public class BadgesController extends SketchEmAllWidget {
    private final BadgesPresentation badgesPresentation;
    private final BadgesModel badgesModel;
    public String currentWord;

    /**
     * Default constructor
     @param turnManager reference to {@link TurnsManager}
     */
    public BadgesController(TurnsManager turnManager) {
        this.badgesModel = new BadgesModel();
        this.badgesPresentation = new BadgesPresentation();

        this.badgesPresentation.InstallUI(this);

        this.currentWord = turnManager.getWordUsedInTheTurn();
        repaint();
    }

    public double[] getPointInScreenOfNextBadge() {
        return null;
    }

    /**
     * Creates new badge with
     *
     * @param badgeIcon     the screenshot of the drawing
     * @param paintModeUsed the color of the mode
     * @param word          the word guessed
     */
    public void createNewBadge(ImageIcon badgeIcon, Color paintModeUsed, String word) {

        badgesModel.setCurrentBadge(badgeIcon, paintModeUsed, word);
        revalidate();
        repaint();
    }

    public BadgesModel getModel() {
        return badgesModel;
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if (badgesPresentation != null) {
            badgesPresentation.paint((Graphics2D) pen, this);
        }
    }

    @Override
    public void reset(){
        badgesModel.resetAllLists();
    }

    @Override
    public Dimension getPreferredSize() {
        return badgesPresentation.getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {return badgesPresentation.getPreferredSize();}

    @Override
    public Dimension getSize() {
        return badgesPresentation.getPreferredSize();
    }
}
