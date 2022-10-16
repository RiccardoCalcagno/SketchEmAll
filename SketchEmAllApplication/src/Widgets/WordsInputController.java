package Widgets;

import InternModels.ChangePlayingTimeRequestLevel;
import InternModels.ObserverOfApplicationActivityStates;
import InternModels.PaintMode;
import ManagersAndServices.TurnsManager;

import java.awt.*;

/**
 * Controller for the input of a new word.
 *
 * @see WordsInputModel
 * @see WordsInputPresentation
 */
public class WordsInputController extends SketchEmAllWidget implements ObserverOfApplicationActivityStates {

    private final TurnsManager turnsManager;

    private WordsInputModel wordsInputModel;
    private WordsInputPresentation wordsInputPresentation;

    /**
     * Default constructor
     @param turnsManager reference to {@link TurnsManager}
     */
    public WordsInputController(TurnsManager turnsManager) {

        this.turnsManager = turnsManager;

        wordsInputModel = new WordsInputModel();

        Init(wordsInputModel);
    }

    private void Init(WordsInputModel wordsInputModel) {

        this.wordsInputModel = wordsInputModel;

        this.wordsInputPresentation = new WordsInputPresentation();

        this.wordsInputPresentation.installUI(this);

        wordsInputModel.addChangeListener(
                e -> onModelChange()
        );

        repaint();
    }

    /**
     * Chek if typed word is correct
     *
     * @param typedWord the inputted word
     */
    private void checkTypedWord(String typedWord) {

        turnsManager.validateNewAttempt(typedWord);
    }

    /**
     * Every time the submit button is clicked
     */
    public void onSubmitRequest() {

        var textToValidate = wordsInputPresentation.getCurrText();

        if (textToValidate.isEmpty() == false) {
            checkTypedWord(textToValidate);
            reset();
        }
    }

    /**
     * Every time the Model is changed
     */
    public void onModelChange() {

        wordsInputPresentation.setIsEnabledInteractions(wordsInputModel.isActive());

        repaint();
    }

    @Override
    public void onChangeActivityStateLevel(ChangePlayingTimeRequestLevel levelOfRequest) {

        if (levelOfRequest == ChangePlayingTimeRequestLevel.NONE) {
            wordsInputModel.setIsActive(true);
        } else {
            wordsInputModel.setIsActive(false);
            wordsInputPresentation.clearInputField();
        }
    }


    /**
     * Clear the input field
     */
    @Override
    public void reset() {
        wordsInputPresentation.clearInputField();
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if (wordsInputPresentation != null) {
            wordsInputPresentation.paint((Graphics2D) pen, this);
        }
    }

    public WordsInputModel getModel() {
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
