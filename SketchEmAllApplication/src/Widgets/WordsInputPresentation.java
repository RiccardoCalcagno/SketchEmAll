package Widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordsInputPresentation {

    private JTextField wordInputField;
    private JButton submitButton;

    public WordsInputPresentation(WordsInputController controller){

        //not showing on screen...
        JPanel labelsPanel = new JPanel();
        JPanel wordsPanel = new JPanel();
        //labelsPanel.setPreferredSize(new Dimension(200,100));
        controller.setLayout(new BoxLayout(controller, BoxLayout.Y_AXIS));
        controller.add(Box.createVerticalGlue());
        controller.add(labelsPanel);
        controller.add(Box.createVerticalGlue());
        controller.add(wordsPanel);

        labelsPanel.setBackground(Color.BLACK);
        JLabel wordLabel = new JLabel("Guess word:");
        wordLabel.setForeground(Color.white);
        wordLabel.setVerticalAlignment(JLabel.BOTTOM);

        labelsPanel.add(wordLabel);

        this.wordInputField = new JTextField(10);

        wordsPanel.add(this.wordInputField);

        this.submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onSubmitRequest();
            }
        });
        wordsPanel.add(submitButton);
        installListeners(controller);
    }

    private static final Dimension PREFERRED_SIZE_WORD_INPUT = new Dimension(250, 90);


    private static final Color BACKGROUND_COLOR = new Color(50,50,50);



    protected void installListeners(WordsInputController controller) {

    }

    public void clearInputField(){
        wordInputField.setText("");
    }

    public void setIsEnabledInteractions(boolean isEnabled){
        submitButton.setEnabled(isEnabled);
        wordInputField.setEnabled(isEnabled);
    }

    public String getCurrText(){
        return this.wordInputField.getText();
    }

    public void paint(Graphics2D pen, JComponent component)
    {
        WordsInputController wordsInputController = ((WordsInputController)component);
        WordsInputModel model = wordsInputController.getModel();
        pen.setColor(Color.black);
        //pen.fillRect(0,0, getPreferredSize().width,getPreferredSize().height);
        pen.setColor(Color.red);



    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_WORD_INPUT;
    }
    public Dimension getMaximumSize() {
        return PREFERRED_SIZE_WORD_INPUT;
    }
    public Dimension getMinimumSize() {
        return PREFERRED_SIZE_WORD_INPUT;
    }
}
