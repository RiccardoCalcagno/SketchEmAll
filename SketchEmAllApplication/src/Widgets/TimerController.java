package Widgets;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;
import ManagersAndServices.TimeManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TimerController extends SketchEmAllWidget implements ObserverOfApplicationActivityStates {

    private TimeManager timeManager;
    private TimerModel timerModel;
    private TimerPresentation timerPresentation;
    private BufferedImage timerBuffImage;


    public boolean isActive(){
        return timerModel.isActive();
    }


    public TimerController(TimeManager timeManager){

        this.timeManager = timeManager;

        timerModel = new TimerModel();

        Init(timerModel);
    }


    private void Init(TimerModel model){
        this.timerModel = model;

        this.timerPresentation = new TimerPresentation();

        timerModel.addChangeListener(
                e -> onModelChange()
        );

        this.timerPresentation.installUI(this);

        //some UI stuff that probably should be moved?
        //JLabel iconLbl = new JLabel();
        //iconLbl.setIcon(timerModel.getTimerImage());
        //this.setLayout(new BorderLayout());
        //this.add(iconLbl, BorderLayout.CENTER);

        onModelChange();
    }


    public void handleOccasionalPauseResumeRequest(boolean isRequestedByUser ){

    }


    public void onModelChange(){

        repaint();
    }

    @Override
    public void onChangeActivityStateLevel(ChangePlayingTimeRequestLevel levelOfRequest) {

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
