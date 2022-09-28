package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;
import ManagersAndServices.TurnsManager;

import javax.swing.*;
import java.awt.*;

public class WordsInputController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {

    private TurnsManager turnsManager;

    private WordsInputModel wordsInputModel;
    private WordsInputPresentation wordsInputPresentation;

    @Override
    public boolean isActive(){
        return wordsInputModel.isActive();
    }


    public WordsInputController(TurnsManager turnsManager){

        this.turnsManager = turnsManager;

        wordsInputModel = new WordsInputModel();

        Init(wordsInputModel);
    }

    private void Init(WordsInputModel wordsInputModel){

        this.wordsInputModel = wordsInputModel;

        this.wordsInputPresentation = new WordsInputPresentation(this);


        wordsInputModel.addChangeListener(
                e -> onModelChange()
        );
        repaint();
    }

    private void checkTypedWord(String typedWord){

        turnsManager.validateNewAttempt(typedWord);
    }

    public void onSubmitRequest(){

    }

    public void onModelChange(){

        if(wordsInputModel.isActive()){

        }
        else{

        }
        repaint();
    }

    @Override
    public void onPlayableTimeStop() {

    }

    @Override
    public void onPlayableTimeRestart() {

    }

    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }


    public void reset() {

    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if(wordsInputPresentation!= null){
            wordsInputPresentation.paint((Graphics2D)pen, this);
        }
    }

    public WordsInputModel getModel(){
        return wordsInputModel;
    }


    @Override
    public Dimension getPreferredSize() {
        return this.wordsInputPresentation.getPreferredSize();
    }
    @Override
    public Dimension getMaximumSize() {
        return this.wordsInputPresentation.getMaximumSize();
    }
    @Override
    public Dimension getMinimumSize() {
        return this.wordsInputPresentation.getMinimumSize();
    }
}
