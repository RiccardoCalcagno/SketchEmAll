package ManagersAndServices;

import Animators.BadgeAttestationAnimator;
import InternModels.TurnEndReason;
import Widgets.*;

import javax.swing.*;

public class SessionManager{

    private TurnsManager turnsManager;
    private BadgeAttestationAnimator badgeAttestationAnimator;
    public AppLayoutManager appLayoutManager;

    public TimeManager timeManager;
    public LoopTaskService loopTaskService;
    public RepositoryService repositoryService;


    // -------------------------  widget of Session ------------------------
    private BadgesController badgesController;
    public CanvasController canvasController;
    private TimerController timerController;
    private WordsInputController wordsInputController;


    public SessionManager() {

        // ----------------- Services -------------------
        loopTaskService = new LoopTaskService();
        repositoryService = new RepositoryService();

        // ----------------- Managers -------------------
        timeManager = new TimeManager(this);
        turnsManager = new TurnsManager(this);
        appLayoutManager = new AppLayoutManager();

        // ----------------- Widgets -------------------
        badgesController = new BadgesController();
        canvasController = new CanvasController(turnsManager);
        timerController = new TimerController(timeManager);
        wordsInputController = new WordsInputController(turnsManager);

        badgeAttestationAnimator
                = new BadgeAttestationAnimator(badgesController, timerController, loopTaskService);

        timeManager.addPlayableTimeResponsiveController(canvasController);
        timeManager.addPlayableTimeResponsiveController(timerController);
        timeManager.addPlayableTimeResponsiveController(wordsInputController);

    }

    public void startSession(){
        loopTaskService.startLoop();

        appLayoutManager.presentStartLayout(badgesController, canvasController, timerController, wordsInputController);

        timeManager.startSessionTimer();
        turnsManager.startTurn();
    }

    private void endSession(){

        loopTaskService.stopLoop();
    }



    public void handleGenericEndOfTurn(TurnEndReason turnEndReason){

        if(turnEndReason == TurnEndReason.WORD_GUESSED){
            handleSuccessOfTurn();
        }
        else{
            handleFailOfTurn(turnEndReason);
        }
    }

    private void handleSuccessOfTurn(){

        ImageIcon imageOfWinningDrawing = canvasController.takeScreenshotOfDrawing();

        timeManager.stopSessionTime();

        canvasController.reset();
        wordsInputController.reset();

        //badgeAttestationAnimator.PerformAnimation();

        badgesController.createNewBadge(imageOfWinningDrawing);

        turnsManager.startTurn();
    }

    private void handleFailOfTurn(TurnEndReason turnEndReason){

        timeManager.stopSessionTime();

        canvasController.reset();
        wordsInputController.reset();

        turnsManager.startTurn();
    }
}
