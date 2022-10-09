package InternModels;

import Widgets.TimerPresentation;

import java.awt.*;
import java.awt.geom.Arc2D;

public class TimerSlice {

    public final PaintMode paintMode;

    private Arc2D sliceShape;
    public Shape getSliceShape(){
        return sliceShape;
    }


    public TimerSlice(PaintMode paintMode, TimerPresentation timerPresentation, double initialTimePercentage){
        this.paintMode = paintMode;
        this.timerPresentation = timerPresentation;
        this.startingPercentageTime = initialTimePercentage;
        this.endingPercentageTime = initialTimePercentage;

        updateShape();
    }

    public double getStartingPercentageTime() {
        return startingPercentageTime;
    }
    public double getEndingPercentageTime() {
        return endingPercentageTime;
    }
    public void setEndingPercentageTime(double endingPercentageTime) {
        this.endingPercentageTime = endingPercentageTime;

        updateShape();
    }
    private double startingPercentageTime = 0;
    private double endingPercentageTime = 0;


    public boolean isGrowing = true;
    public boolean isWonMode = false;

    private TimerPresentation timerPresentation;

    public void paint(Graphics2D pen, TimerPresentation timerPresentation){

        if(isGrowing == false && isWonMode == false){
            pen.setColor(Color.lightGray);
        }
        else{
            pen.setColor(paintMode.timerRepresentativeColor);
        }

        pen.fill(sliceShape);
    }


    private void updateShape(){
        var centerOfSliceComposition = timerPresentation.getCenterOfSlicedTimer();
        var maxSizeForSlice = timerPresentation.getRadiusOfSlicedTimer();

        var sizeOfThisSlice = maxSizeForSlice * 2.0d * (paintMode.weightedTimeInPercentage + 1.0d) / 2.0d;

        sliceShape = new Arc2D.Double(
                centerOfSliceComposition.x - (sizeOfThisSlice / 2.0d),
                centerOfSliceComposition.y - (sizeOfThisSlice / 2.0d),
                sizeOfThisSlice,
                sizeOfThisSlice,
                360.0d * (1-startingPercentageTime) + 90.0d,
                360.0d * (startingPercentageTime - endingPercentageTime),
                Arc2D.PIE
        );
    }
}
