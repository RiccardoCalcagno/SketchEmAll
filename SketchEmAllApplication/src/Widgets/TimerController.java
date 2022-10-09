package Widgets;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;
import InternModels.PaintMode;
import InternModels.TimerSlice;
import ManagersAndServices.LoopTaskService;
import ManagersAndServices.TimeManager;
import ManagersAndServices.TurnsManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimerController extends SketchEmAllWidget implements ObserverOfApplicationActivityStates {

    private LoopTaskService loopTaskService;
    private TimeManager timerManager;
    private TimerModel timerModel;
    private TimerPresentation timerPresentation;
    private BufferedImage timerBuffImage;

    private LoopTaskService.TaskOfSession updateTimerTask = new LoopTaskService.TaskOfSession() {
        @Override
        public void performTask() {
            updateTimerOnSessionTimeIncrement();
        }
    };

    public static final String TIMER_OF_SLICE_EXPIRED_ACTION_NAME = "timerOfSliceExpired";

    public boolean isActive(){
        return timerModel.isActive();
    }


    public TimerController(TimeManager timerManager, LoopTaskService loopTaskService){
        this.timerManager = timerManager;
        this.loopTaskService = loopTaskService;

        timerModel = new TimerModel();

        Init(timerModel);
    }


    private void Init(TimerModel model){
        this.timerModel = model;

        this.loopTaskService.addTaskInLoop(updateTimerTask);

        this.timerPresentation = new TimerPresentation();

        timerModel.addChangeListener(
                e -> repaint()
        );

        this.timerPresentation.installUI(this);

        repaint();
    }


    public void handleOccasionalPauseResumeRequest(boolean isRequestedByUser ){

        var levelOfRequest = (isRequestedByUser)?
                ChangePlayingTimeRequestLevel.USER_REQUEST :
                ChangePlayingTimeRequestLevel.OCCASIONAL_PROGRAM_REQUEST;

        boolean isPauseRequest = timerModel.isGameOccasionallyInterrupted() == false;

        if(isPauseRequest){
            timerManager.stopTime_levelledRequest(levelOfRequest);
        }
        else{
            timerManager.resumeTime_levelledRequest(levelOfRequest);
        }
    }


    @Override
    public void onChangeActivityStateLevel(ChangePlayingTimeRequestLevel levelOfRequest) {

        if(levelOfRequest == ChangePlayingTimeRequestLevel.NONE){
            timerModel.setGameOccasionallyInterrupted(false);
            timerModel.setIsActive(true);
        }
        else{
            if(levelOfRequest.compareTo(ChangePlayingTimeRequestLevel.TURN_OVER) < 0){
                timerModel.setGameOccasionallyInterrupted(true);
                timerModel.setIsActive(true);
            }
            else{
                timerModel.setIsActive(false);
            }
        }
    }


    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }

    public void reset() {

    }


    public TimerSlice getCurrentSlice(){

        var slices = timerModel.getTimerSlices();

        if(slices.size() == 0){
            return null;
        }

        return slices.get(slices.size() -1);
    }


    public void endCurrentSlice(boolean isWon){
        timerModel.stopGrowthOfCurrentSlice(isWon);
    }

    public void createNewSliceForNewTurn(PaintMode paintModeUsedInNewTurn){

        TimerSlice newTimerSlice = new TimerSlice(
                paintModeUsedInNewTurn,
                timerPresentation,
                timerManager.getPercentageOfSession()
                );

        timerModel.addTimerSlice(newTimerSlice);
    }

    public void updateTimerOnSessionTimeIncrement(){
        var currentSlice = getCurrentSlice();

        if(currentSlice != null && currentSlice.isGrowing == true){

            var maxSecondsForCurrentMode = PaintMode.MAXIMUM_SECONDS_OF_HARDEST_MODE * currentSlice.paintMode.weightedTimeInPercentage;

            var secondsSpentInForThisSlice = (currentSlice.getEndingPercentageTime() - currentSlice.getStartingPercentageTime())
                    * TurnsManager.DURATION_OF_SESSION_IN_SECONDS;

            if(secondsSpentInForThisSlice >= maxSecondsForCurrentMode){
                notifyExpirationOfTurn();
            }
            else{
                timerModel.setCurrentSliceEndPercentageTime(timerManager.getPercentageOfSession());
            }
        }
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

    public LocalTime getCurrentSessionTime(){
        return timerManager.getSessionTime();
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


    private List<ActionListener> actionListeners = new ArrayList<>();
    public void addActionListener(ActionListener actionListenerToAdd){
        actionListeners.add(actionListenerToAdd);
    }
    public void removeActionListener(ActionListener actionListenerToRemove){
        actionListeners.remove(actionListenerToRemove);
    }
    public void notifyExpirationOfTurn(){
        ActionEvent actionEventEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TIMER_OF_SLICE_EXPIRED_ACTION_NAME);

        for (var listener: actionListeners){
            listener.actionPerformed(actionEventEvent);
        }
    }

}
