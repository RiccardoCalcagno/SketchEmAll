package Widgets;

import ManagersAndServices.SessionManager;
import ManagersAndServices.TimeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TimerPresentation {

    public TimerPresentation(){


    }

    private static final Dimension PREFERRED_SIZE_TIMER_BOX = new Dimension(250, 100);


    public void paint(Graphics2D pen, JComponent component)
    {
        TimerController timerController = ((TimerController)component);
        TimerModel model = timerController.getModel();
        pen.setColor(Color.red);
        //pen.fillRect(0,0, getPreferredSize().width,getPreferredSize().height);

        ImageIcon img = timerController.getModel().getTimerImage();


        var image = img.getImage();

        int imageDimension = (int)(getPreferredSize().height * 0.7d);

        //placeholder
        pen.drawImage(img.getImage(),
                10,
                (int)(getPreferredSize().height * 0.15d),
                imageDimension,imageDimension,null);

    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_TIMER_BOX;
    }
    public Dimension getMaximumSize() {
        return PREFERRED_SIZE_TIMER_BOX;
    }
    public Dimension getMinimumSize() {
        return PREFERRED_SIZE_TIMER_BOX;
    }
}
