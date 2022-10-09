package ManagersAndServices;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeManager {
    private LocalDateTime timeOfSessionStart;

    private int centisecondsOfSession;
    private LocalTime localTimeOfSession;
    public LocalTime getSessionTime(){
        return localTimeOfSession;
    }

    private Timer executorTimer;
    private ActionListener timerListener;

    public boolean isSessionRunningOverThisLevel(ChangePlayingTimeRequestLevel levelToCheck){
        return currentLevelOfStoppedTime.compareTo(levelToCheck) < 0;
    }

    private ChangePlayingTimeRequestLevel currentLevelOfStoppedTime;
    public ChangePlayingTimeRequestLevel getCurrentLevelOfStoppedTime() {
        return currentLevelOfStoppedTime;
    }



    private List<ObserverOfApplicationActivityStates> playableTimeResponsiveControllers = new ArrayList<ObserverOfApplicationActivityStates>();


    public void initializeSessionTimer(){

        currentLevelOfStoppedTime = ChangePlayingTimeRequestLevel.TURN_OVER;

        centisecondsOfSession = 0;

        timerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTimerTic();
            }
        };

        updateLocalTimeOfSession();

        executorTimer = new Timer(0, timerListener);
        executorTimer.setDelay(10);
        executorTimer.setRepeats(true);
        executorTimer.start();
    }

    public void addPlayableTimeResponsiveController(ObserverOfApplicationActivityStates controllerToManage){
        playableTimeResponsiveControllers.add(controllerToManage);
    }

    public void stopTime_levelledRequest(ChangePlayingTimeRequestLevel levelOfRequest){

        executorTimer.stop();

        if(levelOfRequest.compareTo(currentLevelOfStoppedTime) > 0){

            currentLevelOfStoppedTime = levelOfRequest;

            for (ObserverOfApplicationActivityStates controllerToStop: playableTimeResponsiveControllers) {
                controllerToStop.onChangeActivityStateLevel(currentLevelOfStoppedTime);
            }
        }
    }

    public void resumeTime_levelledRequest(ChangePlayingTimeRequestLevel levelOfRequest){

        if(levelOfRequest.compareTo(currentLevelOfStoppedTime) >= 0 && currentLevelOfStoppedTime != ChangePlayingTimeRequestLevel.NONE){
            currentLevelOfStoppedTime = ChangePlayingTimeRequestLevel.NONE;

            executorTimer.restart();

            for (ObserverOfApplicationActivityStates controllerToRestart: playableTimeResponsiveControllers) {
                controllerToRestart.onChangeActivityStateLevel(currentLevelOfStoppedTime);
            }
        }
    }


    private void handleTimerTic() {

        centisecondsOfSession++;

        if(centisecondsOfSession % 100 == 0){
            updateLocalTimeOfSession();
        }
    }

    public double getPercentageOfSession(){
        return (double)centisecondsOfSession / ((double)TurnsManager.DURATION_OF_SESSION_IN_SECONDS * 100.0d);
    }

    private void updateLocalTimeOfSession(){
        int totalRemainingSeconds = TurnsManager.DURATION_OF_SESSION_IN_SECONDS - centisecondsOfSession / 100;
        int hours = totalRemainingSeconds / 3600;
        int minutes = (totalRemainingSeconds / 60) % 60;
        int seconds = totalRemainingSeconds % 60;

        localTimeOfSession = LocalTime.of(hours, minutes, seconds);
    }
}
