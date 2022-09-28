package Widgets;

import javax.swing.*;
import java.awt.*;

public class BadgesPresentation {

    private static final Dimension PREFERRED_SIZE_BADGES_BOX = new Dimension(500, 180);


    public BadgesPresentation(BadgesController controller){
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

        pen.setColor(Color.red);
        //pen.fillRect(0,0, getPreferredSize().width,getPreferredSize().height);

        ImageIcon img = controller.getModel().getCurrBadge();

        int imageDimension = (int)(controller.getPreferredSize().height * 0.7d);

        //placeholder
        pen.drawImage(img.getImage(),
                100,
                (int)(controller.getPreferredSize().height * 0.15d),
                imageDimension,imageDimension,null);

    }


    public Dimension getPreferredSize() {return PREFERRED_SIZE_BADGES_BOX;}
}
