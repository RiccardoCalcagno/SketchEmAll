package ManagersAndServices;

import Widgets.*;

import javax.swing.*;
import java.awt.*;

public class AppLayoutManager extends JFrame {

    public AppLayoutManager() {
        super();

    }

    public void presentStartLayout(BadgesController badgesController,
                                    CanvasController canvasController,
                                    TimerController timerController,
                                    WordsInputController wordsInputController){

        this.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();

        JPanel placeToPutCanvas = new JPanel();
        centerPanel.add(placeToPutCanvas, BorderLayout.NORTH);
        placeToPutCanvas.add(canvasController);

        //placeholder for now
        placeToPutCanvas.setPreferredSize(new Dimension(700,600));


        JPanel placeToPutBadges = new JPanel();
        centerPanel.add(placeToPutCanvas, BorderLayout.SOUTH);
        badgesController.instantiateWidget(placeToPutBadges);

        this.add(centerPanel, BorderLayout.CENTER);

        //mandatory
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();
        this.setVisible(true);
    }

    private void setUpCanvas(){

    }

    public void instantiateWordPickerWidget(WordPickerController wordPickerController){

    }

}
