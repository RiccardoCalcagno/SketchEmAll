package Widgets;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SketchEmAllWidget extends JComponent {

    protected Container placeToPutWidget;

    public void instantiateWidget(Container placeToPutWidget){
        this.placeToPutWidget = placeToPutWidget;
    }

    void reset(){

    }
}
