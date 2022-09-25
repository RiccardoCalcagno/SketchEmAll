package PaintingTools;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.Random;

public class CrazyPenTool extends PencilTool{

    // pixel / millisecond
    private static final double MEDIUM_AVERAGE_CURSOR_SPEED = 2d;
    private static final int SPEED_SAMPLING_AMPLITUDE = 5;

    private double cursorSpeed;

    private double[] cursorSpeedBuffer = new double[SPEED_SAMPLING_AMPLITUDE];
    private int currentIndexOfCircularSpeedBuffer;
    private LocalTime lastUpdateTime;
    private Point lastUpdatePointerLocation;
    private double cursorSpreadSpeed;

    private Random myRandom;


    public CrazyPenTool(){
        super();
        restSpeedBuffer();
        myRandom = new Random();
    }


    private boolean resultOfCrazyDice(){
        return myRandom.nextInt((int)(100 *getConstantOfCraziness())) > 30;
    }

    private Point findItsCrazyPosition(Point startPosition){
        double constantOfCraziness = getConstantOfCraziness();
        return new Point(
                (int)(startPosition.x + myRandom.nextInt((int)(40 * constantOfCraziness))  - 20 * constantOfCraziness),
                (int)(startPosition.y + myRandom.nextInt((int)(40 * constantOfCraziness)) - 20 * constantOfCraziness)
        );
    };

    private double getConstantOfCraziness(){

        double realConstant = cursorSpreadSpeed / MEDIUM_AVERAGE_CURSOR_SPEED;

        if(realConstant > 2){
            realConstant = 2;
        }
        if(realConstant < 0.5d){
            realConstant = 0.5;
        }
        return realConstant;
    }

    @Override
    public void addNewAlteredPointFromPointer(Point pointerLocation){

        updateCursorSpeedTracking(pointerLocation);

        if(resultOfCrazyDice()){
            nextPointToAdd = findItsCrazyPosition(pointerLocation);
        }
        else{
            nextPointToAdd = pointerLocation;
        }
    }

    private void restSpeedBuffer(){
        for(int i=0; i<SPEED_SAMPLING_AMPLITUDE; i++){
            cursorSpeedBuffer[i] = MEDIUM_AVERAGE_CURSOR_SPEED;
        }
        currentIndexOfCircularSpeedBuffer = 0;
        lastUpdateTime = null;
        lastUpdatePointerLocation = null;
        cursorSpreadSpeed = MEDIUM_AVERAGE_CURSOR_SPEED;
    }

    private void updateCursorSpeedTracking(Point newLocation){
        LocalTime now = LocalTime.now();

        if(lastUpdateTime != null){

            double milliOfUpdateDelay = (now.toNanoOfDay() - lastUpdateTime.toNanoOfDay()) / 1000000d;
            double distanceBetweenUpdates = lastUpdatePointerLocation.distanceSq(newLocation);
            cursorSpeedBuffer[currentIndexOfCircularSpeedBuffer] = distanceBetweenUpdates / milliOfUpdateDelay;
        }

        lastUpdateTime = now;
        lastUpdatePointerLocation = newLocation;
        currentIndexOfCircularSpeedBuffer = (currentIndexOfCircularSpeedBuffer + 1) % SPEED_SAMPLING_AMPLITUDE;

        double sumOfSpeeds = 0;
        for(int i=0; i<SPEED_SAMPLING_AMPLITUDE; i++){
            sumOfSpeeds += cursorSpeedBuffer[i];
            if(i == currentIndexOfCircularSpeedBuffer){
                sumOfSpeeds += 2 * cursorSpeedBuffer[i];
            }
        }

        // in pixel/milliseconds
        cursorSpreadSpeed = sumOfSpeeds / (SPEED_SAMPLING_AMPLITUDE + 2);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        restSpeedBuffer();
        super.mousePressed(e);
    }

    @Override
    public PaintingToolsEnum getPaintingToolsEnum() {
        return PaintingToolsEnum.CRAZY_PEN;
    }
}
