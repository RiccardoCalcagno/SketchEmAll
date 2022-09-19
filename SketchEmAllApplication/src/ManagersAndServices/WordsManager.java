package ManagersAndServices;

import InternModels.StoppableAccordinglyToPlayableTime;
import Widgets.WordPickerController;

///<summary>
/// responsibilities: choose word procedures, management of attempts and
///
///</summary>
public class WordsManager implements StoppableAccordinglyToPlayableTime {

    // -------------------------------  Dependencies -----------------------------------------
    private WordRepositoryService wordRepositoryService;
    private WordPickerController temporaryWordPickerController;


    // -------------------------------  Attributes -----------------------------------------

    private boolean isCheckNewWordEnabled;
    private int numberOfAttemptsLeft;
    public int getNumberOfAttemptsLeft() { return this.numberOfAttemptsLeft; }

    private String currentWord;


    // -------------------------------  Public Methods -----------------------------------------


    public WordsManager(){

        wordRepositoryService = new WordRepositoryService();
    }



    public void validateNewAttempt(String wordToCheck){


        if(wordToCheck == currentWord){
            handleWordCorrespondence();
        }
        else {
            if (numberOfAttemptsLeft == 0) {

                numberOfAttemptsLeft--;
            } else {
                handleEndOfAttempts();
            }
        }
    }


    // -------------------------------  Private Methods -----------------------------------------

    private void handleEndOfAttempts(){

    }

    private void handleWordCorrespondence(){

    }



    @Override
    public void onPlayableTimeStop() {

    }

    @Override
    public void onPlayableTimeRestart() {

    }
}
