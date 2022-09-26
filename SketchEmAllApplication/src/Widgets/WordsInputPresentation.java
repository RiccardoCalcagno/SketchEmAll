package Widgets;

import javax.swing.*;
import java.awt.*;

public class WordsInputPresentation {

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
        pen.fillRect(0,0, 600,450);

    }
    public Dimension getPreferredSize(){
        return new Dimension(600, 450);
    }
}
