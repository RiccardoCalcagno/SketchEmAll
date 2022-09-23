package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;
import ManagersAndServices.TurnsManager;
import PaintingTools.AbstractDrawing;
import PaintingTools.AbstractTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CanvasController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {

    private CanvasModel canvasModel;
    private CanvasPresentation canvasPresentation;
    private JLabel title;

    private TurnsManager turnsManager;
    private AbstractTool lastToolUsed;

    public CanvasController(TurnsManager turnsManager) {
            this.turnsManager = turnsManager;

            Init(new CanvasModel());
        }

    private void Init(CanvasModel canvasModel){

        this.canvasModel = canvasModel;

        this.canvasPresentation = new CanvasPresentation();

        canvasPresentation.installUI(this);

        canvasModel.addChangeListener(
                e -> repaint()
        );

        title = new JLabel("Canvas");
    }


    public void reset() {
        if(this.lastToolUsed != null){
            this.removeMouseListener(this.lastToolUsed);
            this.removeMouseMotionListener(this.lastToolUsed);
            this.removeKeyListener(this.lastToolUsed);
        }

        this.lastToolUsed = this.turnsManager.getModeUsedInTheTurn().paintTool;

        this.addMouseListener(this.lastToolUsed);
        this.addMouseMotionListener(this.lastToolUsed);
        this.addKeyListener(this.lastToolUsed);
    }


    public void editCurrentDrawing(AbstractTool toolToUse){
        if(canvasModel.isDrawing() == false){
            addNewDrawing(toolToUse);
        }

        toolToUse.applyCurrentTransformationOnSubject(canvasModel.getCurrentDrawing());
    }

    public void addNewDrawing(AbstractTool toolToUse) {
        closeCurrentDrawing();

        AbstractDrawing newDrawing = toolToUse.getNewDrawing();
        canvasModel.addNewDrawing(newDrawing);
        canvasModel.chooseDrawingToEdit(newDrawing);
    }

    public void closeCurrentDrawing(){
        if(canvasModel.isDrawing()){
            canvasModel.closeEditOfDrawing();
        }
    }




    public CanvasModel getModel() { return canvasModel;}


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


    @Override
    public Dimension getPreferredSize() {
        return canvasPresentation.getPreferredSize();
    }
    @Override
    public Dimension getMaximumSize() {
        return canvasPresentation.getMaximumSize();
    }
    @Override
    public Dimension getMinimumSize() {
        return canvasPresentation.getMinimumSize();
    }



    public ImageIcon takeScreenshotOfDrawing(){

        // TODO
        return null;
    }
}
