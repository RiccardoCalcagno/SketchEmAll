package Widgets;

import ManagersAndServices.RepositoryService;

import javax.swing.*;
import java.awt.*;

public class TimerPresentation {

    private JButton pauseResumeButton;

    private ImageIcon playIcon, pauseIcon;

    public TimerPresentation(){

        playIcon = RepositoryService.loadImageFromResources("playIcon.png");
        pauseIcon = RepositoryService.loadImageFromResources("pauseIcon.png");
    }

    public void installUI(TimerController timer) {
        installComponentsHierarchy(timer);
        installListeners(timer);

        timer.repaint();
    }

    protected void installListeners(TimerController timer) {

    }

    protected void installComponentsHierarchy(TimerController timer){

        FlowLayout layoutOfParent = new FlowLayout(FlowLayout.CENTER);
        layoutOfParent.setHgap(40);
        timer.setLayout(layoutOfParent);

        JLabel timerImagePlaceholder = new JLabel(RepositoryService.loadImageFromResources("timerPlaceholder.png"));

        this.pauseResumeButton = new JButton();
        this.pauseResumeButton.addActionListener( e -> timer.handleOccasionalPauseResumeRequest(true));
        this.pauseResumeButton.setPreferredSize(new Dimension(80, 50));

        timer.add(timerImagePlaceholder);
        timer.add(this.pauseResumeButton);
    }

    private static final Dimension PREFERRED_SIZE_TIMER_BOX = new Dimension(250, 100);


    public void paint(Graphics2D pen, JComponent component)
    {
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
