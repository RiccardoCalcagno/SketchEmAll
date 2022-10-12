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
        installListeners(component);
        WordPickerController controller = (WordPickerController) component;
        JPanel frontCardPanel = installStructureOfFrontSideCardUI(controller);
        installStructureOfBackSideCardUI(controller);

        component.setLayout(new BorderLayout());
        component.add(frontCardPanel);
    }



    private JPanel installStructureOfFrontSideCardUI(WordPickerController controller){
        JPanel frontCardPanel = new JPanel();
        frontCardPanel.setBackground(BACKGROUND_COLOR);
        frontCardPanel.setLayout(new BoxLayout(frontCardPanel, BoxLayout.Y_AXIS));

        JPanel panelForImageFrontCard = new JPanel();
        panelForImageFrontCard.setBackground(BACKGROUND_COLOR);
        panelForImageFrontCard.setBorder(new EmptyBorder(90,50,0,50));
        JLabel interactiveImageFrontCard = new JLabel(controller.getFrontSideImage());
        interactiveImageFrontCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.flipCard();
            }
        });
        interactiveImageFrontCard.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelForImageFrontCard.add(interactiveImageFrontCard);

        JPanel panelForDescriptionFrontCard = new JPanel();
        panelForDescriptionFrontCard.setBackground(BACKGROUND_COLOR);
        panelForDescriptionFrontCard.setBorder(new EmptyBorder(10,50,50,50));
        JLabel labelForDescriptionFrontCard = new JLabel(controller.FRONT_SIDE_CARD_DESCRIPTION);
        labelForDescriptionFrontCard.setFont(new Font("SansSerif", Font.PLAIN, 15));
        labelForDescriptionFrontCard.setForeground(Color.white);
        labelForDescriptionFrontCard.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panelForDescriptionFrontCard.add(labelForDescriptionFrontCard);

        frontCardPanel.add(panelForImageFrontCard);
        frontCardPanel.add(panelForDescriptionFrontCard);

        controller.setFrontCardPanel(frontCardPanel);

        return frontCardPanel;
    }

    private void installStructureOfBackSideCardUI(WordPickerController controller) {
        JPanel backCardPanel = new JPanel();
        backCardPanel.setBackground(BACKGROUND_COLOR);
        backCardPanel.setLayout(new BorderLayout());

        FlowLayout layoutForModePanel = new FlowLayout();
        layoutForModePanel.setAlignment(FlowLayout.CENTER);
        layoutForModePanel.setHgap(20);
        JPanel panelForModeBackCard = new JPanel(layoutForModePanel);
        panelForModeBackCard.setBackground(BACKGROUND_COLOR);
        panelForModeBackCard.setBorder(new EmptyBorder(65,50,0,50));
        JLabel modeImageFrontCard = new JLabel(controller.getModeImage());
        panelForModeBackCard.add(modeImageFrontCard);


        PaintMode paintMode = controller.getPaintMode();
        JPanel panelForTextualDescriptionsOfMode = new JPanel(new BorderLayout());
        panelForTextualDescriptionsOfMode.setBackground(BACKGROUND_COLOR);
        JLabel modeLabelFrontCard = new JLabel(paintMode.uiName);
        modeLabelFrontCard.setForeground(Color.white);
        panelForTextualDescriptionsOfMode.add(modeLabelFrontCard, BorderLayout.NORTH);
        JLabel modeDescriptionFrontCard = new JLabel("<html>"+ paintMode.uiDescription +"</html>");
        modeDescriptionFrontCard.setPreferredSize(new Dimension(400, 80));
        modeDescriptionFrontCard.setForeground(Color.white);
        panelForTextualDescriptionsOfMode.add(modeDescriptionFrontCard, BorderLayout.CENTER);
        panelForModeBackCard.add(panelForTextualDescriptionsOfMode);

        JPanel panelForWordBackCard = new JPanel(new BorderLayout());
        panelForWordBackCard.setBackground(BACKGROUND_COLOR);
        panelForWordBackCard.setBorder(new EmptyBorder(10, 50,35,50));
        JLabel word = new JLabel(controller.getChosenWordForTurn());
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

        buttonForStartTheGame.addActionListener(e -> controller.notifyEndOfProcedure());
        panelForStartGameTrigger.add(buttonForStartTheGame);
        buttonForStartTheGame.setMinimumSize(new Dimension(300, 100));

        backCardPanel.add(panelForModeBackCard, BorderLayout.NORTH);
        backCardPanel.add(panelForWordBackCard, BorderLayout.CENTER);
        backCardPanel.add(panelForStartGameTrigger, BorderLayout.SOUTH);

        controller.setBackCardPanel(backCardPanel);
    }

    protected void installListeners(JComponent wordPickerController)
    {

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