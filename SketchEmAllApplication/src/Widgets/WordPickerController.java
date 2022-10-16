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
 * Controller for the picking of a new word. It's a one-time widget, most of its attributes are final.
 * @see WordPickerPresentation
 * @see WordPickerModel
 */
public class WordPickerController extends SketchEmAllWidget {

    /**
     * Basic description for the frond side
     */
    final String FRONT_SIDE_CARD_DESCRIPTION = "Click the treasure to discover the mysterious word!";
    /**
     * Description for the front side when the players failed three times to guess the word.
     */
    final String THIRD_FAIL_DESCRIPTION = "Oops, you failed 3 times, so we picked another word for you to try again. Click the treasure to discover the mysterious word!";
    /**
     * Description for the front side when the players managed to guess the word.
     */
    final String WIN_DESCRIPTION = "You won! You even won a badge with your creation on it! Now click the treasure to discover another mysterious word!";
    /**
     * Description for the front side when the players ran out of time
     */
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

    /**
     * Default constructor
     * @param paintMode
     *  To know the mode that will be used for the drawing
     * @param chosenWordForTurn
     *  The word that was picked
     * @param callReason
     *  To know the context of the call to the word picker
     */
    public WordPickerController(PaintMode paintMode, String chosenWordForTurn, TurnEndReason callReason) {
        this.chosenWordForTurn = chosenWordForTurn;
        this.paintMode = paintMode;
        init(new WordPickerModel(this, callReason));
    }

    /**
     * Initializes the widget
     * @param wordPickerModel
     *  Model of the word picking utility
     */
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

    /**
     * Notifies the listeners that the user is fully aware of the picked word.
     */
    void notifyEndOfProcedure(){
        if (notifiedEndOfProcedure) return;

        ActionEvent endOfProcedureActionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "use chosen word");

        for (ActionListener endProcedureListener: endProcedureListeners){
            endProcedureListener.actionPerformed(endOfProcedureActionEvent);
        }

        notifiedEndOfProcedure = true;
    }


    /**
     * Changes the content of the view according to the changes in the model
     */
    private void handleChangesInModel(){

        if (wordPickerModel.isFlipped()){
            this.remove(frontCardPanel);
            this.add(backCardPanel);
        }
        else {
            this.remove(backCardPanel);
            this.add(frontCardPanel);
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
