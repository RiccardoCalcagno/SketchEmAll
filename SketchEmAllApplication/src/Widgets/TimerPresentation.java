package Widgets;

import javax.swing.*;
import java.awt.*;

public class TimerPresentation {

    private static final Dimension PREFERRED_SIZE_TIMER_BOX = new Dimension(250, 100);


    public void paint(Graphics2D pen, JComponent component)
    {
        TimerController timerController = ((TimerController)component);
        TimerModel model = timerController.getModel();
        pen.setColor(Color.red);
        pen.fillRect(0,0, getPreferredSize().width,getPreferredSize().height);

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
