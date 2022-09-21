package Widgets;

import java.awt.*;

public abstract class SketchEmAllWidget {

    protected Container placeToPutWidget;

    public void instantiateWidget(Container placeToPutWidget){
        this.placeToPutWidget = placeToPutWidget;
    }

    void reset(){

    }
}
