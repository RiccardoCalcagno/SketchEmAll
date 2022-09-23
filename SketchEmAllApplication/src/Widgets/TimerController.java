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

}
