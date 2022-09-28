package Widgets;

import ManagersAndServices.RepositoryService;

import javax.swing.*;
import java.awt.*;

public class BadgesController extends SketchEmAllWidget {
        private  BadgesPresentation badgesPresentation;
        private BadgesModel badgesModel;
        public ImageIcon currBadge;
        public static final Dimension PREFERRED_SIZE_BADGES_BOX = new Dimension(500,150);;

    public BadgesController(){
        this.badgesModel = new BadgesModel();
        this.badgesPresentation = new BadgesPresentation(this);
        badgesModel.setCurrBadge(RepositoryService.loadImageFromResources("badge_placeholder.png"));
        this.currBadge = badgesModel.getCurrBadge();
        repaint();
    }

    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }


    public double[] getPointInScreenOfNextBadge(){

        // TODO
        return null;
    }



    public void createNewBadge(ImageIcon iconForTheBadge){

    }

    public BadgesModel getModel(){
        return badgesModel;
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if(badgesPresentation!= null){
            badgesPresentation.paint((Graphics2D)pen, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_BADGES_BOX;
    }
    @Override
    //public Dimension getMaximumSize() {return PREFERRED_SIZE_BADGES_BOX;}
    public Dimension getMinimumSize() {return PREFERRED_SIZE_BADGES_BOX;}
}
