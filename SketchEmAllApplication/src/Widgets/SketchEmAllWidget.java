package Widgets;

import ManagersAndServices.AppLayoutManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class SketchEmAllWidget extends JPanel {

    protected Container placeToPutWidget;


    public SketchEmAllWidget(){
        setBackground(AppLayoutManager.BACKGROUND_APPLICATION);
    }

    public void instantiateWidget(Container placeToPutWidget){
        this.placeToPutWidget = placeToPutWidget;
    }

    void reset(){

    }
}
