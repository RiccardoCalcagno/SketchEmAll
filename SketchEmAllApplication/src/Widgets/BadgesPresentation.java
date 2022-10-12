package Widgets;

import InternModels.PaintMode;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;

import static ManagersAndServices.StyleUtility.drawPaintModeFrame;

public class BadgesPresentation {

    private static final Dimension PREFERRED_SIZE_BADGES_BOX = new Dimension(900, 180);
    private static final Dimension PREFERRED_SIZE_BADGE = new Dimension(150,100);
    private BadgesController bc;
    private BadgesModel bm;


    public BadgesPresentation(BadgesController controller){
        this.bc = controller;

    }
    public void paint(Graphics2D pen, BadgesController controller)
    {
        this.bc = controller;
        this.bm = controller.getModel();
        pen.setColor(Color.red);

        //placeholder

        //draw all badges
        int x = 10;
        System.out.println(bm.getListBadges().size());
        for (int i = 0; i < bm.getListBadges().size() ; i++) {

            pen.drawImage(bm.getListBadges().get(i).getImage(),
                    x,
                    (int) (bc.getPreferredSize().height * 0.15d),
                    PREFERRED_SIZE_BADGE.width,PREFERRED_SIZE_BADGE.height, null);
            Point topLeftcorner = new Point(x,(int) (bc.getPreferredSize().height * 0.15d));
            pen.setStroke(new BasicStroke(5f));
            drawPaintModeFrame(pen,bm.getListColors().get(i),bm.getListWords().get(i),topLeftcorner,PREFERRED_SIZE_BADGE,new Color(20,20,20));
            x = x+PREFERRED_SIZE_BADGE.width;
        }

    }


    public Dimension getPreferredSize() {return PREFERRED_SIZE_BADGES_BOX;}
}
