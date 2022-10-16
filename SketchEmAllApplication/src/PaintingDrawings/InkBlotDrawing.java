package PaintingDrawings;

import InternModels.PointToPointComparator;
import ManagersAndServices.CatmullRomSpline;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class InkBlotDrawing extends AbstractDrawing implements TargetableDrawing {

    private static final int NUMBER_OF_POINTS_BETWEEN_ACTIVE_POINTS = 50;

    private static final int AVERAGE_RADIUS_OF_INITIAL_BLOT = 70;
    private static final Color VERY_LIGHT_GRAY = new Color(240,240,240);

    private static Random myRandomGenerator = new Random();

    @Override
    protected void paintTemplateMethod(Graphics2D pen) {

        var colorMultiplier = this.hashCode() % 2;
        pen.setColor( (colorMultiplier == 0)? Color.BLACK : VERY_LIGHT_GRAY);

        for(var active : activePoints){
            pen.fillOval((int)active.getX() - 2, (int)active.getY() - 2, 4, 4);
        }
        pen.fillPolygon(finalShape);
    }

    private InkBlotDrawing() {
        super();
    }

    public static InkBlotDrawing create(Point initialCenter){

        var blot = new InkBlotDrawing();

        var i=0;
        for(double iAngle=0; iAngle<Math.PI*2d-0.0001d; iAngle+= Math.PI / 4d){
            i++;
            var angle = iAngle + myRandomGenerator.nextDouble() * Math.PI / 8d;
            var distance = AVERAGE_RADIUS_OF_INITIAL_BLOT * (
                        (1 + (myRandomGenerator.nextDouble() / 1.8d - 1/3.8d)) + (0.4d * (i % 2) - 0.2d)
            );

            var nextPoint = new Point2D.Double(
                initialCenter.x + Math.cos(angle) * distance,
                initialCenter.y + Math.sin(angle) * distance
            );

            blot.activePoints.add(nextPoint);
        }

        blot.updateSplineFromActivePoints();
        blot.setIsEmpty(false);

        return blot;
    }


    private Polygon finalShape;

    public List<Point2D> completePolyline = new ArrayList<>();

    public List<Point2D> activePoints = new ArrayList<>();

    //public Point currentVectorOfActivePointSelectedFeedback = new Point(0,0);


    private void updatePolygon(){
        finalShape = new Polygon();

        for (var pointOfLine : completePolyline){
            finalShape.addPoint((int)pointOfLine.getX(), (int)pointOfLine.getY());
        }
    }

    public void updateSplineFromActivePoints(){
        CatmullRomSpline interpolatingSpline = CatmullRomSpline.create(
                activePoints,
                NUMBER_OF_POINTS_BETWEEN_ACTIVE_POINTS,
                0,
                true
        );

        completePolyline = interpolatingSpline.getInterpolatedPoints();

        // Re-defying activePoints based on the new line, useful to understand where to place new ActivePoints
        var nearestPointComparator = new PointToPointComparator();
        for(int i=0; i<activePoints.size(); i++){
            nearestPointComparator.pointToGetNear = activePoints.get(i);
            activePoints.set(i, completePolyline.stream().min(nearestPointComparator).get());
        }

        updatePolygon();

        notifyChange();
    }

    public void moveActivePoints(HashMap<Point2D, Point2D> activePointsToMoveWithVector){
        for (var activePointToMove : activePointsToMoveWithVector.keySet()){
            if(activePoints.contains(activePointToMove)) {
                var movement = activePointsToMoveWithVector.get(activePointToMove);
                activePointToMove.setLocation(
                        activePointToMove.getX() + movement.getX(),
                        activePointToMove.getY() + movement.getY()
                );
            }
        }

        updateSplineFromActivePoints();
    }

    // returns the index of the new ActivePoint
    public int addActivePoint(int indexOfPointInSplineToMakeActive){

        var pointToMakeActive = getPointFromIndex(indexOfPointInSplineToMakeActive);

        if(activePoints.size() == 0 || getIndexOfPoint(activePoints.get(0)) > indexOfPointInSplineToMakeActive){

            activePoints.add(0, pointToMakeActive);
            return 0;
        }

        int i=1;
        for (; i<activePoints.size(); i++){
            var indexOfNextActivePoint = getIndexOfPoint(activePoints.get(i));
            if(indexOfNextActivePoint > indexOfPointInSplineToMakeActive){
                break;
            }
        }

        activePoints.add(i, pointToMakeActive);
        return i;
    }

    public void removeActivePoint(Point2D activePointToRemove){
        activePoints.remove(activePointToRemove);
    }

    public Point2D getBestPoint(Point2D sourcePoint){

        var comparator = new PointToPointComparator();
        comparator.pointToGetNear = sourcePoint;

        return completePolyline.stream().min(comparator).get();
    }


    public int getIndexOfPoint(Point2D pointToSearch){
        return completePolyline.indexOf(pointToSearch);
    }
    public Point2D getPointFromIndex(int index){
        return completePolyline.get(index);
    }

    @Override
    public boolean contain(Point point) {
        return finalShape.contains(point);
    }
}

