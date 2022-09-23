package Widgets;

import InternModels.PaintMode;
import ManagersAndServices.RepositoryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WordPickerController extends SketchEmAllWidget {

    private WordPickerModel wordPickerModel;
    private WordPickerPresentation wordPickerPresentation;

    private static final String FRONT_SIDE_CARD_DESCRIPTION = "click the treasure to discover the mysterious word!";

    private JPanel panelForFrontSideCard;
    private JPanel panelForBackSideCard;

    private ArrayList<ActionListener> endProcedureListeners = new ArrayList<>();
    public void addEndProcedureListeners(ActionListener endProcedureListener){
        this.endProcedureListeners.add(endProcedureListener);
    }
    public void removeEndProcedureListeners(ActionListener endProcedureListener){
        this.endProcedureListeners.remove(endProcedureListener);
    }
    private void notifyEndOfProcedure(){
        ActionEvent endOfProcedureActionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "use chosen word");

        for (ActionListener endProcedureListener: endProcedureListeners){
            endProcedureListener.actionPerformed(endOfProcedureActionEvent);
        }
    }
    private boolean notifiedEndOfProcedure = false;


    public WordPickerController(PaintMode paintMode, String chosenWordForTurn) {

        init(new WordPickerModel(paintMode, chosenWordForTurn));
    }


    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }

    private void init(WordPickerModel wordPickerModel){
        this.wordPickerModel = wordPickerModel;

        wordPickerModel.setFrontSideImage(RepositoryService.loadImageFromResources("frontSideImage.png"));

        this.wordPickerPresentation = new WordPickerPresentation();

        wordPickerPresentation.installUI(this);

        wordPickerModel.addChangeListener(
                e -> handleChangesInModel()
        );

        // ---------------- UI structure --------

        this.setLayout(new BorderLayout());

        installStructureOfFrontSideCardUI();
        installStructureOfBackSideCardUI();

        this.add(panelForFrontSideCard);
        this.revalidate();
    }

    private void installStructureOfFrontSideCardUI(){
        panelForFrontSideCard = new JPanel();
        panelForFrontSideCard.setLayout(new BoxLayout(panelForFrontSideCard, BoxLayout.Y_AXIS));

        JPanel panelForImageFrontCard = new JPanel();
        panelForImageFrontCard.setBorder(new EmptyBorder(50,50,25,50));
        JButton buttonForImageFrontCard = new JButton(wordPickerModel.getFrontSideImage());
        buttonForImageFrontCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipCard();
            }
        });
        buttonForImageFrontCard.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelForImageFrontCard.add(buttonForImageFrontCard);

        JPanel panelForDescriptionFrontCard = new JPanel(new BorderLayout());
        panelForDescriptionFrontCard.setBorder(new EmptyBorder(25,50,50,50));
        JLabel labelForDescriptionFrontCard = new JLabel(FRONT_SIDE_CARD_DESCRIPTION);
        labelForDescriptionFrontCard.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panelForDescriptionFrontCard.add(labelForDescriptionFrontCard, BorderLayout.NORTH);

        panelForFrontSideCard.add(panelForImageFrontCard);
        panelForFrontSideCard.add(panelForDescriptionFrontCard);
    }

    private void installStructureOfBackSideCardUI() {
        panelForBackSideCard = new JPanel();
        panelForBackSideCard.setLayout(new BoxLayout(panelForBackSideCard, BoxLayout.Y_AXIS));

        FlowLayout layoutForModePanel = new FlowLayout();
        layoutForModePanel.setAlignment(FlowLayout.CENTER);
        layoutForModePanel.setHgap(20);
        JPanel panelForModeBackCard = new JPanel(layoutForModePanel);
        panelForModeBackCard.setBorder(new EmptyBorder(50,50,25,50));
        JLabel modeImageFrontCard = new JLabel(wordPickerModel.paintMode.canvasRepresentativeIcon);
        panelForModeBackCard.add(modeImageFrontCard);

        JPanel panelForTextualDescriptionsOfMode = new JPanel(new BorderLayout());

        JLabel modeLableFrontCard = new JLabel(wordPickerModel.paintMode.uiName);
        panelForTextualDescriptionsOfMode.add(modeLableFrontCard, BorderLayout.NORTH);
        JLabel modeDescriptionFrontCard = new JLabel(wordPickerModel.paintMode.uiDesription);
        panelForTextualDescriptionsOfMode.add(modeLableFrontCard, BorderLayout.CENTER);
        panelForModeBackCard.add(panelForTextualDescriptionsOfMode);

        JPanel panelForStartGameTrigger = new JPanel(new BorderLayout());
        panelForStartGameTrigger.setBorder(new EmptyBorder(25,50,50,50));
        JButton buttonForStartTheGame = new JButton("START");
        buttonForStartTheGame.setPreferredSize(new Dimension(200, 70));

        buttonForStartTheGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyEndOfProcedure();
            }
        });
        panelForStartGameTrigger.add(buttonForStartTheGame, BorderLayout.NORTH);

        panelForBackSideCard.add(panelForModeBackCard);
        panelForBackSideCard.add(panelForStartGameTrigger);
    }

    private void handleChangesInModel(){

        if(wordPickerModel.getIsReadyToExitProcedure() == true && notifiedEndOfProcedure == false){
            notifyEndOfProcedure();
            notifiedEndOfProcedure = true;
        }

        repaint();
    }

    public WordPickerModel getModel(){
        return wordPickerModel;
    }

    public void flipCard(){
        wordPickerModel.setIsFlipped(true);

        this.remove(panelForFrontSideCard);
        this.add(panelForBackSideCard);

        this.revalidate();
    }

    public boolean isCardFlipped(){
        return  wordPickerModel.getIsFlipped();
    }



    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);

        if (wordPickerPresentation != null){
            wordPickerPresentation.paint((Graphics2D)pen, this);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return wordPickerPresentation.getPreferredSize();
    }
    @Override
    public Dimension getMaximumSize() {
        return wordPickerPresentation.getMaximumSize();
    }
    @Override
    public Dimension getMinimumSize() {
        return wordPickerPresentation.getMinimumSize();
    }
}
