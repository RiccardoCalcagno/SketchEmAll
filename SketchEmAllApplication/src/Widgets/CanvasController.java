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



    public void setValueForDebugProperty(int numOfProperty, double value){
        /*
            ACTIVE_POINT_REDUNDANCY_CHECK_COEFFICIENT = 0.85d;
            WEIGHT_OF_DISTANCE_RELATIVE_TO_DELTA_ANGLE = 0.7d;
            THRESHOLD_FOR_ADDING_NEW_POINT = 0.1d;
            VARIANCE_FOR_DELTA_ANGLE = 1.0d;
            VARIANCE_FOR_DISTANCE = 1.0d;
         */
        switch (numOfProperty){
            case 1:
                ((InkBlotTool)lastToolUsed).ACTIVE_POINT_REDUNDANCY_CHECK_COEFFICIENT = value;
                break;
            case 2:
                ((InkBlotTool)lastToolUsed).WEIGHT_OF_DISTANCE_RELATIVE_TO_DELTA_ANGLE = value;
                break;
            case 3:
                ((InkBlotTool)lastToolUsed).THRESHOLD_FOR_ADDING_NEW_POINT = value;
                break;
            case 4:
                ((InkBlotTool)lastToolUsed).VARIANCE_FOR_DELTA_ANGLE = value;
                break;
            case 5:
                ((InkBlotTool)lastToolUsed).VARIANCE_FOR_DISTANCE = value;
                break;
            default:
                break;
        }

    }



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

        title = new JLabel("Canvas");

        this.nextNumberOfBadge = 1;
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

        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);

        canvasPresentation.paint((Graphics2D) image.getGraphics(),this);

        nextNumberOfBadge++;

        return new ImageIcon(image);
    }
}
