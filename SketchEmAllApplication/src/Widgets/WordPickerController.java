package Widgets;

import InternModels.PaintMode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WordPickerController extends SketchEmAllWidget {

    private WordPickerModel wordPickerModel;
    private WordPickerPresentation wordPickerPresentation;


    private ArrayList<ActionListener> endProcedureListeners = new ArrayList<>();
    public void addEndProcedureListeners(ActionListener endProcedureListener){
        this.endProcedureListeners.add(endProcedureListener);
    }
    public void removeEndProcedureListeners(ActionListener endProcedureListener){
        this.endProcedureListeners.remove(endProcedureListener);
    }
    private void notifyEndOfProcedure(){
        ActionEvent endOfProcedureActionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "use chosen word");

        for (ActionListener endProcedureListener: endProcedureListeners){
            endProcedureListener.actionPerformed(endOfProcedureActionEvent);
        }
    }
    private boolean notifiedEndOfProcedure = false;


    public WordPickerController(PaintMode paintMode, String chosenWordForTurn) {
        this.wordPickerModel = new WordPickerModel(paintMode, chosenWordForTurn);
        this.wordPickerPresentation = new WordPickerPresentation();

        wordPickerPresentation.installListeners(this);

        wordPickerModel.addChangeListener(
                e -> handleChangesInModel()
        );

        init();

        repaint();
    }


    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }

    private void init(){

        updateUI();
    }

    private void handleChangesInModel(){

        if(wordPickerModel.getIsReadyToExitProcedure() == true && notifiedEndOfProcedure == false){
            notifyEndOfProcedure();
            notifiedEndOfProcedure = true;
        }

        repaint();
    }

    public WordPickerModel getModel(){
        return wordPickerModel;
    }

    public void flipCard(){
        wordPickerModel.setIsFlipped(true);
    }

    public boolean isCardFlipped(){
        return  wordPickerModel.getIsFlipped();
    }



    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if (wordPickerPresentation != null){
            wordPickerPresentation.paint((Graphics2D)pen, this);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return wordPickerPresentation.getPreferredSize();
    }
    @Override
    public Dimension getMaximumSize() {
        return wordPickerPresentation.getMaximumSize();
    }
    @Override
    public Dimension getMinimumSize() {
        return wordPickerPresentation.getMinimumSize();
    }
}
