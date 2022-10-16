package InternModels;

public interface ObserverOfApplicationActivityStates {

    /**
     * This method is a handler for asynchronous changings at the session time performed by TimeManger
     * @param levelOfRequest
     *      level to which the game is brought to
     */
    void onChangeActivityStateLevel(ChangePlayingTimeRequestLevel levelOfRequest);
}
