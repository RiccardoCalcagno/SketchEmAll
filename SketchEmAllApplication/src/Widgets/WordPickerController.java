package Widgets;

import InternModels.PaintMode;
import ManagersAndServices.RepositoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller for the picking of a new word
 */
public class WordPickerController extends SketchEmAllWidget {

    private WordPickerModel wordPickerModel;

    public ImageIcon modeImage;

    private ImageIcon frontSideImage;
    private final String chosenWordForTurn;

    private final PaintMode paintMode;

    private JPanel frontCardPanel;
    private JPanel backCardPanel;


    public final String FRONT_SIDE_CARD_DESCRIPTION = "Click the treasure to discover the mysterious word!";


    private boolean notifiedEndOfProcedure = false;




    private void init(WordPickerModel wordPickerModel){
        this.wordPickerModel = wordPickerModel;

        setFrontSideImage(RepositoryService.loadImageFromResources("frontSideImage.png"));

        modeImage = this.paintMode.canvasRepresentativeIcon;

        wordPickerModel.addChangeListener(
                e -> handleChangesInModel()
        );

        setUI(new WordPickerPresentation());
    }

    private final ArrayList<ActionListener> endProcedureListeners = new ArrayList<>();

    void notifyEndOfProcedure(){
        ActionEvent endOfProcedureActionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "use chosen word");

        for (ActionListener endProcedureListener: endProcedureListeners){
            endProcedureListener.actionPerformed(endOfProcedureActionEvent);
        }
    }

    public WordPickerController(PaintMode paintMode, String chosenWordForTurn) {
        this.chosenWordForTurn = chosenWordForTurn;
        this.paintMode = paintMode;
        init(new WordPickerModel(this));
    }


    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }

    private void handleChangesInModel(){

        if(wordPickerModel.getIsFlipped()){
            this.remove(frontCardPanel);
            this.add(backCardPanel);
        }
        else{
            this.remove(backCardPanel);
            this.add(frontCardPanel);
        }

        if(wordPickerModel.getIsReadyToExitProcedure() && !notifiedEndOfProcedure){
            notifyEndOfProcedure();
            notifiedEndOfProcedure = true;
        }

        revalidate();
    }

    public void addEndProcedureListeners(ActionListener endProcedureListener){
        this.endProcedureListeners.add(endProcedureListener);
    }

    public void removeEndProcedureListeners(ActionListener endProcedureListener){
        this.endProcedureListeners.remove(endProcedureListener);
    }

    public WordPickerModel getModel(){
        return wordPickerModel;
    }
    @Override
    public Dimension getPreferredSize() {
        return ui.getPreferredSize(this);
    }
    @Override
    public Dimension getMaximumSize() {
        return ui.getMaximumSize(this);
    }
    @Override
    public Dimension getMinimumSize() {
        return ui.getMinimumSize(this);
    }

    public ImageIcon getFrontSideImage(){
        return  this.frontSideImage;
    }
    public void setFrontSideImage(ImageIcon frontSideImage){
        this.frontSideImage = frontSideImage;
    }
    public ImageIcon getModeImage(){
        return this.modeImage;
    }

    public PaintMode getPaintMode() {
        return paintMode;
    }

    public String getChosenWordForTurn() {
        return chosenWordForTurn;
    }

    public void flipCard(){
        wordPickerModel.setIsFlipped(true);
    }

    public void setFrontCardPanel(JPanel frontCardPanel) {
        this.frontCardPanel = frontCardPanel;
    }

    public void setBackCardPanel(JPanel backCardPanel) {
        this.backCardPanel = backCardPanel;
    }
}
