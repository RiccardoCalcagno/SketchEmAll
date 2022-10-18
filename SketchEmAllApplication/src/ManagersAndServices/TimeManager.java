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

    private int centisecondsOfSession;
    private LocalTime localTimeOfSession;
    public LocalTime getSessionTime(){
        return localTimeOfSession;
    }

    private Timer executorTimer;

    private ChangePlayingTimeRequestLevel currentLevelOfStoppedTime;


    public boolean isSessionRunningOverThisLevel(ChangePlayingTimeRequestLevel levelToCheck){
        return currentLevelOfStoppedTime.compareTo(levelToCheck) < 0;
    }



    private final List<ObserverOfApplicationActivityStates> playableTimeResponsiveControllers = new ArrayList<ObserverOfApplicationActivityStates>();


    public void initializeSessionTimer(){

        currentLevelOfStoppedTime = ChangePlayingTimeRequestLevel.TURN_OVER;

        centisecondsOfSession = 0;

        ActionListener timerListener = e -> handleTimerTic();

        updateLocalTimeOfSession();

        executorTimer = new Timer(0, timerListener);
        executorTimer.setDelay(10);
        executorTimer.setRepeats(true);
        executorTimer.start();
        executorTimer.stop();
    }

    /**
     * you can add a controller that implements ObserverOfApplicationActivityStates, so that each time that the game
     * will experience a change of it time level it will be notified
     * @param controllerToManage
     */
    public void addPlayableTimeResponsiveController(ObserverOfApplicationActivityStates controllerToManage){
        playableTimeResponsiveControllers.add(controllerToManage);
    }

    /**
     * each extern controller can try to request the break of the time of the play at a certain level.
     * if this level is higher than the one in which the application is currently going then the application
     * must reach this level of inactivity
     * @param levelOfRequest
     */
    public void stopTime_levelledRequest(ChangePlayingTimeRequestLevel levelOfRequest){

        executorTimer.stop();

        if(levelOfRequest.compareTo(currentLevelOfStoppedTime) > 0){

            currentLevelOfStoppedTime = levelOfRequest;

            for (ObserverOfApplicationActivityStates controllerToStop: playableTimeResponsiveControllers) {
                controllerToStop.onChangeActivityStateLevel(currentLevelOfStoppedTime);
            }
        }
    }

    /**
     * each extern controller can try to request the resume of the time of the play at a certain level.
     * if this level is higher than the one in which the application is currently going then the application
     * must decrease the level of inactivity at the level provide as the argument
     * @param levelOfRequest
     */
    public void resumeTime_levelledRequest(ChangePlayingTimeRequestLevel levelOfRequest){

        if(levelOfRequest.compareTo(currentLevelOfStoppedTime) >= 0 && currentLevelOfStoppedTime != ChangePlayingTimeRequestLevel.NONE){
            currentLevelOfStoppedTime = ChangePlayingTimeRequestLevel.NONE;

            executorTimer.restart();

            for (ObserverOfApplicationActivityStates controllerToRestart: playableTimeResponsiveControllers) {
                controllerToRestart.onChangeActivityStateLevel(currentLevelOfStoppedTime);
            }
        }
    }

    /**
     * Timer Loop management
     */
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

        if (totalRemainingSeconds == 0) stopTime_levelledRequest(ChangePlayingTimeRequestLevel.FORCED_INTERRUPTION);

        localTimeOfSession = LocalTime.of(hours, minutes, seconds);
    }
}
