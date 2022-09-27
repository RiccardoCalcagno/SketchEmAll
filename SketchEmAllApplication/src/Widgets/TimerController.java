package Widgets;

import InternModels.StoppableAccordinglyToPlayableTime;
import ManagersAndServices.RepositoryService;
import ManagersAndServices.TimeManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TimerController extends SketchEmAllWidget implements StoppableAccordinglyToPlayableTime {

    private TimeManager timeManager;
    private TimerModel timerModel;
    private TimerPresentation timerPresentation;
    private BufferedImage timerBuffImage;

    @Override
    public boolean isActive(){
        return timerModel.isActive();
    }

    public TimerController(TimeManager timeManager){

        this.timeManager = timeManager;

        timerModel = new TimerModel();
        timerModel.setTimerImage(RepositoryService.loadImageFromResources("timer.png"));
        timerPresentation = new TimerPresentation();
        Init(timerModel);
    }


    private void Init(TimerModel model){

        this.timerModel = model;

        this.timerPresentation = new TimerPresentation();

        timerModel.addChangeListener(
                e -> onModelChange()
        );

        //some UI stuff that probably should be moved?
        //JLabel iconLbl = new JLabel();
        //iconLbl.setIcon(timerModel.getTimerImage());
        this.setLayout(new BorderLayout());
        //this.add(iconLbl, BorderLayout.CENTER);
        repaint();

    }



    public void onModelChange(){

        if(timerModel.isActive()){

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

    public Shape getShapeOfLastSlice(){

        // TODO
        return null;
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if(timerPresentation!= null){
            timerPresentation.paint((Graphics2D)pen, this);
        }
    }

    public TimerModel getModel(){
        return timerModel;
    }


    @Override
    public Dimension getPreferredSize() {
        return this.timerPresentation.getPreferredSize();
    }
    @Override
    public Dimension getMaximumSize() {
        return this.timerPresentation.getMaximumSize();
    }
    @Override
    public Dimension getMinimumSize() {
        return this.timerPresentation.getMinimumSize();
    }


}
