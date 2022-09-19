package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;
import ManagersAndServices.WordsManager;

import java.awt.*;

public class WordsInputController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {

    private WordsManager wordsManager;

    private WordsInputModel wordsValidatorModel;
    private WordsInputPresentation wordsValidatorPresentation;

    public WordsInputController(Container containerParentOfWidget, WordsManager wordsManager){

        super(containerParentOfWidget);

        this.wordsManager = wordsManager;

        wordsValidatorModel = new WordsInputModel();
        wordsValidatorPresentation = new WordsInputPresentation();
    }

    private void checkTypedWord(String typedWord){

        wordsManager.validateNewAttempt(typedWord);
    }


    @Override
    public void onPlayableTimeStop() {

    }

    @Override
    public void onPlayableTimeRestart() {

    }

    @Override
    public void showAndStartIdempotent() {

    }


    public void reset() {

    }
}
