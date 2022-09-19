package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;

import javax.swing.*;
import java.awt.*;

public class CanvasController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {

    public CanvasController(Container containerParentOfWidget) {
        super(containerParentOfWidget);
    }

    @Override
    public void onPlayableTimeStop() {

    }

    @Override
    public void onPlayableTimeRestart() {

    }


    @Override
    public void showAndStartIdempotent() {

    }


    public void reset() {

    }


    public ImageIcon takeScreenshotOfDrawing(){

        // TODO
        return null;
    }
}
