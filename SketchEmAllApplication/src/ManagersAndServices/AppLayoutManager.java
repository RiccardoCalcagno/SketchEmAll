package ManagersAndServices;

import Widgets.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;

public class AppLayoutManager extends JFrame {

    private JFrame containerOfWordPickerWdget = null;
    private Dimension APP_PREFFERED_SIZE = new Dimension(1000,1000);

    public static final Color BACKGROUND_APPLICATION = new Color(20,20,20);

    public AppLayoutManager() {
        super();
    }

    public void presentStartLayout(BadgesController badgesController,
                                    CanvasController canvasController,
                                    TimerController timerController,
                                    WordsInputController wordsInputController){

        this.setLayout(new BorderLayout());
        this.setPreferredSize(APP_PREFFERED_SIZE);

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
        /*
        JPanel placeToPutCanvas = new JPanel();
        centerPanel.add(placeToPutCanvas);
        placeToPutCanvas.add(canvasController);

        //placeholder for now
        placeToPutCanvas.setPreferredSize(new Dimension(850,600));

        JPanel timerAndInputWordPanel = new JPanel();
        centerPanel.add(timerAndInputWordPanel);

        //the problem is from the LAYOUT
        timerAndInputWordPanel.setLayout(new BoxLayout(timerAndInputWordPanel, BoxLayout.LINE_AXIS));
        //timerAndInputWordPanel.add(timerController);
        Box b = Box.createVerticalBox();
        b.add(wordsInputController);
        b.add(Box.createVerticalGlue());
        timerAndInputWordPanel.add(b);

        JPanel placeToPutBadges = new JPanel();
        centerPanel.add(placeToPutBadges, BorderLayout.SOUTH);
        badgesController.instantiateWidget(placeToPutBadges);
         */


        this.add(centerPanel, BorderLayout.CENTER);

        //mandatory
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }



    public void instantiateWordPickerWidget(WordPickerController wordPickerController){

        containerOfWordPickerWdget = new JFrame();
        containerOfWordPickerWdget.setAlwaysOnTop(true);
        containerOfWordPickerWdget.setResizable(false);
        containerOfWordPickerWdget.setUndecorated(true);
        containerOfWordPickerWdget.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        containerOfWordPickerWdget.add(wordPickerController);

        containerOfWordPickerWdget.pack();
        containerOfWordPickerWdget.setLocationRelativeTo(this);
        containerOfWordPickerWdget.setVisible(true);
    }

    public void removeWordPickerWidget(){

        if(containerOfWordPickerWdget !=null){

            containerOfWordPickerWdget.setVisible(false);
            containerOfWordPickerWdget.dispose();
            containerOfWordPickerWdget = null;
        }

    }


}
