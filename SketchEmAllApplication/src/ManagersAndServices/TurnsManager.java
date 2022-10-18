package ManagersAndServices;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.PaintMode;
import InternModels.TurnEndReason;
import PaintingTools.*;
import Widgets.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;


public class TurnsManager{

    // the user can try with a maximum of NUM_OF_ATTEMPT_EACH_TURN words each turn to guess the draw of the partner
    public static final int NUM_OF_ATTEMPT_EACH_TURN = 3;

    // The collection of each turn must end in seconds
    public static final int DURATION_OF_SESSION_IN_SECONDS = 3;//300;

    // ------------------------------------ Managers references ------------------------------------
    private final SessionManager sessionManager;
    private final TimeManager timeManager;

    // ------------------------------------ widgets references ------------------------------------
    private CanvasController canvasController;
    private WordsInputController wordsInputController;
    private TimerController timerController;
    // present only in a sub-procedure of the turn
    private WordPickerController wordPickerController;


    private Dictionary<PaintingToolsEnum, PaintMode> paintModesKit = new Hashtable<>();

    // public interface access to relevant information (like what is the current mode in use)
    private PaintMode modeUsedInTheTurn = null;
    public PaintMode getModeUsedInTheTurn(){
        return this.modeUsedInTheTurn;
    }
    private String wordUsedInTheTurn = null;
    public String getWordUsedInTheTurn(){
        return this.wordUsedInTheTurn;
    }
    private int numberOfAttemptsLeft;

    // used to perform the cycle of modes ("cyclic" in the presentation, "random" in production)
    private int lastPaintModeIndex = -1;

    // this manager gives to the TimerController a listener to provide an appropriate reaction to the expiration of turn timeout
    private final ActionListener expiredTimeForCurrentTurn = this::notifyEndOfTimer;

    public TurnsManager(SessionManager sessionManager){

        this.sessionManager = sessionManager;
        this.timeManager = sessionManager.timeManager;

        setPaintModesKit();
    }

    /**
     * initial setting of the widgets that turnManager is going to use
     */
    public void setTurnWidgets(CanvasController canvasController, WordsInputController wordsInputController, TimerController timerController){
        this.canvasController = canvasController;
        this.wordsInputController = wordsInputController;
        this.timerController = timerController;
        this.timerController.addActionListener(expiredTimeForCurrentTurn);
    }


    /**
     * for each new turn this method will retrieve to the caller a new paintMode with which it could initialize the turn
     */
    private PaintMode chosePaintModeForNewTurn(){

        var paintModesEnumeration = paintModesKit.elements();

        // random choice
        //int indexOfChosenMode = new Random().nextInt(paintModesKit.size());

        // cycled choice
        lastPaintModeIndex = (lastPaintModeIndex + 1) % paintModesKit.size();
        var counter = lastPaintModeIndex;
        while(counter > 0){
            counter--;
            paintModesEnumeration.nextElement();
        }
        return paintModesEnumeration.nextElement();
    }

    /**
     * start a new turn after the end of other or simpler to start the first turn
     * @param callReason
     *  if there was a past turn => reason of the end of it (useful to present feedback of the progression of the game)
     */
    public void startTurn(TurnEndReason callReason) {

        wordUsedInTheTurn =  RepositoryService.chooseNextWord();
        //changed to normal pen
        modeUsedInTheTurn = chosePaintModeForNewTurn();

        // ----------------- resetting states and data of controllers of the game -------------------
        canvasController.reset();
        wordsInputController.reset();
        numberOfAttemptsLeft = NUM_OF_ATTEMPT_EACH_TURN;

        try {
            invokeWordPickingProcedure(
                    e -> startPlayingInTheTurn(),
                    callReason
            );
        }
        catch (Exception e){
            System.out.println(e.getMessage()+". Probably due to: two procedure of word picking at the same time");
        }
    }

