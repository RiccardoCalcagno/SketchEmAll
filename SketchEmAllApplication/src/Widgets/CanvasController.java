package Widgets;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;
import InternModels.PaintMode;
import ManagersAndServices.TurnsManager;
import PaintingDrawings.AbstractDrawing;
import PaintingDrawings.TargetableDrawing;
import PaintingTools.AbstractTool;
import PaintingTools.InkBlotTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Predicate;

public class CanvasController extends SketchEmAllWidget implements ObserverOfApplicationActivityStates {

    private CanvasModel canvasModel;
    private CanvasPresentation canvasPresentation;

    private final TurnsManager turnsManager;
    private AbstractTool lastToolUsed;


    public PaintMode getCurrentPaintMode(){
        return turnsManager.getModeUsedInTheTurn();
    }

    public boolean isActive(){
        return canvasModel.isActive();
    }


    /**
     * given a point it will understand if there is a Drawing beneath it.
     * If more than one it will return only the one on top
     * If there is no one then it will return null
     * @return
     *  drawing targeted by the point
     */
    public AbstractDrawing getDrawingTargeted(Point targetingPoint){
        var optionalDrawings =  canvasModel.getDrawings().stream().filter(new Predicate<AbstractDrawing>() {
            @Override
            public boolean test(AbstractDrawing abstractDrawing) {
                return (abstractDrawing instanceof TargetableDrawing)
                        && ((TargetableDrawing)abstractDrawing).contain(targetingPoint);
            }
        });
        var objectsTargeted = optionalDrawings.toArray();
        if(objectsTargeted.length == 0){
            return null;
        }
        return (AbstractDrawing)(objectsTargeted[objectsTargeted.length -1]);
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
                e -> repaint()
        );
    }


    @Override
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
            addNewDrawing(toolToUse.getNewDrawing());
        }

        toolToUse.applyCurrentTransformationOnSubject(canvasModel.getCurrentDrawing());
    }

    public void addNewDrawing(AbstractDrawing drawingToAdd) {
        if(!isActive()){
            return;
        }

        closeCurrentDrawing();

        canvasModel.addNewDrawing(drawingToAdd);
        canvasModel.chooseDrawingToEdit(drawingToAdd);
    }
    public void chooseDrawingToEdit(AbstractDrawing drawingInEditing){
        if(!isActive()){
            return;
        }

        closeCurrentDrawing();

        canvasModel.chooseDrawingToEdit(drawingInEditing);
    }
    public void removeLastDrawing(){
        if(canvasModel.isDrawing()){
            canvasModel.removeDrawing(canvasModel.getCurrentDrawing());
        }
        else{
            canvasModel.removeLastDrawing();
        }
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


    /**
     * Return an image that represent what is drawn in the Canvas by the user till this moment (without app decorations)
     */
    public ImageIcon takeScreenshotOfDrawing() {

        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);

        canvasPresentation.paint((Graphics2D) image.getGraphics(),this);

        return new ImageIcon(image);
    }
}
