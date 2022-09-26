package Widgets;

import javax.swing.*;
import java.awt.*;

public class WordsInputPresentation {

    private static final Dimension PREFERRED_SIZE_WORD_INPUT = new Dimension(250, 100);


    private static final Color BACKGROUND_COLOR = new Color(50,50,50);

    public void installUI(CanvasController canvas) {
        installListeners(canvas);
    }

    protected void installListeners(CanvasController canvas) {

    }


    public void paint(Graphics2D pen, JComponent component)
    {
        WordsInputController wordsInputController = ((WordsInputController)component);
        WordsInputModel model = wordsInputController.getModel();
        pen.setColor(Color.black);
        pen.fillRect(0,0, getPreferredSize().width,getPreferredSize().height);

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
