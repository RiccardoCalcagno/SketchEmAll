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

    private static final Dimension PREFERRED_SIZE_BADGES_BOX = new Dimension(800, 250);
    private static final Dimension PREFERRED_SIZE_BADGE = new Dimension(150, 100);
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

        //placeholder

        //draw all badges
        int x;
        int y;
        for (int i = 0; i < badgesModel.getListBadges().size(); i++) {
           if(i < 6) {
               x = 10 + PREFERRED_SIZE_BADGE.width*i;
               y = (int) (badgesController.getPreferredSize().height * 0.05d);
           } else {
               x = 10 + PREFERRED_SIZE_BADGE.width*(i-6);
               y = (int) (badgesController.getPreferredSize().height * 0.05d) + PREFERRED_SIZE_BADGE.height;
           }
            pen.drawImage(badgesModel.getListBadges().get(i).getImage(),
                    x,
                    y,
                    PREFERRED_SIZE_BADGE.width, PREFERRED_SIZE_BADGE.height, null);

            Point topLeftcorner = new Point(x, y);
            pen.setStroke(new BasicStroke(5f));
            drawPaintModeFrame(pen, badgesModel.getListColors().get(i), badgesModel.getListWords().get(i), topLeftcorner, PREFERRED_SIZE_BADGE, new Color(20, 20, 20));

        }
    }


    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_BADGES_BOX;
    }
}
