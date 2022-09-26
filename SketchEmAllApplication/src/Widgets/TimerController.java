package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;
import ManagersAndServices.TimeManager;

import java.awt.*;

public class TimerController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {

    private TimeManager timeManager;
    private TimerModel timerModel;
    private TimerPresentation timerPresentation;

    @Override
    public boolean isActive(){
        return timerModel.isActive();
    }

    public TimerController(TimeManager timeManager){

        this.timeManager = timeManager;

        timerModel = new TimerModel();
        timerPresentation = new TimerPresentation();
        Init(timerModel);
    }


    private void Init(TimerModel model){

        this.timerModel = model;

        this.timerPresentation = new TimerPresentation();


        timerModel.addChangeListener(
                e -> onModelChange()
        );
        repaint();

    }



    public void onModelChange(){

        if(timerModel.isActive()){

        }
        else{

        }
        repaint();
    }

    @Override
    public void onPlayableTimeStop() {

    }

    @Override
    public void onPlayableTimeRestart() {

    }


    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }

    public void reset() {

    }

    public Shape getShapeOfLastSlice(){

        // TODO
        return null;
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if(timerPresentation!= null){
            timerPresentation.paint((Graphics2D)pen, this);
        }
    }

    public TimerModel getModel(){
        return timerModel;
    }


    @Override
    public Dimension getPreferredSize() {
        return this.timerPresentation.getPreferredSize();
    }
    @Override
    public Dimension getMaximumSize() {
        return this.timerPresentation.getMaximumSize();
    }
    @Override
    public Dimension getMinimumSize() {
        return this.timerPresentation.getMinimumSize();
    }


}
