package ManagersAndServices;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.PaintMode;
import InternModels.TurnEndReason;
import PaintingTools.CrazyPenTool;
import PaintingTools.InvertedPenTool;
import PaintingTools.PaintingToolsEnum;
import PaintingTools.PencilTool;
import Widgets.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;


public class TurnsManager{

    public static final int NUM_OF_ATTEMPT_EACH_TURN = 3;

    public static final int DURATION_OF_SESSION_IN_SECONDS = 40000;

    private SessionManager sessionManager;
    private TimeManager timeManager;

    private CanvasController canvasController;
    private WordsInputController wordsInputController;
    private TimerController timerController;
    // present only in a sub-procedure of the turn
    private WordPickerController wordPickerController;

    private BadgesController badgesController;

    private Dictionary<PaintingToolsEnum, PaintMode> paintModesKit = new Hashtable<>();

    private PaintMode modeUsedInTheTurn = null;
    public PaintMode getModeUsedInTheTurn(){
        return this.modeUsedInTheTurn;
    }
    private String wordUsedInTheTurn = null;
    public String getWordUsedInTheTurn(){
        return this.wordUsedInTheTurn;
    }
    private int numberOfAttemptsLeft;

    private ActionListener expiredTimeForCurrentTurn = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            notifyEndOfTurn(TurnEndReason.TURN_TIMER_EXPIRATION);
        }
    };

    public TurnsManager(SessionManager sessionManager){

        this.sessionManager = sessionManager;
        this.timeManager = sessionManager.timeManager;

        setPaintModesKit();
    }

    public void setTurnWidgets(CanvasController canvasController, WordsInputController wordsInputController, TimerController timerController){
        this.canvasController = canvasController;
        this.wordsInputController = wordsInputController;
        this.timerController = timerController;
        this.badgesController = new BadgesController(canvasController, this);
        this.timerController.addActionListener(expiredTimeForCurrentTurn);
    }


    private PaintMode chosePaintModeForNewTurn(){
        var paintModesEnumeration = paintModesKit.elements();
        int indexOfChosenMode = new Random().nextInt(paintModesKit.size());
        while(indexOfChosenMode > 0){
            indexOfChosenMode--;
            paintModesEnumeration.nextElement();
        }
        return paintModesEnumeration.nextElement();
    }

    public void startTurn() throws Exception{

        System.out.println("starting new turn");
        wordUsedInTheTurn =  RepositoryService.chooseNextWord();
        //changed to normal pen
        modeUsedInTheTurn = chosePaintModeForNewTurn();

        canvasController.reset();
        wordsInputController.reset();

        numberOfAttemptsLeft = NUM_OF_ATTEMPT_EACH_TURN;

        try {
            invokeWordPickingProcedure(
                    e -> startPlayingInTheTurn()
            );
        }
        catch (Exception e){
            System.out.println(e.getMessage()+". Probably due to: two procedure of word picking at the same time");
        }
    }

    private void invokeWordPickingProcedure(ActionListener endOfProcedureEvent) throws Exception{

        if(wordPickerController != null){
            throw new Exception("a new procedure of this kind should be invoked only if there are no other actives");
        }

        wordPickerController = new WordPickerController(
                modeUsedInTheTurn,
                wordUsedInTheTurn
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

    private void startPlayingInTheTurn(){

        timerController.createNewSliceForNewTurn(modeUsedInTheTurn);

        this.timeManager.resumeTime_levelledRequest(ChangePlayingTimeRequestLevel.TURN_OVER);
    }


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
        paintModesKit.put(PaintingToolsEnum.INVERTED_PEN,
                new PaintMode(
                        "Inverted pen",
                        "Have you ever drawn in a mirror? No? This changes now. Good luck! O:)",
                        RepositoryService.loadImageFromResources("pencil_icon.png"),
                        Color.cyan,
                        0.8d,
                        new InvertedPenTool()
                )
        );
    }


    public void validateNewAttempt(String wordToCheck){

        if(wordToCheck.toLowerCase().compareTo(wordUsedInTheTurn.toLowerCase()) == 0){
            notifyEndOfTurn(TurnEndReason.WORD_GUESSED);
        }
        else {
            if (numberOfAttemptsLeft > 0) {

                numberOfAttemptsLeft--;
            } else {
                notifyEndOfTurn(TurnEndReason.NO_MORE_ATTEMPTS);
            }
        }
    }


    private void notifyEndOfTurn(TurnEndReason turnEndReason){

        timerController.endCurrentSlice(turnEndReason == TurnEndReason.WORD_GUESSED);
        /*if (turnEndReason == TurnEndReason.WORD_GUESSED){
            canvasController.takeScreenshotOfDrawing();
            badgesController.createNewBadge(canvasController.takeScreenshotOfDrawing());
        }*/
        sessionManager.handleGenericEndOfTurn(turnEndReason);
    }
}
