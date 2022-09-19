package ManagersAndServices;


import Animators.BadgeAttestationAnimator;
import InternModels.TurnFailReason;
import Widgets.WordPickerController;

public class TurnsManager {

    private SessionManager sessionManager;
    private TimeManager timeManager;

    // present only in a sub-procedure of the turn
    private WordPickerController wordPickerController;


    public TurnsManager(SessionManager sessionManager){

        this.sessionManager = sessionManager;
        timeManager = sessionManager.timeManager;
    }

    public void startTurn(){

    }


    public void handleFailOfTurn(TurnFailReason turnFailReason){
        sessionManager.handleFailOfTurn(turnFailReason);
    }

    public void handleSuccessOfTurn(){

        sessionManager.handleSuccessOfTurn();
    }
}
