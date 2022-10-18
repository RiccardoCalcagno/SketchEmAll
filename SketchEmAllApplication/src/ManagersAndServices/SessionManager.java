package ManagersAndServices;

import Animators.BadgeAttestationAnimator;
import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.TurnEndReason;
import Widgets.*;

import javax.swing.*;

public class SessionManager{

    // -------------------------  Managers and Services ------------------------
    private final TurnsManager turnsManager;
    private BadgeAttestationAnimator badgeAttestationAnimator;
    public AppLayoutManager appLayoutManager;

    public TimeManager timeManager;
    public LoopTaskService loopTaskService;


    // -------------------------  widget of Session ------------------------
    private final BadgesController badgesController;
    public CanvasController canvasController;
    private final TimerController timerController;
    private final WordsInputController wordsInputController;


    public SessionManager() {
        // ----------------- Initialization Services -------------------
        RepositoryService.initializeAsSingleton(this);
        AudioService.initialize();
        loopTaskService = new LoopTaskService();

        // ----------------- Initialization Managers -------------------
        timeManager = new TimeManager();
        turnsManager = new TurnsManager(this);
        appLayoutManager = new AppLayoutManager();

        // ----------------- Initialization Widgets -------------------
        canvasController = new CanvasController(turnsManager);
        timerController = new TimerController(timeManager, loopTaskService);
        wordsInputController = new WordsInputController(turnsManager);
        badgesController = new BadgesController(turnsManager);
        badgeAttestationAnimator
                = new BadgeAttestationAnimator(badgesController, timerController, loopTaskService);

        turnsManager.setTurnWidgets(canvasController, wordsInputController, timerController);

        // Add listeners that needs to be notified when the level of session time change
        timeManager.addPlayableTimeResponsiveController(canvasController);
        timeManager.addPlayableTimeResponsiveController(timerController);
        timeManager.addPlayableTimeResponsiveController(wordsInputController);
    }

    public void startSession(){
        // start of the background loop of the game
        loopTaskService.startLoop();

        appLayoutManager.presentStartLayout(badgesController, canvasController, timerController, wordsInputController);

        // initialize the timeManager and prepare it to be activated (the first time) by the start of the first turn
        timeManager.initializeSessionTimer();

        try {
            turnsManager.startTurn(TurnEndReason.NONE);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void endSession(){
        loopTaskService.stopLoop();
        AudioService.playVictorySound();
        appLayoutManager.presentEndLayout(badgesController, e->startSession());
    }



    public void handleGenericEndOfTurn(TurnEndReason turnEndReason){

        timeManager.stopTime_levelledRequest(ChangePlayingTimeRequestLevel.TURN_OVER);

        if(turnEndReason == TurnEndReason.WORD_GUESSED){
            handleSuccessOfTurn();
        }
        else{
            handleFailOfTurn(turnEndReason);
        }
    }

    private void handleSuccessOfTurn(){

        ImageIcon imageOfWinningDrawing = canvasController.takeScreenshotOfDrawing();

        //badgeAttestationAnimator.PerformAnimation();

        badgesController.createNewBadge(imageOfWinningDrawing, turnsManager.getModeUsedInTheTurn().timerRepresentativeColor,turnsManager.getWordUsedInTheTurn());

        AudioService.playVictorySound();

        try {
            turnsManager.startTurn(TurnEndReason.WORD_GUESSED);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void handleFailOfTurn(TurnEndReason turnEndReason){
        if (turnEndReason == TurnEndReason.TURN_TIMER_EXPIRATION){
            AudioService.playOutOfTimeSound();
        }
        else if (turnEndReason == TurnEndReason.NO_MORE_ATTEMPTS){
            AudioService.playLossSound();
        }

        try {
            turnsManager.startTurn(turnEndReason);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
