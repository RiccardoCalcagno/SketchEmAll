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

    public SketchEmAllWidget(){
        setBackground(AppLayoutManager.BACKGROUND_APPLICATION);
    }

    /**
     * Resets the content of the component
     */
    public void reset(){

    }
}
