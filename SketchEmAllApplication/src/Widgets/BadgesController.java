package Widgets;

import ManagersAndServices.RepositoryService;

import javax.swing.*;
import java.awt.*;

public class BadgesController extends SketchEmAllWidget {
        private  BadgesPresentation badgesPresentation;
        private BadgesModel badgesModel;
        public ImageIcon currBadge;

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



    public void createNewBadge(ImageIcon bagdeIcon){
        //badgesModel.setCurrBadge(RepositoryService.loadImageFromResources("badge1.png"));
        badgesModel.setCurrBadge(bagdeIcon);
        this.currBadge = badgesModel.getCurrBadge();
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
        return badgesPresentation.getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return badgesPresentation.getPreferredSize();
    }
}