    /**
     * Asynchronous procedure needed to present the word and mode chosen for this turn
     */
    private void invokeWordPickingProcedure(ActionListener endOfProcedureEvent, TurnEndReason callReason) throws Exception{

        if(wordPickerController != null){
            throw new Exception("a new procedure of this kind should be invoked only if there are no other actives");
        }

        wordPickerController = new WordPickerController(
                modeUsedInTheTurn,
                wordUsedInTheTurn,
                callReason
                );

        // Auto-detach listener
        var wrappedEndOfProcedureEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sessionManager.appLayoutManager.removeWordPickerWidget();
                wordPickerController = null;
                endOfProcedureEvent.actionPerformed(e);
            }
        };

        wordPickerController.addEndProcedureListeners(wrappedEndOfProcedureEvent);

        sessionManager.appLayoutManager.instantiateWordPickerWidget(wordPickerController);
    }

    /**
     * At this point the program can really start the new turn because the asynchronous procedure
     * of picking the new word is finished
     */
    private void startPlayingInTheTurn(){

        timerController.createNewSliceForNewTurn(modeUsedInTheTurn);

        // at start or at the end of the previous turn the time of the session was stopped so now we are going to resume it
        this.timeManager.resumeTime_levelledRequest(ChangePlayingTimeRequestLevel.TURN_OVER);
    }


    /**
     * Creating the set of tools (modes) that can be used in the game
     */
    public void setPaintModesKit(){
        paintModesKit = new Hashtable<>();

        // ------------------------ Pencil PaintMode -----------------------
        paintModesKit.put(PaintingToolsEnum.PENCIL,
                new PaintMode(
                "Pencil",
                "Keep pressed and drag the mouse to draw the lines for your amazing painting ;)",
                RepositoryService.loadImageFromResources("pencil_icon.png"),
                Color.orange,
                0.5d,
                new PencilTool()
            )
        );
        paintModesKit.put(PaintingToolsEnum.INVERTED_PEN,
                new PaintMode(
                        "Inverted pen",
                        "Have you ever drawn in a mirror? No? This changes now. Good luck! O:)",
                        RepositoryService.loadImageFromResources("invertedPen.png"),
                        Color.cyan,
                        0.77d,
                        new InvertedPenTool()
                )
        );
        paintModesKit.put(PaintingToolsEnum.CRAZY_PEN,
                new PaintMode(
                        "Crazy Pen",
                        "Keep pressed and drag the mouse to draw the lines, but... pay attention... sometimes it likes to be silly!",
                        RepositoryService.loadImageFromResources("crazy_pen.png"),
                        Color.PINK,
                        0.6d,
                        new CrazyPenTool()
                )
        );
        paintModesKit.put(PaintingToolsEnum.INK_BLOT,
                new PaintMode(
                        "Ink blot",
                        "Try to click in the canvas and a blot of ink will appear! try to stretch it",
                        RepositoryService.loadImageFromResources("inkBlot.png"),
                        Color.yellow,
                        0.95d,
                        new InkBlotTool()
                )
        );
    }


    /**
     * method that handle the submitting of a new attempt (a new word), it manages autonomously the validation
     * , the possible fail of the turn or the winning of the turn => so that the end of it for these reasons
     * @param wordToCheck
     *  Word input by the user, to check its validity
     */
    public void validateNewAttempt(String wordToCheck){

        if(wordToCheck.toLowerCase().compareTo(wordUsedInTheTurn.toLowerCase()) == 0){
            notifyEndOfTurn(TurnEndReason.WORD_GUESSED);
        }
        else {
            if (numberOfAttemptsLeft > 1) {

                numberOfAttemptsLeft--;
                AudioService.playFailureSound();
            } else {
                notifyEndOfTurn(TurnEndReason.NO_MORE_ATTEMPTS);
            }
        }
    }

    /**
     * Listener for the timerController
     * @param e
     *  Action event to know what actually happened.
     */
    private void notifyEndOfTimer(ActionEvent e){

        if (e.getActionCommand().equals(TimerController.TIMER_OF_SLICE_EXPIRED_ACTION_NAME)){
            notifyEndOfTurn(TurnEndReason.TURN_TIMER_EXPIRATION);
        } else if (e.getActionCommand().equals(TimerController.SESSION_EXPIRED_ACTION_NAME)) {
            timerController.endCurrentSlice(false);
            sessionManager.endSession();
        }
    }

    /**
     * The end of turn is detected in the level of turnManager but it has to be handled in the upper level of the
     * SessionManager
     * @param turnEndReason
     */
    private void notifyEndOfTurn(TurnEndReason turnEndReason){

        timerController.endCurrentSlice(turnEndReason == TurnEndReason.WORD_GUESSED);

        sessionManager.handleGenericEndOfTurn(turnEndReason);
    }
}
