package Widgets;

import InternModels.PaintMode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WordPickerPresentation extends ComponentUI {

    private static final Color BACKGROUND_COLOR = new Color(50,50,50);

    @Override
    public void installUI(JComponent component) {
        WordPickerController controller = (WordPickerController) component;
        installStructureOfFrontSideCardUI(controller);
        installStructureOfBackSideCardUI(controller);

        component.setLayout(new BorderLayout());
    }

    private void installStructureOfFrontSideCardUI(WordPickerController controller){
        JPanel frontCardPanel = new JPanel();
        frontCardPanel.setBackground(BACKGROUND_COLOR);
        frontCardPanel.setLayout(new BoxLayout(frontCardPanel, BoxLayout.Y_AXIS));

        installFrontInteractiveImage(controller, frontCardPanel);

        installFrontDescription(controller, frontCardPanel);

        controller.setFrontCardPanel(frontCardPanel);

    }

    private void installFrontInteractiveImage(WordPickerController controller, JPanel frontCardPanel){
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(BACKGROUND_COLOR);
        imagePanel.setBorder(new EmptyBorder(90,50,0,50));
        JLabel interactiveImage = new JLabel(controller.getFrontSideImage());
        interactiveImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.flipCard();
            }
        });
        interactiveImage.setAlignmentX(JButton.CENTER_ALIGNMENT);
        imagePanel.add(interactiveImage);

        frontCardPanel.add(imagePanel);
    }
    private void installFrontDescription(WordPickerController controller, JPanel frontCardPanel){
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBackground(BACKGROUND_COLOR);
        descriptionPanel.setBorder(new EmptyBorder(10,50,50,50));

        JLabel descriptionLabel = new JLabel("<html>"+ controller.getDescription() +"</html>", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        descriptionLabel.setForeground(Color.white);
        descriptionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        descriptionPanel.add(descriptionLabel, BorderLayout.CENTER);

        frontCardPanel.add(descriptionPanel);
    }

    private void installStructureOfBackSideCardUI(WordPickerController controller) {
        JPanel backCardPanel = new JPanel();
        backCardPanel.setBackground(BACKGROUND_COLOR);
        backCardPanel.setLayout(new BorderLayout());

        installModeBackPanel(controller, backCardPanel);

        installWordPanel(controller, backCardPanel);

        installStartButton(controller, backCardPanel);


        controller.setBackCardPanel(backCardPanel);
    }

    private void installModeBackPanel(WordPickerController controller, JPanel backCardPanel){
        FlowLayout modeLayout = new FlowLayout();
        modeLayout.setAlignment(FlowLayout.CENTER);
        modeLayout.setHgap(20);

        JPanel modePanel = new JPanel(modeLayout);
        modePanel.setBackground(BACKGROUND_COLOR);
        modePanel.setBorder(new EmptyBorder(65,50,0,50));
        JLabel modeImage = new JLabel(controller.getModeImage());
        modePanel.add(modeImage);


        PaintMode paintMode = controller.getPaintMode();

        JPanel modeDescriptionPanel = new JPanel(new BorderLayout());
        modeDescriptionPanel.setBackground(BACKGROUND_COLOR);
        JLabel modeLabel = new JLabel(paintMode.uiName);
        modeLabel.setForeground(Color.white);
        modeDescriptionPanel.add(modeLabel, BorderLayout.NORTH);

        JLabel modeDescription = new JLabel("<html>"+ paintMode.uiDescription +"</html>");
        modeDescription.setPreferredSize(new Dimension(400, 80));
        modeDescription.setForeground(Color.white);
        modeDescriptionPanel.add(modeDescription, BorderLayout.CENTER);

        modePanel.add(modeDescriptionPanel);

        backCardPanel.add(modePanel, BorderLayout.NORTH);

    }

    private void installWordPanel(WordPickerController controller, JPanel backCardPanel){

        JPanel wordPanel = new JPanel(new BorderLayout());
        wordPanel.setBackground(BACKGROUND_COLOR);
        wordPanel.setBorder(new EmptyBorder(10, 50,35,50));
        JLabel word = new JLabel(controller.getChosenWordForTurn());
        word.setForeground(Color.white);
        word.setFont(new Font("SansSerif", Font.BOLD, 60));
        word.setHorizontalAlignment(JLabel.CENTER);
        wordPanel.add(word, BorderLayout.NORTH);

        backCardPanel.add(wordPanel, BorderLayout.CENTER);

    }

    private void installStartButton(WordPickerController controller, JPanel backCardPanel){

        FlowLayout layout =new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        JPanel startButtonPanel = new JPanel(layout);
        startButtonPanel.setBackground(Color.orange/*wordPickerPresentation.getBackgroundColor()*/);
        startButtonPanel.setBorder(new EmptyBorder(25,160,25,160));
        startButtonPanel.setPreferredSize(new Dimension(200, 100));

        JButton startButton = new JButton("START");
        startButton.addActionListener(e -> controller.notifyEndOfProcedure());
        startButtonPanel.add(startButton);
        startButton.setMinimumSize(new Dimension(300, 100));

        backCardPanel.add(startButtonPanel, BorderLayout.SOUTH);
    }

    @Override
    public Dimension getPreferredSize(JComponent component){
        return new Dimension(600, 450);
    }
    @Override
    public Dimension getMinimumSize(JComponent component){
        return getPreferredSize(component);
    }
    @Override
    public Dimension getMaximumSize(JComponent component){
        return getPreferredSize(component);
    }
}