package ManagersAndServices;

import Animators.Animator;
import Animators.BadgeAttestationAnimator;
import InternModels.TurnFailReason;
import Widgets.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class SessionManager{

    private TurnsManager turnsManager;
    public TimeManager timeManager;
    private WordsManager wordsManager;
    private LoopTaskService loopTaskService;
    private BadgeAttestationAnimator badgeAttestationAnimator;
    private AppLayoutManager appLayoutManager;


    // -------------------------  widget of Session ------------------------
    private BadgesController badgesController;
    private CanvasController canvasController;
    private TimerController timerController;
    private WordsInputController wordsInputController;


    public SessionManager() {

        loopTaskService = new LoopTaskService();
        wordsManager = new WordsManager();
        timeManager = new TimeManager(loopTaskService);
        turnsManager = new TurnsManager(this);
        appLayoutManager = new AppLayoutManager();

        badgesController = new BadgesController();
        canvasController = new CanvasController();
        timerController = new TimerController(timeManager);
        wordsInputController = new WordsInputController(wordsManager);

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



    private void handleGenericEndOfTurn(){

        timeManager.stopSessionTime();

        canvasController.reset();
        wordsInputController.reset();
    }

    public void handleSuccessOfTurn(){

        ImageIcon imageOfWinningDrawing = canvasController.takeScreenshotOfDrawing();

        handleGenericEndOfTurn();

        //badgeAttestationAnimator.PerformAnimation();

        badgesController.createNewBadge(imageOfWinningDrawing);

        turnsManager.startTurn();
    }

    public void handleFailOfTurn(TurnFailReason turnFailReason){

        handleGenericEndOfTurn();

        turnsManager.startTurn();
    }
}
