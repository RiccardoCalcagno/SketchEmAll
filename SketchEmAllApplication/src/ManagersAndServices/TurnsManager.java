package ManagersAndServices;

import InternModels.PaintMode;
import InternModels.TurnEndReason;
import PaintingTools.PaintingToolsEnum;
import PaintingTools.PencilTool;
import Widgets.WordPickerController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Random;


public class TurnsManager{

    private SessionManager sessionManager;
    private TimeManager timeManager;

    // present only in a sub-procedure of the turn
    private WordPickerController wordPickerController;

    private Hashtable<PaintingToolsEnum, PaintMode> paintModesKit;


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
        Object[] possiblePaintModes = paintModesKit.values().toArray();

        return (PaintMode)possiblePaintModes[new Random().nextInt(possiblePaintModes.length)];
    }

    private void invokeWordPickingProcedure() throws Exception{

        if(wordPickerController != null){
            throw new Exception("a new procedure of this kind should be invoked only if there are no other actives");
        }

        String chosenWord = sessionManager.repositoryService.chooseNextWord();

        // TODO set it in the WordManager or WordInputController

        wordPickerController = new WordPickerController(
                chosePaintModeForNewTurn(),
                chosenWord
                );

        wordPickerController.addEndProcedureListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endWordPickingProcedure();
            }
        });

        sessionManager.appLayoutManager.instantiateWordPickerWidget(wordPickerController);
    }

    private void endWordPickingProcedure(){

        sessionManager.appLayoutManager.removeWordPickerWidget();

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


    private void handleFailOfTurn(TurnEndReason turnEndReason){
        sessionManager.handleGenericEndOfTurn(turnEndReason);
    }

    private void handleSuccessOfTurn(){
        sessionManager.handleGenericEndOfTurn(TurnEndReason.WORD_GUESSED);
    }
}
