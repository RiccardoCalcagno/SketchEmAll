package Widgets;

import ManagersAndServices.AppLayoutManager;
import ManagersAndServices.RepositoryService;
import ManagersAndServices.StyleUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.font.FontRenderContext;

public class CanvasPresentation{

    private static final Dimension PREFERRED_SIZE_CANVAS = new Dimension(600, 400);
    private static final Dimension MINIMUM_SIZE_CANVAS = new Dimension(100, 70);

    private JButton deleteLastDrawingButton;

    public void installUI(CanvasController canvas) {

        canvas.setLayout(new BorderLayout());
        JPanel panelForFooter = new JPanel(new BorderLayout());
        var transparentColor = new Color(0,0,0,0);
        panelForFooter.setBackground(transparentColor);
        canvas.add(panelForFooter, BorderLayout.SOUTH);
        panelForFooter.setBorder(new EmptyBorder(0,0,10,10));

        var rawIcon = RepositoryService.loadImageFromResources("lastDrawingIcon.png");
        var finalImage = rawIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        deleteLastDrawingButton = new JButton(new ImageIcon(finalImage));
        deleteLastDrawingButton.setBackground(transparentColor);
        deleteLastDrawingButton.setPreferredSize(new Dimension(25,25));
        deleteLastDrawingButton.addActionListener(e -> canvas.removeLastDrawing());
        deleteLastDrawingButton.setFocusPainted(false);

        panelForFooter.add(deleteLastDrawingButton, BorderLayout.EAST);

        installListeners(canvas);
    }

    protected void installListeners(CanvasController canvas) {

    }

    public void paint(Graphics2D pen, CanvasController canvasController) {
        CanvasModel model = canvasController.getModel();

        pen.setColor(Color.white);
        pen.fillRect(0,0, canvasController.getWidth(), canvasController.getHeight());

        pen.setColor(Color.black);
        var drawAnnotations = model.getDrawings();
        for (var drawAnnotation: drawAnnotations) {

            drawAnnotation.paint(pen);
        }

        if(canvasController.isActive()){
            StyleUtility.drawPaintModeFrame(
                    pen,
                    canvasController.getCurrentPaintMode(),
                    new Point(0,0),
                    canvasController.getSize(),
                    AppLayoutManager.BACKGROUND_APPLICATION
            );
        }
    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE_CANVAS;
    }
    public Dimension getMaximumSize() {
        return PREFERRED_SIZE_CANVAS;
    }
    public Dimension getMinimumSize() {
        return MINIMUM_SIZE_CANVAS;
    }
}
