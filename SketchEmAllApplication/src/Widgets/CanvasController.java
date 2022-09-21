package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;

import javax.swing.*;
import java.awt.*;

public class CanvasController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {
    CanvasModel canvasModel;
    CanvasPresentation canvasPresentation;
    JLabel title;
    public CanvasController() {

            canvasModel = new CanvasModel();
            canvasPresentation = new CanvasPresentation();
            title = new JLabel("Canvas");
            initUI();
        }

    private void initUI() {
        updateUI();
    }

    @Override
    public void updateUI(){
        setUI((CanvasPresentation)UIManager.getUI(this));
    }

    public CanvasModel getModel() { return canvasModel;
    }
    @Override
    public void onPlayableTimeStop() {

    }

    @Override
    public void onPlayableTimeRestart() {

    }

    @Override
    public void instantiateWidget(Container placeToPutWidget){
        //ToDo add Canvas
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if (canvasPresentation != null){
            canvasPresentation.paint((Graphics2D)pen, this);
        }
    }


    public void reset() {

    }


    public ImageIcon takeScreenshotOfDrawing(){

        // TODO
        return null;
    }
}
