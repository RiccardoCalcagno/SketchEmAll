package Widgets;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class CanvasPresentation extends ComponentUI {

    public void installUI(CanvasController controller){

    }

    public void paint(Graphics2D pen, CanvasController canvasController) {
        CanvasModel model = canvasController.getModel();
        pen.setColor((Color.red));

        pen.setColor(Color.black);

    }
}
