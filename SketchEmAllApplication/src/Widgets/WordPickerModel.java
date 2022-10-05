package Widgets;

import InternModels.PaintMode;
import ManagersAndServices.RepositoryService;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class WordPickerModel {

    // add field of type: PaintMode -> the mode used (display icon, name and description)
    // add filed of type: String -> the word to Show

    public PaintMode paintMode;
    public String chosenWordForTurn;
    public ImageIcon modeImage;

    private ImageIcon frontSideImage;
    public ImageIcon getFrontSideImage(){
        return  this.frontSideImage;
    }
    public void setFrontSideImage(ImageIcon frontSideImage){
        this.frontSideImage = frontSideImage;
    }
    public ImageIcon getModeImage(){
        return this.modeImage;
    }

    private boolean isFlipped;

    private boolean isReadyToExitProcedure;
    public boolean getIsReadyToExitProcedure(){
        return this.isReadyToExitProcedure;
    }
    public void setIsReadyToExitProcedure(boolean isReadyToExitProcedure){
        if(!this.isReadyToExitProcedure){
            this.isReadyToExitProcedure = true;
            notifyChange();
        }
    }


    public WordPickerModel(PaintMode paintMode, String chosenWordForTurn){
        this.paintMode = paintMode;
        this.chosenWordForTurn = chosenWordForTurn;
        this.isFlipped = false;
        this.isReadyToExitProcedure = false;

        modeImage = paintMode.canvasRepresentativeIcon;
    }

    public boolean getIsFlipped(){
        return this.isFlipped;
    }
    public void setIsFlipped(boolean isFlipped){
        if(this.isFlipped != isFlipped){
            this.isFlipped = isFlipped;
            notifyChange();
        }
    }


    private final List<ChangeListener> changeListeners = new ArrayList<>();
    public void addChangeListener(ChangeListener changeListenerToAdd){
        changeListeners.add(changeListenerToAdd);
    }
    public void removeChangeListener(ChangeListener changeListenerToRemove){
        changeListeners.add(changeListenerToRemove);
    }
    public void notifyChange(){
        ChangeEvent changeEvent = new ChangeEvent(this);

        for (ChangeListener changeListener: changeListeners){
            changeListener.stateChanged(changeEvent);
        }
    }
}
