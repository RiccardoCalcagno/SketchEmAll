package Widgets;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class WordPickerPresentation extends ComponentUI {

    private static final Color BACKGROUND_COLOR = new Color(50,50,50);
    @Override
    public void installUI(JComponent component) {
        super.installUI(component);
        installListeners((WordPickerController) component);
    }

    protected void installListeners(WordPickerController wordPickerController)
    {

    }

    public void paint(Graphics2D pen, JComponent component)
    {
        WordPickerController wordPickerController = ((WordPickerController)component);
        WordPickerModel model = wordPickerController.getModel();

        if(model.getIsFlipped()){
            paintFrontSideCard(pen, wordPickerController);
        }
        else{
            paintBackSideCard(pen, wordPickerController);
        }
    }

    private void paintFrontSideCard(Graphics2D pen, WordPickerController wordPickerController) {

    }

    private void paintBackSideCard(Graphics2D pen, WordPickerController wordPickerController) {

    }

    public Color getBackgroundColor(){
        return BACKGROUND_COLOR;
    }

    public Dimension getPreferredSize(){
        return new Dimension(600, 450);
    }
    public Dimension getMinimumSize(){
        return getPreferredSize();
    }
    public Dimension getMaximumSize(){
        return getPreferredSize();
    }
}