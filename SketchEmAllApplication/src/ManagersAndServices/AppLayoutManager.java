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

        JPanel placeToPutCanvas = new JPanel();
        this.add(placeToPutCanvas, BorderLayout.NORTH);
        badgesController.instantiateWidget(placeToPutCanvas);

        JPanel placeToPutBadges = new JPanel();
        this.add(placeToPutCanvas, BorderLayout.SOUTH);
        badgesController.instantiateWidget(placeToPutBadges);
    }

    public void instantiateWordPickerWidget(WordPickerController wordPickerController){

    }

}
