package Widgets;

import InternModels.PaintMode;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;

public class BadgesPresentation {

    private static final Dimension PREFERRED_SIZE_BADGES_BOX = new Dimension(500, 180);
    private static final Dimension PREFERRED_SIZE_BADGE = new Dimension(150,100);
    private BadgesController bc;
    private BadgesModel bm;


    public BadgesPresentation(BadgesController controller){
        this.bc = controller;
        /*
        JPanel badgesPanel = new JPanel();
        //JLabel iconBadge = new JLabel(controller.currBadge);
        //badgesPanel.add(iconBadge);
        //controller.setLayout(new BorderLayout());
        //controller.add(badgesPanel, BorderLayout.NORTH);
        controller.setLayout(new BorderLayout());
        controller.setPreferredSize(controller.getPreferredSize());
        controller.add(badgesPanel, BorderLayout.NORTH);
        */
    }
    public void paint(Graphics2D pen, BadgesController controller)
    {
        this.bc = controller;
        this.bm = controller.getModel();
        pen.setColor(Color.red);
        //pen.fillRect(0,0, getPreferredSize().width,getPreferredSize().height);

        ImageIcon img = controller.getModel().getCurrBadge();

        int imageDimension = (int)(controller.getPreferredSize().height * 0.7d);

        //placeholder
        /*
        pen.drawImage(img.getImage(),
                50,
                (int)(controller.getPreferredSize().height * 0.15d),
                imageDimension,imageDimension,null);
        */
        //draw all badges
        int x = 0;
        System.out.println(bm.getListBadges().size());
        for (int i = 0; i < bm.getListBadges().size() ; i++) {

            pen.drawImage(bm.getListBadges().get(i).getImage(),
                    x,
                    (int) (controller.getPreferredSize().height * 0.15d),
                    PREFERRED_SIZE_BADGE.width,PREFERRED_SIZE_BADGE.height, null);
            //makeBadgeBorder(pen,controller,controller.currPaintMode, controller.currWord);
            x = x+PREFERRED_SIZE_BADGE.width;
        }


    }

    public void makeBadgeBorder(Graphics2D pen, BadgesController controller, PaintMode paintMode, String word){
        // TODO
        //border with paint mode color and a label with the word
        pen.setStroke(new BasicStroke(5f));

        pen.setColor(paintMode.timerRepresentativeColor);


    }


    public Dimension getPreferredSize() {return PREFERRED_SIZE_BADGES_BOX;}
}
