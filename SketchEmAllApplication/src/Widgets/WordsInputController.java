package Widgets;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;
import ManagersAndServices.TurnsManager;

import java.awt.*;

public class WordsInputController extends SketchEmAllWidget implements ObserverOfApplicationActivityStates {

    private final TurnsManager turnsManager;

    private WordsInputModel wordsInputModel;
    private WordsInputPresentation wordsInputPresentation;


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

        var textToValidate =wordsInputPresentation.getCurrText();

        if(textToValidate.isEmpty() == false){
            checkTypedWord(textToValidate);
            reset();
        }
    }

    public void onModelChange(){

        wordsInputPresentation.setIsEnabledInteractions(wordsInputModel.isActive());

        repaint();
    }

    @Override
    public void onChangeActivityStateLevel(ChangePlayingTimeRequestLevel levelOfRequest) {

        if(levelOfRequest == ChangePlayingTimeRequestLevel.NONE){
            wordsInputModel.setIsActive(true);
        }
        else{
            wordsInputModel.setIsActive(false);
            wordsInputPresentation.clearInputField();
        }
    }


    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }


    public void reset() {
        wordsInputPresentation.clearInputField();
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
