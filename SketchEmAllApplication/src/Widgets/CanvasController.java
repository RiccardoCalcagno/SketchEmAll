package Widgets;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;
import InternModels.PaintMode;
import ManagersAndServices.TurnsManager;
import PaintingTools.AbstractDrawing;
import PaintingTools.AbstractTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CanvasController extends SketchEmAllWidget implements ObserverOfApplicationActivityStates {

    private CanvasModel canvasModel;
    private CanvasPresentation canvasPresentation;
    private JLabel title;

    private final TurnsManager turnsManager;
    private AbstractTool lastToolUsed;

    public int nextNumberOfBadge;

    public PaintMode getCurrentPaintMode(){
        return turnsManager.getModeUsedInTheTurn();
    }

    public boolean isActive(){
        return canvasModel.isActive();
    }


    public CanvasController(TurnsManager turnsManager) {
            this.turnsManager = turnsManager;

            Init(new CanvasModel());
        }

    private void Init(CanvasModel canvasModel){

        this.canvasModel = canvasModel;

        this.canvasPresentation = new CanvasPresentation();

        canvasPresentation.installUI(this);

        canvasModel.addChangeListener(
                e -> onModelChange()
        );

        title = new JLabel("Canvas");

        this.nextNumberOfBadge = 1;
    }


    public void onModelChange(){

        repaint();
    }



    public void reset() {
        if(lastToolUsed != null) {
            this.lastToolUsed.setCanvasWhereToDraw(null);
        }

        this.lastToolUsed = this.turnsManager.getModeUsedInTheTurn().paintTool;

        if(lastToolUsed != null){
            this.lastToolUsed.setCanvasWhereToDraw(this);
        }

        this.canvasModel.removeAllDrawing();
    }


    public void editCurrentDrawing(AbstractTool toolToUse){
        if(!isActive()){
            return;
        }

        if(!canvasModel.isDrawing()){
            addNewDrawing(toolToUse);
        }

        toolToUse.applyCurrentTransformationOnSubject(canvasModel.getCurrentDrawing());
    }

    public void addNewDrawing(AbstractTool toolToUse) {
        if(!isActive()){
            return;
        }

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
    public void onChangeActivityStateLevel(ChangePlayingTimeRequestLevel levelOfRequest) {
        canvasModel.setIsActive(levelOfRequest == ChangePlayingTimeRequestLevel.NONE);
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



    public ImageIcon takeScreenshotOfDrawing() {

        // TODO

        BufferedImage image = null;

        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException e) {
            e.printStackTrace();
        }

        if (image == null){
            System.out.println("image is null");
        }
        nextNumberOfBadge++;
        assert image != null;
        return new ImageIcon(image);
    }
}
