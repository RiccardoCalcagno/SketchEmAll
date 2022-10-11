package Widgets;

import InternModels.PaintMode;
import ManagersAndServices.RepositoryService;
import ManagersAndServices.TurnsManager;

import javax.swing.*;
import java.awt.*;

public class BadgesController extends SketchEmAllWidget {
        private  BadgesPresentation badgesPresentation;
        private BadgesModel badgesModel;
        public ImageIcon currBadge;
        public PaintMode currPaintMode;
        public String currWord;

    public BadgesController(CanvasController cc, TurnsManager tm){
        this.badgesModel = new BadgesModel();
        this.badgesPresentation = new BadgesPresentation(this);
        //badgesModel.setCurrBadge(RepositoryService.loadImageFromResources("badge_placeholder.png"));
        this.currBadge = badgesModel.getCurrBadge();
        this.currPaintMode = cc.getCurrentPaintMode();
        this.currWord = tm.getWordUsedInTheTurn();
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
        repaint();
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
