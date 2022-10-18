package ManagersAndServices;

import Widgets.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Manager handling the layout of the whole application
 */
public class AppLayoutManager extends JFrame {

    private JFrame containerOfWordPickerWidget = null;

    private final Dimension APP_PREFERRED_SIZE = new Dimension(1000, 1000);

    public static final Color BACKGROUND_APPLICATION = new Color(20, 20, 20);

    public AppLayoutManager() {
        super();
        setTitle("SketchEmAll");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Creates the layout of the application and places
     *
     * @param badgesController     the container with the badges
     * @param canvasController     the canvas for drawing
     * @param timerController      the timer representation
     * @param wordsInputController the word input field and submit button
     *                             Reference to badges {@link BadgesController}
     *                             Reference to canvas {@link CanvasController}
     *                             Reference to timer {@link TimerController
     *                             Reference to word input {@link WordsInputController}
     */

    public void presentStartLayout(BadgesController badgesController,
                                   CanvasController canvasController,
                                   TimerController timerController,
                                   WordsInputController wordsInputController) {

        this.getContentPane().removeAll();
        // BorderLayout is already the default in a JFrame
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


        Border border = BorderFactory.createStrokeBorder(new BasicStroke(5.0f), Color.DARK_GRAY);

        badgesController.setBorder(border);
        JScrollPane scrollPane = setupScrollPane(badgesController);

        centerPanel.add(scrollPane);

        centerPanel.add(Box.createVerticalGlue());


        this.add(centerPanel, BorderLayout.CENTER);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void presentEndLayout(BadgesController badgesController, ActionListener restart) {
        this.getContentPane().removeAll();
        this.setPreferredSize(APP_PREFERRED_SIZE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_APPLICATION);

        Border border = BorderFactory.createStrokeBorder(new BasicStroke(5.0f), Color.DARK_GRAY);

        badgesController.setBorder(border);
        JScrollPane scrollPane = setupScrollPane(badgesController);

        centerPanel.add(Box.createVerticalGlue());
        //TODO
        JLabel thanksLabel = new JLabel("Thank you something");
        thanksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(thanksLabel);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(scrollPane);

        centerPanel.add(Box.createVerticalGlue());

        JLabel restartLabel = new JLabel("If you liked the game and want to play another session, please press the RESTART button!!");
        restartLabel.setForeground(Color.WHITE);
        restartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(restartLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton restartButton = new JButton("RESTART");
        restartButton.addActionListener(restart);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(restartButton);

        centerPanel.add(Box.createVerticalGlue());

        this.add(centerPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JScrollPane setupScrollPane(BadgesController badgesController){
        JScrollPane scrollPane = new JScrollPane(badgesController,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(BACKGROUND_APPLICATION);
        scrollPane.getViewport().setBackground(BACKGROUND_APPLICATION);
        scrollPane.getVerticalScrollBar().setBackground(BACKGROUND_APPLICATION);
        scrollPane.getHorizontalScrollBar().setBackground(BACKGROUND_APPLICATION);
        return scrollPane;
    }


    /**
     * Calls an additional window when a new turn is started or ended
     *
     * @param wordPickerController representation of a card with new word and mode
     */
    public void instantiateWordPickerWidget(WordPickerController wordPickerController) {

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

    /**
     * Removes the additional window when a new turn is ready to start
     */
    public void removeWordPickerWidget() {

        if (containerOfWordPickerWidget != null) {

            containerOfWordPickerWidget.setVisible(false);
            containerOfWordPickerWidget.dispose();
            containerOfWordPickerWidget = null;
        }
        this.setState(JFrame.NORMAL);
        this.setVisible(true);
        this.toFront();
    }


}
