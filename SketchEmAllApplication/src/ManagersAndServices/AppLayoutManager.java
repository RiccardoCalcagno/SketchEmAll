package ManagersAndServices;

import Widgets.*;

import javax.swing.*;
import java.awt.*;

public class AppLayoutManager extends JFrame {

    private JFrame containerOfWordPickerWidget = null;

    private final Dimension APP_PREFERRED_SIZE = new Dimension(1000,1000);

    public static final Color BACKGROUND_APPLICATION = new Color(20,20,20);

    public AppLayoutManager() {
        super();
        setTitle("SketchEmAll");
    }

    public void presentStartLayout(BadgesController badgesController,
                                    CanvasController canvasController,
                                    TimerController timerController,
                                    WordsInputController wordsInputController){

        this.setLayout(new BorderLayout());
        this.setPreferredSize(APP_PREFERRED_SIZE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_APPLICATION);

        centerPanel.add(Box.createVerticalGlue());

        centerPanel.add(canvasController);

        centerPanel.add(Box.createVerticalGlue());

        JPanel timerAndInputWordPanel = new JPanel();
        BoxLayout layoutForTimerAndInput = new BoxLayout(timerAndInputWordPanel, BoxLayout.X_AXIS);
        timerAndInputWordPanel.setLayout(layoutForTimerAndInput);
        timerAndInputWordPanel.setBackground(BACKGROUND_APPLICATION);
        timerAndInputWordPanel.setMaximumSize(new Dimension(600, 200));
        timerAndInputWordPanel.add(timerController);
        timerAndInputWordPanel.add(Box.createHorizontalGlue());
        timerAndInputWordPanel.add(wordsInputController);
        centerPanel.add(timerAndInputWordPanel);

        centerPanel.add(Box.createVerticalGlue());

        centerPanel.add(badgesController);

        centerPanel.add(Box.createVerticalGlue());


        this.add(centerPanel, BorderLayout.CENTER);

        //mandatory
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }



    public void instantiateWordPickerWidget(WordPickerController wordPickerController){

        containerOfWordPickerWidget = new JFrame();
        containerOfWordPickerWidget.setTitle("Your mission!");
        containerOfWordPickerWidget.setAlwaysOnTop(true);
        containerOfWordPickerWidget.setResizable(false);
        containerOfWordPickerWidget.setUndecorated(true);
        containerOfWordPickerWidget.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        containerOfWordPickerWidget.add(wordPickerController);

        containerOfWordPickerWidget.pack();
        containerOfWordPickerWidget.setLocationRelativeTo(this);
        containerOfWordPickerWidget.setVisible(true);
    }

    public void removeWordPickerWidget(){

        if(containerOfWordPickerWidget !=null){

            containerOfWordPickerWidget.setVisible(false);
            containerOfWordPickerWidget.dispose();
            containerOfWordPickerWidget = null;
        }
        this.setState(JFrame.NORMAL);
        this.setVisible(true);
        this.toFront();
    }


}
