package Widgets;

import InternModels.PaintMode;
import ManagersAndServices.RepositoryService;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class WordPickerController extends SketchEmAllWidget {

    private WordPickerModel wordPickerModel;
    private WordPickerPresentation wordPickerPresentation;

    private static final String FRONT_SIDE_CARD_DESCRIPTION = "Click the treasure to discover the mysterious word!";

    private JPanel panelForFrontSideCard;
    private JPanel panelForBackSideCard;

    private final ArrayList<ActionListener> endProcedureListeners = new ArrayList<>();
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
        panelForFrontSideCard.setBackground(wordPickerPresentation.getBackgroundColor());
        panelForFrontSideCard.setLayout(new BoxLayout(panelForFrontSideCard, BoxLayout.Y_AXIS));

        JPanel panelForImageFrontCard = new JPanel();
        panelForImageFrontCard.setBackground(wordPickerPresentation.getBackgroundColor());
        panelForImageFrontCard.setBorder(new EmptyBorder(90,50,0,50));
        JLabel interactiveImageFrontCard = new JLabel(wordPickerModel.getFrontSideImage());
        interactiveImageFrontCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                flipCard();
            }
        });
        interactiveImageFrontCard.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelForImageFrontCard.add(interactiveImageFrontCard);

        JPanel panelForDescriptionFrontCard = new JPanel();
        panelForDescriptionFrontCard.setBackground(wordPickerPresentation.getBackgroundColor());
        panelForDescriptionFrontCard.setBorder(new EmptyBorder(10,50,50,50));
        JLabel labelForDescriptionFrontCard = new JLabel(FRONT_SIDE_CARD_DESCRIPTION);
        labelForDescriptionFrontCard.setFont(new Font("SansSerif", Font.PLAIN, 15));
        labelForDescriptionFrontCard.setForeground(Color.white);
        labelForDescriptionFrontCard.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panelForDescriptionFrontCard.add(labelForDescriptionFrontCard);

        panelForFrontSideCard.add(panelForImageFrontCard);
        panelForFrontSideCard.add(panelForDescriptionFrontCard);
    }

    private void installStructureOfBackSideCardUI() {
        panelForBackSideCard = new JPanel();
        panelForBackSideCard.setBackground(wordPickerPresentation.getBackgroundColor());
        panelForBackSideCard.setLayout(new BorderLayout());

        FlowLayout layoutForModePanel = new FlowLayout();
        layoutForModePanel.setAlignment(FlowLayout.CENTER);
        layoutForModePanel.setHgap(20);
        JPanel panelForModeBackCard = new JPanel(layoutForModePanel);
        panelForModeBackCard.setBackground(wordPickerPresentation.getBackgroundColor());
        panelForModeBackCard.setBorder(new EmptyBorder(65,50,0,50));
        JLabel modeImageFrontCard = new JLabel(wordPickerModel.getModeImage());
        panelForModeBackCard.add(modeImageFrontCard);

        JPanel panelForTextualDescriptionsOfMode = new JPanel(new BorderLayout());
        panelForTextualDescriptionsOfMode.setBackground(wordPickerPresentation.getBackgroundColor());
        JLabel modeLabelFrontCard = new JLabel(wordPickerModel.paintMode.uiName);
        modeLabelFrontCard.setForeground(Color.white);
        panelForTextualDescriptionsOfMode.add(modeLabelFrontCard, BorderLayout.NORTH);
        JLabel modeDescriptionFrontCard = new JLabel("<html>"+ wordPickerModel.paintMode.uiDesription+"</html>");
        modeDescriptionFrontCard.setPreferredSize(new Dimension(400, 80));
        modeDescriptionFrontCard.setForeground(Color.white);
        panelForTextualDescriptionsOfMode.add(modeDescriptionFrontCard, BorderLayout.CENTER);
        panelForModeBackCard.add(panelForTextualDescriptionsOfMode);

        JPanel panelForWordBackCard = new JPanel(new BorderLayout());
        panelForWordBackCard.setBackground(wordPickerPresentation.getBackgroundColor());
        panelForWordBackCard.setBorder(new EmptyBorder(10, 50,35,50));
        JLabel word = new JLabel(wordPickerModel.chosenWordForTurn);
        word.setForeground(Color.white);
        word.setFont(new Font("SansSerif", Font.BOLD, 60));
        word.setHorizontalAlignment(JLabel.CENTER);
        panelForWordBackCard.add(word, BorderLayout.NORTH);

        FlowLayout layout =new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        JPanel panelForStartGameTrigger = new JPanel(layout);
        panelForStartGameTrigger.setBackground(Color.orange/*wordPickerPresentation.getBackgroundColor()*/);
        panelForStartGameTrigger.setBorder(new EmptyBorder(25,160,25,160));
        panelForStartGameTrigger.setPreferredSize(new Dimension(200, 100));
        JButton buttonForStartTheGame = new JButton("START");

        buttonForStartTheGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyEndOfProcedure();
            }
        });
        panelForStartGameTrigger.add(buttonForStartTheGame);
        buttonForStartTheGame.setMinimumSize(new Dimension(300, 100));

        panelForBackSideCard.add(panelForModeBackCard, BorderLayout.NORTH);
        panelForBackSideCard.add(panelForWordBackCard, BorderLayout.CENTER);
        panelForBackSideCard.add(panelForStartGameTrigger, BorderLayout.SOUTH);
    }

    private void handleChangesInModel(){

        if(wordPickerModel.getIsReadyToExitProcedure() && !notifiedEndOfProcedure){
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
