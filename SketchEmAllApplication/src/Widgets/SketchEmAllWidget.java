package Widgets;

import java.awt.*;

public abstract class SketchEmAllWidget {

    protected Container containerParentOfWidget;
    public SketchEmAllWidget(Container containerParentOfWidget){
        this.containerParentOfWidget = containerParentOfWidget;
    }

    public abstract void showAndStartIdempotent();

    void reset(){

    }
}
