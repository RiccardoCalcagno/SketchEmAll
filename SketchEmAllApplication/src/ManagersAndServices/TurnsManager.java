package ManagersAndServices;

import InternModels.PaintMode;
import InternModels.TurnFailReason;
import PaintingTools.PaintingToolsEnum;
import PaintingTools.PencilTool;
import Widgets.WordPickerController;

import java.awt.*;
import java.util.Hashtable;


public class TurnsManager {

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

    public void handleFailOfTurn(TurnFailReason turnFailReason){
        sessionManager.handleFailOfTurn(turnFailReason);
    }

    public void handleSuccessOfTurn(){
        sessionManager.handleSuccessOfTurn();
    }
}
