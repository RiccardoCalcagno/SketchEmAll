package PaintingTools;

import PaintingDrawings.AbstractDrawing;
import PaintingDrawings.InkBlotDrawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class InkBlotTool extends AbstractTool{

    // from 0.0 to 1.0
    // higher => require more accuracy to put remove the active point
    private static final double ACTIVE_POINT_REDUNDANCY_CHECK_COEFFICIENT = 0.99d;
    private static final double WEIGHT_OF_DISTANCE_RELATIVE_TO_DELTA_ANGLE = 0.6d;
    private static final double THRESHOLD_FOR_ADDING_NEW_POINT = 0.5d;
    private static final double VARIANCE_FOR_DELTA_ANGLE = 0.1d;
    private static final double VARIANCE_FOR_DISTANCE = 2d;
    private static final int BLOT_RESIZING_UPDATE_GAP = 3;
    private static final int RESIZING_PARAMETERS_UPDATE_GAP = 6;

    private final double deltaAngleMeanValue = getGaussianValueOf(0, 0, VARIANCE_FOR_DELTA_ANGLE);
    private final double distanceMeanValue = getGaussianValueOf(0, 0, VARIANCE_FOR_DISTANCE);


    private Point bookedLocationOfNextSpawn;

    private int counterOfPressingEvents = 0;

    private Point previousPositionOfCursor;
    private Point currentPositionOfCursor;
    private Point farAgoPositionOfCursor;
    private double directionOfMouseMovement;

    private int numberOfCycleFormLastActivePointAdded=0;

    private InkBlotDrawing currentInkBlot = null;

    private ArrayList<Double> temporaryWeighsPairedToActivePoints = new ArrayList<Double>();

    @Override
    public void applyCurrentTransformationOnSubject(AbstractDrawing subject) {
        if(currentPositionOfCursor != previousPositionOfCursor){

            var currentInkBlotDrawing = ((InkBlotDrawing)subject);
            var currentVectorOfStretching = new Point2D.Double(
                    currentPositionOfCursor.x - previousPositionOfCursor.x,
                    currentPositionOfCursor.y - previousPositionOfCursor.y
                    );

            var activePointsMovementsVectors = new HashMap<Point2D, Point2D>();

            for (int i=0; i<temporaryWeighsPairedToActivePoints.size(); i++){

                double weigh = temporaryWeighsPairedToActivePoints.get(i);

                if(weigh > 0.04d){
                    activePointsMovementsVectors.put(
                            currentInkBlotDrawing.activePoints.get(i),
                            new Point2D.Double(currentVectorOfStretching.x * weigh,currentVectorOfStretching.y * weigh)
                    );
                }
            }
            currentInkBlotDrawing.moveActivePoints(activePointsMovementsVectors);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(currentInkBlot != null){
            return;
        }

        bookedLocationOfNextSpawn = e.getPoint();

        // requesting the canvas to add a new drawing
        canvasWhereToDraw.addNewDrawing(InkBlotDrawing.create(bookedLocationOfNextSpawn));
    }


    @Override
    public void mousePressed(MouseEvent e) {
        var drawingTargeted = canvasWhereToDraw.getDrawingTargeted(e.getPoint());

        if(drawingTargeted != null && drawingTargeted instanceof InkBlotDrawing){

            currentInkBlot = (InkBlotDrawing) drawingTargeted;

            counterOfPressingEvents = 0;

            previousPositionOfCursor = e.getPoint();
            currentPositionOfCursor = previousPositionOfCursor;
            farAgoPositionOfCursor = previousPositionOfCursor;

            updateResizingParameters();

            canvasWhereToDraw.chooseDrawingToEdit(drawingTargeted);
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        counterOfPressingEvents++;

        if(currentInkBlot != null){
            currentPositionOfCursor = e.getPoint();

            if(counterOfPressingEvents % RESIZING_PARAMETERS_UPDATE_GAP == 0) {
                if (currentInkBlot.contain(currentPositionOfCursor)) {

                    updateResizingParameters();
                }
                farAgoPositionOfCursor = currentPositionOfCursor;
            }

            if(counterOfPressingEvents % BLOT_RESIZING_UPDATE_GAP == 0){

                updateResizing();
                previousPositionOfCursor = currentPositionOfCursor;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        currentInkBlot = null;
    }


    public void updateResizing(){

        // numberOfCycleFormLastActivePointAdded it is the countdown started when a new point of the spline
        // was made active
        if(numberOfCycleFormLastActivePointAdded > 0){
            // this check is needed because: the deletion of useless point should be disabled until the user had
            // the possibility to move its new active point enough to be seen as usefull in the spline.
            // otherwise it will be removed after its creation everytime
            numberOfCycleFormLastActivePointAdded--;
        }
        else{
            // ----------------------------  deleting unusefull activePoints ------------------------------
            deleteUnusefulActivePoints(currentInkBlot);
        }

        // ----------------------------  updating canvas View ----------------------------
        canvasWhereToDraw.editCurrentDrawing(this);
    }


    private void updateDirectionOfMouseMovement(Point2D bestPoint){
        if(farAgoPositionOfCursor != currentPositionOfCursor){
            directionOfMouseMovement = getDirectionInRadiansOfSegment(farAgoPositionOfCursor, currentPositionOfCursor);

            if(minimumAngleBetweenAngle(
                    getDirectionInRadiansOfSegment(currentPositionOfCursor, bestPoint),
                    directionOfMouseMovement)
                    > Math.PI / 2d){
                // I need to consider the opposite angle of the directionOfMovement because it means that the user want to shrink
                // the region near to the currentBestPoint (instead of enlarging the opposite region)

                directionOfMouseMovement += (directionOfMouseMovement <= 0) ? Math.PI : -Math.PI;
            }
        }
        else{
            directionOfMouseMovement = getDirectionInRadiansOfSegment(currentPositionOfCursor, bestPoint);
        }
    }

    private void calculateWeighsOfEachActivePoint(double directionAngle, double minDistance){
        temporaryWeighsPairedToActivePoints.clear();

        for (var point : currentInkBlot.activePoints){
            // Weighted average of the two weights
            temporaryWeighsPairedToActivePoints.add(calculateWeighOfPoint(point, currentPositionOfCursor, directionAngle, minDistance));
        }
    }

    private void addMakeBestPointAnActivePoint(double currentWeighOfBestPoint, Point2D currentBestPoint){
        numberOfCycleFormLastActivePointAdded = 10;

        var indexOfNewActivePoint = currentInkBlot.addActivePoint(currentInkBlot.getIndexOfPoint(currentBestPoint));
        temporaryWeighsPairedToActivePoints.add(indexOfNewActivePoint, currentWeighOfBestPoint);

        currentInkBlot.updateSplineFromActivePoints();
    }

    public void updateResizingParameters(){
        var currentBestPoint = currentInkBlot.getBestPoint(currentPositionOfCursor);

        updateDirectionOfMouseMovement(currentBestPoint);

        if (currentBestPoint.distance(currentPositionOfCursor) > 5) {
            // distance > <small_amount> otherwise the calculation of angles (cursor and line points) and the weight
            // will suffer from imprecision errors

            var directionAngle = getDirectionInRadiansOfSegment(currentPositionOfCursor, currentBestPoint);
            var minDistance = currentPositionOfCursor.distance(currentBestPoint);
            // ------------------------------ calculate weighs ---------------------------------
            calculateWeighsOfEachActivePoint(directionAngle, minDistance);

            // -------------- add a new active point if current active ones are too far -------------
            var currentWeighOfBestPoint = calculateWeighOfPoint(currentBestPoint, currentPositionOfCursor, directionAngle, minDistance);
            if(shouldMakeBestPointActive(currentWeighOfBestPoint)){
                addMakeBestPointAnActivePoint(currentWeighOfBestPoint, currentBestPoint);
            }
        }
    }



    private double calculateWeighOfPoint(Point2D pointToEvaluate, Point cursorPoint, double bestPointDirectionAngle, double minDistance){
        var angleWithPoint = getDirectionInRadiansOfSegment(cursorPoint, pointToEvaluate);

        var deltaAngleWithOptimum = minimumAngleBetweenAngle(directionOfMouseMovement, angleWithPoint);
        var distanceWithAngle = cursorPoint.distance(pointToEvaluate);

        var weightForDeltaAngle = getGaussianValueOf(deltaAngleWithOptimum, 0, VARIANCE_FOR_DELTA_ANGLE) / deltaAngleMeanValue;

        var weightForDistance = getGaussianValueOf(distanceWithAngle, minDistance, VARIANCE_FOR_DISTANCE); // / distanceMeanValue;
        //System.out.println("distanceMeanValue: "+distanceMeanValue+ ", weightForDistance: "+weightForDistance+", fract: "+ distanceMeanValue / weightForDistance);
        weightForDistance /= distanceMeanValue;

        var weigh = (weightForDeltaAngle + weightForDistance * WEIGHT_OF_DISTANCE_RELATIVE_TO_DELTA_ANGLE) / (1 + WEIGHT_OF_DISTANCE_RELATIVE_TO_DELTA_ANGLE);

        // //TODO remove cachedDrawingSubject
        //if(cachedDrawingSubject != null) {
        //    System.out.println(currentInkBlot.activePoints.indexOf(pointToEvaluate)+" weigh: "+weigh);
        //}

        return weigh;
    }


    /**
     * with a while cycle, try to find each time a point to delete and it does it until it can not find point anymore
     */
    private void deleteUnusefulActivePoints(InkBlotDrawing cachedDrawingSubject){
        var activePointToDelete = getNullableOfActivePointToBeRemovedIfRedundant(cachedDrawingSubject);

        while (activePointToDelete != null){
            var indexOfActiveToRemove = cachedDrawingSubject.activePoints.indexOf(activePointToDelete);
            cachedDrawingSubject.removeActivePoint(activePointToDelete);
            temporaryWeighsPairedToActivePoints.remove(indexOfActiveToRemove);

            activePointToDelete = getNullableOfActivePointToBeRemovedIfRedundant(cachedDrawingSubject);
        }

        cachedDrawingSubject.updateSplineFromActivePoints();
    }

    /**
     * With a test performed on the weight of the candidate to become an active point and the current weight of each
     * active point it can understand if the candidate currentWeighOfBestPoint should become an active point or not
     * @param currentWeighOfBestPoint
     * @return
     */
    private boolean shouldMakeBestPointActive(double currentWeighOfBestPoint){

        return ! temporaryWeighsPairedToActivePoints.stream().anyMatch(new Predicate<Double>() {
            @Override
            public boolean test(Double aDouble) {
                return aDouble + THRESHOLD_FOR_ADDING_NEW_POINT >= currentWeighOfBestPoint;
            }
        });
    }

    /**
     * the method execute a test on each active point to understand how much it is usefull in the spline
     * if the point less useful between everyone reach a threshold of "uselessness" that that point will be
     * retreived as the point to be deleted
     * @param cachedDrawingSubject
     *      if not null the Point should be deleted because it is useless
     *      if null than there is no point to delete
     * @return
     */
    public Point2D getNullableOfActivePointToBeRemovedIfRedundant(InkBlotDrawing cachedDrawingSubject){

        var activeLine = cachedDrawingSubject.activePoints;

        if(activeLine.size() <3){
            return null;
        }

        double prevAngle = 0;
        Point2D prevMiddlePoint = null;
        for(int i= 1; i< activeLine.size() -1; i++){
            var currentAngle = getAngleOfMiddleActivePoint(activeLine.get(i-1), activeLine.get(i), activeLine.get(i+1));

            if(currentAngle > prevAngle){
                prevAngle = currentAngle;
                prevMiddlePoint = activeLine.get(i);
            }
        }

        if(prevMiddlePoint != null && prevAngle > Math.PI * ACTIVE_POINT_REDUNDANCY_CHECK_COEFFICIENT){
            return prevMiddlePoint;
        }

        return null;
    }



    // ------------------------------------ Utility methods ---------------------------------------------
    private static double getAngleOfMiddleActivePoint(Point2D firstEdge, Point2D middlePoint, Point2D secondEdge){

        var firstHalfAngle = getDirectionInRadiansOfSegment(firstEdge, middlePoint);
        var secondHalfAngle = getDirectionInRadiansOfSegment(secondEdge,middlePoint);

        return minimumAngleBetweenAngle(firstHalfAngle, secondHalfAngle);
    }
    private static double getGaussianValueOf(double x, double mean, double variance){
        return (1d / Math.sqrt(variance* 2 * Math.PI))*Math.exp(-(((x - mean) * (x - mean)) / ((2 * variance))));
    }
    public static double getDirectionInRadiansOfSegment(Point2D sourcePoint, Point2D targetPoint){
        double disorientedAngle;
        if(Math.abs(targetPoint.getX() - sourcePoint.getX()) < 0.1) {
            if(targetPoint.getY() > sourcePoint.getY()){
                disorientedAngle = Math.PI / 2.0d;
            }
            else{
                disorientedAngle = - Math.PI / 2.0d;
            }
            return disorientedAngle;
        }

        disorientedAngle =  Math.atan((targetPoint.getY() - sourcePoint.getY())/(targetPoint.getX() - sourcePoint.getX()));

        if(targetPoint.getX() < sourcePoint.getX()){

            disorientedAngle -= Math.signum(disorientedAngle) * Math.PI;
        }

        return disorientedAngle;
    }
    public static double minimumAngleBetweenAngle(double source, double target){

        if(Math.signum(source) == Math.signum(target)){
            return Math.abs(source - target);
        }

        var angle = Math.abs(source) + Math.abs(target);

        return (angle > Math.PI) ? Math.PI*2d -angle : angle;
    }





    @Override
    public AbstractDrawing getNewDrawing() {

        var spawnLocation = bookedLocationOfNextSpawn;
        if(spawnLocation == null){
            spawnLocation = new Point((int)(canvasWhereToDraw.getWidth() / 2d)
                    , (int)(canvasWhereToDraw.getHeight() / 2d));
        }

        return InkBlotDrawing.create(spawnLocation);
    }
    @Override
    public PaintingToolsEnum getPaintingToolsEnum() {
        return PaintingToolsEnum.INK_BLOT;
    }
}
