package InternModels;

public interface StoppableAccordinglyToPlayableTime {

    void onPlayableTimeStop();

    void onPlayableTimeRestart();

    boolean isActive();
}
