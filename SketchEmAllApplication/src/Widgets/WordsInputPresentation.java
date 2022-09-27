package Widgets;

import javax.swing.*;
import java.awt.*;

public class WordsInputPresentation {

    public WordsInputPresentation(WordsInputController controller){

        //not showing on screen...
        JPanel labelsPanel = new JPanel();
        //labelsPanel.setPreferredSize(new Dimension(200,100));
        controller.setLayout(new BorderLayout());

        controller.add(labelsPanel, BorderLayout.NORTH);
        labelsPanel.setBackground(Color.RED);
        JLabel wordLabel = new JLabel("Guess word:");
        wordLabel.setForeground(Color.white);
        labelsPanel.add(wordLabel);

        JTextField wordInputField = new JTextField(10);
        labelsPanel.add(wordInputField);
        installListeners(controller);

    }

    private static final Dimension PREFERRED_SIZE_WORD_INPUT = new Dimension(250, 100);


    private static final Color BACKGROUND_COLOR = new Color(50,50,50);



    protected void installListeners(WordsInputController controller) {

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
