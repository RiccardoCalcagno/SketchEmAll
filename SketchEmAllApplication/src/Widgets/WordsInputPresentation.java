package Widgets;

import ManagersAndServices.AppLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Presentation class of Word Input
 @see WordsInputController
 */
public class WordsInputPresentation {

    private static final Dimension PREFERRED_SIZE_WORD_INPUT = new Dimension(250, 90);


    private JTextField wordInputField;
    private JButton submitButton;

    public void installUI(JComponent component) {

        var controller = (WordsInputController)component;

        installUIStructure(controller);

        wordInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    controller.onSubmitRequest();
                }
            }
        });

        submitButton.addActionListener(e -> controller.onSubmitRequest());
    }

    private void installUIStructure(WordsInputController controller){
        JPanel labelsPanel = new JPanel();
        labelsPanel.setBackground(AppLayoutManager.BACKGROUND_APPLICATION);
        JPanel wordsPanel = new JPanel();
        wordsPanel.setBackground(AppLayoutManager.BACKGROUND_APPLICATION);
        controller.setLayout(new BoxLayout(controller, BoxLayout.Y_AXIS));
        controller.add(Box.createVerticalGlue());
        controller.add(labelsPanel);
        controller.add(Box.createVerticalGlue());
        controller.add(wordsPanel);

        JLabel wordLabel = new JLabel("Guess word:");
        wordLabel.setForeground(Color.white);
        wordLabel.setVerticalAlignment(JLabel.BOTTOM);

        labelsPanel.add(wordLabel);

        this.wordInputField = new JTextField(10);

        wordsPanel.add(this.wordInputField);

        this.submitButton = new JButton("Submit");
        this.submitButton.setBackground(Color.white);
        submitButton.addActionListener(e -> controller.onSubmitRequest());
        wordsPanel.add(submitButton);
    }


    public void clearInputField() {
        wordInputField.setText("");
    }


    public void setIsEnabledInteractions(boolean isEnabled) {
        submitButton.setEnabled(isEnabled);
        wordInputField.setEnabled(isEnabled);
    }

    public String getCurrText() {
        return this.wordInputField.getText();
    }

    public void paint(Graphics2D pen, JComponent component) {
        WordsInputController wordsInputController = ((WordsInputController) component);
        WordsInputModel model = wordsInputController.getModel();
        pen.setColor(Color.black);
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
