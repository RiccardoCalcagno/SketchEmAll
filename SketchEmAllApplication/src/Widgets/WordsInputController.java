package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;
import ManagersAndServices.TurnsManager;

import java.awt.*;

public class WordsInputController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {

    private TurnsManager turnsManager;

    private WordsInputModel wordsValidatorModel;
    private WordsInputPresentation wordsValidatorPresentation;

    public WordsInputController(TurnsManager turnsManager){

        this.turnsManager = turnsManager;

        wordsValidatorModel = new WordsInputModel();
        wordsValidatorPresentation = new WordsInputPresentation();
    }

    private void checkTypedWord(String typedWord){

        turnsManager.validateNewAttempt(typedWord);
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
