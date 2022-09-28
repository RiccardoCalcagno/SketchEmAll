package ManagersAndServices;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeManager {

    private SessionManager sessionManager;

    private LocalDateTime timeOfSessionStart;

    private int millisecondsOfPause;

    public boolean isSessionRunningOverThisLevel(ChangePlayingTimeRequestLevel levelToCheck){
        return currentLevelOfStoppedTime.compareTo(levelToCheck) < 0;
    }


    private ChangePlayingTimeRequestLevel currentLevelOfStoppedTime;
    public ChangePlayingTimeRequestLevel getCurrentLevelOfStoppedTime() {
        return currentLevelOfStoppedTime;
    }



    private LoopTaskService.TaskOfSession perpetualManagementOfTime = new LoopTaskService.TaskOfSession() {
        @Override
        public void call() {

            // TODO

        }
    };


    private List<ObserverOfApplicationActivityStates> playableTimeResponsiveControllers = new ArrayList<ObserverOfApplicationActivityStates>();

    public TimeManager(SessionManager sessionManager){

        this.sessionManager = sessionManager;
    }


    public void initializeSessionTimer(){

        currentLevelOfStoppedTime = ChangePlayingTimeRequestLevel.NONE;

        sessionManager.loopTaskService.addTaskInLoop(perpetualManagementOfTime);
    }

    public void addPlayableTimeResponsiveController(ObserverOfApplicationActivityStates controllerToManage){
        playableTimeResponsiveControllers.add(controllerToManage);
    }

    public void stopTime_levelledRequest(ChangePlayingTimeRequestLevel levelOfRequest){

        if(levelOfRequest.compareTo(currentLevelOfStoppedTime) > 0){

            currentLevelOfStoppedTime = levelOfRequest;

            for (ObserverOfApplicationActivityStates controllerToStop: playableTimeResponsiveControllers) {
                controllerToStop.onChangeActivityStateLevel(levelOfRequest);
            }
        }
    }

    public void resumeTime_levelledRequest(ChangePlayingTimeRequestLevel levelOfRequest){

        if(levelOfRequest.compareTo(currentLevelOfStoppedTime) >= 0 && currentLevelOfStoppedTime != ChangePlayingTimeRequestLevel.NONE){
            currentLevelOfStoppedTime = ChangePlayingTimeRequestLevel.NONE;
            
            for (ObserverOfApplicationActivityStates controllerToRestart: playableTimeResponsiveControllers) {
                controllerToRestart.onChangeActivityStateLevel(levelOfRequest);
            }
        }
    }
}
