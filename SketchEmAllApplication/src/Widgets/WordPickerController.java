package Widgets;

import InternModels.PaintMode;
import InternModels.TurnEndReason;
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
    final String FRONT_SIDE_CARD_DESCRIPTION = "Click the treasure to discover the mysterious word!";
    final String THIRD_FAIL_DESCRIPTION = "Oops, you failed 3 times, so we picked another word for you to try again. Click the treasure to discover the mysterious word!";
    final String WIN_DESCRIPTION = "You won! You even won a badge with your creation on it! Now click the treasure to discover another mysterious word!";
    final String OUT_OF_TIME_DESCRIPTION = "Oops, it looks like you took too long, so we picked another word for you to try again. Click the treasure to discover the mysterious word!";

    public ImageIcon modeImage;
    private ImageIcon frontSideImage;
    private final String chosenWordForTurn;
    private String description;
    private boolean notifiedEndOfProcedure = false;
    private final PaintMode paintMode;
    private final ArrayList<ActionListener> endProcedureListeners = new ArrayList<>();

    private JPanel frontCardPanel;
    private JPanel backCardPanel;

    private WordPickerModel wordPickerModel;


    public WordPickerController(PaintMode paintMode, String chosenWordForTurn){
        this.chosenWordForTurn = chosenWordForTurn;
        this.paintMode = paintMode;
        init(new WordPickerModel(this, TurnEndReason.NONE));
    }

    public WordPickerController(PaintMode paintMode, String chosenWordForTurn, TurnEndReason callReason) {
        this.chosenWordForTurn = chosenWordForTurn;
        this.paintMode = paintMode;
        init(new WordPickerModel(this, callReason));
    }

    private void init(WordPickerModel wordPickerModel){
        this.wordPickerModel = wordPickerModel;

        setFrontSideImage(RepositoryService.loadImageFromResources("frontSideImage.png"));

        modeImage = this.paintMode.canvasRepresentativeIcon;

        wordPickerModel.addChangeListener(
                e -> handleChangesInModel()
        );

        setUI(new WordPickerPresentation());
        add(frontCardPanel);
    }


    void notifyEndOfProcedure(){
        ActionEvent endOfProcedureActionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "use chosen word");

        for (ActionListener endProcedureListener: endProcedureListeners){
            endProcedureListener.actionPerformed(endOfProcedureActionEvent);
        }
    }


    @Override
    public void instantiateWidget(Container placeToPutWidget){

    }

    private void handleChangesInModel(){

        if (wordPickerModel.isFlipped()){
            this.remove(frontCardPanel);
            this.add(backCardPanel);
        }
        else {
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
        wordPickerModel.flip();
    }

    public void setFrontCardPanel(JPanel frontCardPanel) {
        this.frontCardPanel = frontCardPanel;
    }

    public void setBackCardPanel(JPanel backCardPanel) {
        this.backCardPanel = backCardPanel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
