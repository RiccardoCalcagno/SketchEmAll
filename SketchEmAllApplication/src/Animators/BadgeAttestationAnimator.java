package Animators;

import InternModels.TimerSlice;
import ManagersAndServices.LoopTaskService;
import Widgets.BadgesController;
import Widgets.TimerController;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class BadgeAttestationAnimator implements Animator{

    private LoopTaskService loopTaskService;
    private BadgesController badgesController;
    private TimerController timerController;

    public AnimationTaskForBadgeAttestation temporaryAnimationTaskForBadgeAttestation;


    public BadgeAttestationAnimator(BadgesController badgesController, TimerController timerController, LoopTaskService loopTaskService){
        this.badgesController = badgesController;
        this.timerController = timerController;
        this.loopTaskService = loopTaskService;

        this.temporaryAnimationTaskForBadgeAttestation = new AnimationTaskForBadgeAttestation(this, loopTaskService);
    }

    @Override
    public void PerformAnimation() {

        TimerSlice sliceOfTimerToMove = timerController.getCurrentSlice();
        Rectangle2D boundsOfSlice = sliceOfTimerToMove.getSliceShape().getBounds2D();
        double[] startPositionOfSlice = {boundsOfSlice.getCenterX(), boundsOfSlice.getCenterY()};

        double[] endPositionOfSlice = badgesController.getPointInScreenOfNextBadge();


        temporaryAnimationTaskForBadgeAttestation.prepareAnimation(sliceOfTimerToMove, startPositionOfSlice, endPositionOfSlice);

        loopTaskService.addTaskInLoop(temporaryAnimationTaskForBadgeAttestation);
    }


    public class AnimationTaskForBadgeAttestation implements LoopTaskService.TaskOfSession {
        private BadgeAttestationAnimator badgeAttestationAnimator;
        private LoopTaskService loopTaskService;

        private final double ANIMATION_SPEED = 0.4d;
        private double[] startPositionOfSlice;
        private double[] endPositionOfSlice;
        private TimerSlice sliceOfTimerToMove;

        public AnimationTaskForBadgeAttestation(BadgeAttestationAnimator badgeAttestationAnimator, LoopTaskService loopTaskService){
            this.badgeAttestationAnimator = badgeAttestationAnimator;
            this.loopTaskService = loopTaskService;
        }

        private void onEndAnimation(){
            loopTaskService.removeTaskFromLoop(this);
        }

        public void prepareAnimation(TimerSlice sliceOfTimerToMove, double[] startPositionOfSlice, double[] endPositionOfSlice){
            this.startPositionOfSlice = startPositionOfSlice;
            this.endPositionOfSlice = endPositionOfSlice;
            this.sliceOfTimerToMove = sliceOfTimerToMove;
        }

        @Override
        public void performTask() {
            // TODO
        }
    }
}
