package ManagersAndServices;

import InternModels.PaintMode;
import InternModels.TurnEndReason;
import PaintingTools.PaintingToolsEnum;
import PaintingTools.PencilTool;
import Widgets.WordPickerController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;


public class TurnsManager{

    private SessionManager sessionManager;
    private TimeManager timeManager;

    // present only in a sub-procedure of the turn
    private WordPickerController wordPickerController;

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


    public TurnsManager(SessionManager sessionManager){

        this.sessionManager = sessionManager;
        timeManager = sessionManager.timeManager;

        setPaintModesKit();
    }

    public void startTurn(){
        try {
            invokeWordPickingProcedure();
        }
        catch (Exception e){
            System.out.println("[DEBUG error] two procedure of word picking at the same time");
        }
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

    private void invokeWordPickingProcedure() throws Exception{

        if(wordPickerController != null){
            throw new Exception("a new procedure of this kind should be invoked only if there are no other actives");
        }

        String chosenWord = sessionManager.repositoryService.chooseNextWord();

        modeUsedInTheTurn = chosePaintModeForNewTurn();

        wordPickerController = new WordPickerController(
                modeUsedInTheTurn,
                chosenWord
                );

        wordPickerController.addEndProcedureListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPlayingInTheTurn();
            }
        });

        sessionManager.appLayoutManager.instantiateWordPickerWidget(wordPickerController);


        //TODO remember to delete this, it is already done by wordPickerController.actionPerformed
        startPlayingInTheTurn();
    }

    private void startPlayingInTheTurn(){

        sessionManager.appLayoutManager.removeWordPickerWidget();

        sessionManager.canvasController.reset();

        timeManager.resumeSessionTime();
    }


    public void setPaintModesKit(){
        paintModesKit = new Hashtable<>();

        // ------------------------ Pencil PaintMode -----------------------

        paintModesKit.put(PaintingToolsEnum.PENCIL,
                new PaintMode(
                "Pencil",
                "Keep pressed and drag the mouse to draw the lines for your amazing painting ;)",
                sessionManager.repositoryService.loadImageFromResources("pencil_icon.png"),
                Color.orange,
                60000,
                new PencilTool()
            )
        );
    }


    public void validateNewAttempt(String wordToCheck){

        if(wordToCheck == wordUsedInTheTurn){
            handleSuccessOfTurn();
        }
        else {
            if (numberOfAttemptsLeft == 0) {

                numberOfAttemptsLeft--;
            } else {
                handleFailOfTurn(TurnEndReason.NO_MORE_ATTEMPTS);
            }
        }
    }


    private void handleFailOfTurn(TurnEndReason turnEndReason){
        sessionManager.handleGenericEndOfTurn(turnEndReason);
    }

    private void handleSuccessOfTurn(){
        sessionManager.handleGenericEndOfTurn(TurnEndReason.WORD_GUESSED);
    }
}
