package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;
import ManagersAndServices.TurnsManager;

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
        wordsInputPresentation = new WordsInputPresentation();
    }

    private void checkTypedWord(String typedWord){

        turnsManager.validateNewAttempt(typedWord);
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
}
