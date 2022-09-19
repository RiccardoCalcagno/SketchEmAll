package ManagersAndServices;

import Animators.Animator;
import Animators.BadgeAttestationAnimator;
import InternModels.TurnFailReason;
import Widgets.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class SessionManager extends JFrame {

    private TurnsManager turnsManager;
    public TimeManager timeManager;
    private WordsManager wordsManager;
    private LoopTaskService loopTaskService;
    private BadgeAttestationAnimator badgeAttestationAnimator;


    // -------------------------  widget of Session ------------------------
    private BadgesController badgesController;
    private CanvasController canvasController;
    private TimerController timerController;
    private WordsInputController wordsInputController;


    public SessionManager(){

        loopTaskService = new LoopTaskService();
        wordsManager = new WordsManager();
        timeManager = new TimeManager(loopTaskService);
        turnsManager = new TurnsManager(this);

        badgesController = new BadgesController((Container) this);
        canvasController = new CanvasController((Container) this);
        timerController = new TimerController((Container) this, timeManager);
        wordsInputController = new WordsInputController((Container)this, wordsManager);

        badgeAttestationAnimator
                = new BadgeAttestationAnimator(badgesController, timerController, loopTaskService);

        timeManager.addPlayableTimeResponsiveController(canvasController);
        timeManager.addPlayableTimeResponsiveController(timerController);
        timeManager.addPlayableTimeResponsiveController(wordsInputController);

        startSession();
    }

    private void startSession(){
        loopTaskService.startLoop();

        badgesController.showAndStartIdempotent();
        canvasController.showAndStartIdempotent();
        timerController.showAndStartIdempotent();
        wordsInputController.showAndStartIdempotent();

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

        badgeAttestationAnimator.PerformAnimation();

        badgesController.createNewBadge(imageOfWinningDrawing);
    }

    public void handleFailOfTurn(TurnFailReason turnFailReason){

        handleGenericEndOfTurn();
    }
}
