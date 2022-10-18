package Widgets;

import ManagersAndServices.AppLayoutManager;

import javax.swing.*;
import java.awt.*;

import static ManagersAndServices.AppLayoutManager.BACKGROUND_APPLICATION;
import static ManagersAndServices.StyleUtility.drawPaintModeFrame;

/**
 * Presentation of the Widget responsible for displaying Badges
 */
public class BadgesPresentation {

    private static final Dimension PREFERRED_SIZE_BADGES_BOX = new Dimension(980, 250);
    private static final Dimension PREFERRED_SIZE_BADGE = new Dimension(150, 100);
    private static final int BADGES_PER_LINE = 6;
    private BadgesController badgesController;
    private BadgesModel badgesModel;


    public BadgesPresentation() {


    }

    public void InstallUI(BadgesController controller){
        this.badgesController = controller;

        InstallUIStructure(controller);
    }


    private void InstallUIStructure(BadgesController controller){
        controller.setLayout(new FlowLayout(FlowLayout.TRAILING));


    }

    public void paint(Graphics2D pen, BadgesController controller) {
        this.badgesController = controller;
        this.badgesModel = controller.getModel();
        pen.setColor(Color.red);

        int preferredHorizontalSpacing = (PREFERRED_SIZE_BADGES_BOX.width - BADGES_PER_LINE*PREFERRED_SIZE_BADGE.width - 20) / (BADGES_PER_LINE-1);
        int verticalSpacing = 20;
        //draw all badges
        int x;
        int y;
        for (int i = 0; i < badgesModel.getListBadges().size(); i++) {
           x = 10 + (PREFERRED_SIZE_BADGE.width + preferredHorizontalSpacing)*(i%BADGES_PER_LINE);
           y = (int) (badgesController.getPreferredSize().height * 0.05d) + (PREFERRED_SIZE_BADGE.height + verticalSpacing) * (i/BADGES_PER_LINE);
           pen.drawImage(badgesModel.getListBadges().get(i).getImage(),
                    x,
                    y,
                    PREFERRED_SIZE_BADGE.width, PREFERRED_SIZE_BADGE.height, null);

            Point topLeftCorner = new Point(x, y);
            pen.setStroke(new BasicStroke(5f));
            drawPaintModeFrame(pen, badgesModel.getListColors().get(i), badgesModel.getListWords().get(i), topLeftCorner, PREFERRED_SIZE_BADGE, new Color(20, 20, 20));

        }
    }


    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_BADGES_BOX;
    }
}
