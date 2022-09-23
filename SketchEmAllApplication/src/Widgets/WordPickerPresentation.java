package Widgets;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class WordPickerPresentation extends ComponentUI {

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


    public Dimension getPreferredSize(){
        return new Dimension(400, 300);
    }
    public Dimension getMinimumSize(){
        return getPreferredSize();
    }
    public Dimension getMaximumSize(){
        return getPreferredSize();
    }
}
