package ManagersAndServices;

import InternModels.StoppableAccordinglyToPlayableTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeManager {

    private LoopTaskService loopTaskService;


    private LocalDateTime timeOfSessionStart;

    private int millisecondsOfPause;

    private boolean isSessionRunning;
    public boolean getIsSessionRunning(){
        return isSessionRunning;
    }


    private LoopTaskService.TaskOfSession perpetualManagementOfTime = new LoopTaskService.TaskOfSession() {
        @Override
        public void call() {

            // TODO

        }
    };


    private List<StoppableAccordinglyToPlayableTime> playableTimeResponsiveControllers = new ArrayList<StoppableAccordinglyToPlayableTime>();

    public TimeManager(LoopTaskService loopTaskService){

        this.loopTaskService = loopTaskService;

        loopTaskService.addTaskInLoop(perpetualManagementOfTime);
    }


    public void startSessionTimer(){

        // TODO cancel time before

        resumeSessionTime();
    }

    public void addPlayableTimeResponsiveController(StoppableAccordinglyToPlayableTime controllerToManage){
        playableTimeResponsiveControllers.add(controllerToManage);
    }

    public void stopSessionTime(){
        for (StoppableAccordinglyToPlayableTime controllerToStop: playableTimeResponsiveControllers) {
            controllerToStop.onPlayableTimeStop();
        }
    }
    public void resumeSessionTime(){
        for (StoppableAccordinglyToPlayableTime controllerToRestart: playableTimeResponsiveControllers) {
            controllerToRestart.onPlayableTimeRestart();
        }
    }
}
