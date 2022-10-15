package ManagersAndServices;

import Widgets.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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





        JPanel toolSettings = new JPanel();
        var l = new BoxLayout(toolSettings, BoxLayout.Y_AXIS);
        toolSettings.setLayout(l);
        toolSettings.setPreferredSize(new Dimension(300, 300));

        var a1 = new JTextField();
        a1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                   canvasController.setValueForDebugProperty(1, Double.valueOf(a1.getText()));
                }
            }
        });
        var a2 = new JTextField();
        a2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    canvasController.setValueForDebugProperty(2, Double.valueOf(a2.getText()));
                }
            }
        });
        var a3 = new JTextField();
        a3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    canvasController.setValueForDebugProperty(3, Double.valueOf(a3.getText()));
                }
            }
        });
        var a4 = new JTextField();
        a4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    canvasController.setValueForDebugProperty(4, Double.valueOf(a4.getText()));
                }
            }
        });
        var a5 = new JTextField();
        a5.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    canvasController.setValueForDebugProperty(5, Double.valueOf(a5.getText()));
                }
            }
        });

        toolSettings.add(a1);
        toolSettings.add(a2);
        toolSettings.add(a3);
        toolSettings.add(a4);
        toolSettings.add(a5);





        //TODO Re-Add
        //timerAndInputWordPanel.add(timerController);
        timerAndInputWordPanel.add(toolSettings);






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
