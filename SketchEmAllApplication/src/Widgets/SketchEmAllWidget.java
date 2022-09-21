package Widgets;

import javax.swing.*;
import java.awt.*;

public abstract class SketchEmAllWidget extends JComponent {

    protected Container placeToPutWidget;

    public void instantiateWidget(Container placeToPutWidget){
        this.placeToPutWidget = placeToPutWidget;
    }

    void reset(){

    }
}
