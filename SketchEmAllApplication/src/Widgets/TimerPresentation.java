package Widgets;

import ManagersAndServices.AppLayoutManager;
import ManagersAndServices.RepositoryService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimerPresentation {

    private JButton pauseResumeButton;

    private ImageIcon playIcon, pauseIcon;

    private JLabel digitalSessionTimeLabel;

    public TimerPresentation(){

        playIcon = RepositoryService.loadImageFromResources("playIcon.png");
        pauseIcon = RepositoryService.loadImageFromResources("pauseIcon.png");
    }

    private static final Stroke BASIC_STROKE = new BasicStroke();
    private static final Stroke LIGHT_THICK_STROKE = new BasicStroke(2.5f);

    private static final Dimension PREFERRED_SIZE_TIMER_WIDGET = new Dimension(300, 200);

    private static final double VISUALLY_SAFE_PERCENTAGE_OF_SESSION_TIME = 0.95d;

    public void installUI(TimerController timer) {
        installComponentsHierarchy(timer);
        installListeners(timer);

        timer.repaint();
    }

    protected void installListeners(TimerController timer) {
        this.pauseResumeButton.addActionListener(
                e -> timer.handleOccasionalPauseResumeRequest(true)
        );
    }

    protected void installComponentsHierarchy(TimerController timer){

        timer.setLayout(new BorderLayout());

        //JLabel timerImagePlaceholder = new JLabel(RepositoryService.loadImageFromResources("timerPlaceholder.png"));

        this.pauseResumeButton = new JButton();
        this.pauseResumeButton.setBackground(Color.white);
        this.pauseResumeButton.setPreferredSize(new Dimension(90, 45));
        var panelForButtonAndLabel = new JPanel();
        panelForButtonAndLabel.setMaximumSize(new Dimension(150, (int)getPreferredSize().getHeight()));
        panelForButtonAndLabel.setBackground(AppLayoutManager.BACKGROUND_APPLICATION);
        var layoutForButtonAndLabel = new BoxLayout(panelForButtonAndLabel, BoxLayout.Y_AXIS);
        pauseResumeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelForButtonAndLabel.setLayout(layoutForButtonAndLabel);
        panelForButtonAndLabel.add(Box.createVerticalGlue());
        panelForButtonAndLabel.add(pauseResumeButton);
        panelForButtonAndLabel.add(Box.createVerticalGlue());

        this.digitalSessionTimeLabel = new JLabel();
        this.digitalSessionTimeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.digitalSessionTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.digitalSessionTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        panelForButtonAndLabel.add(digitalSessionTimeLabel);
        panelForButtonAndLabel.add(Box.createVerticalGlue());

        timer.add(panelForButtonAndLabel, BorderLayout.EAST);
    }


    private void setEnabledPauseResumeButton(boolean isEnabled){
        this.pauseResumeButton.setEnabled(isEnabled);
    }
    private void updateLocalPauseResume(boolean isPaused){
        if(isPaused){
            this.pauseResumeButton.setIcon(pauseIcon);
        }
        else{
            this.pauseResumeButton.setIcon(playIcon);
        }
    }
    private void setDigitalFormatOfTimeOfSession(LocalTime digitalTimeFormat, double percentageTimeOfSession){

        if(percentageTimeOfSession >= VISUALLY_SAFE_PERCENTAGE_OF_SESSION_TIME){
            digitalSessionTimeLabel.setForeground(Color.red);
        }
        else{
            digitalSessionTimeLabel.setForeground(Color.white);
        }

        digitalSessionTimeLabel.setText(digitalTimeFormat.format(DateTimeFormatter.ISO_LOCAL_TIME).substring(3));
    }


    public void paint(Graphics2D pen, JComponent component) {
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        pen.setRenderingHints(rh);

        TimerController timerController = ((TimerController)component);
        TimerModel model = timerController.getModel();

        setEnabledPauseResumeButton(model.isActive());

        updateLocalPauseResume(model.isGameOccasionallyInterrupted());

        double percentageTimeOfSession = 0;
        var currentSlice = timerController.getCurrentSlice();
        if(currentSlice!= null){
            percentageTimeOfSession = currentSlice.getEndingPercentageTime();
        }

        setDigitalFormatOfTimeOfSession(timerController.getCurrentSessionTime(), percentageTimeOfSession);

        pen.setStroke(BASIC_STROKE);

        var slices = model.getTimerSlices();
        for(var slice : slices){
            slice.paint(pen, this);
        }

        // ----------------------------------  arrow of sliced timer ----------------------------------
        pen.setStroke(LIGHT_THICK_STROKE);
        var centerOfSlicedTimer = getCenterOfSlicedTimer();
        var arrowLength = getRadiusOfSlicedTimer() * 0.5d;
        var degreeOfArrow = Math.PI * 2.0d * percentageTimeOfSession;
        var endPointOfArrow = new Point(
                (int)(Math.cos(Math.PI * 1.5d + degreeOfArrow) * arrowLength + centerOfSlicedTimer.x),
                (int)(Math.sin(Math.PI * 1.5d - degreeOfArrow) * arrowLength + centerOfSlicedTimer.y)
        );
        pen.setColor(Color.WHITE);
        pen.drawLine(centerOfSlicedTimer.x, centerOfSlicedTimer.y, endPointOfArrow.x, endPointOfArrow.y);
    }


    public Point getCenterOfSlicedTimer(){
        var dimensionOfWidget = getPreferredSize();
        return new Point((int)(dimensionOfWidget.getWidth() * 0.395d), (int)(dimensionOfWidget.getHeight() * 0.49d ));
    }
    public int getRadiusOfSlicedTimer(){
        return (int)(getPreferredSize().getWidth() * 0.3d);
    }


    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_TIMER_WIDGET;
    }
    public Dimension getMaximumSize() {
        return PREFERRED_SIZE_TIMER_WIDGET;
    }
    public Dimension getMinimumSize() {
        return PREFERRED_SIZE_TIMER_WIDGET;
    }
}
