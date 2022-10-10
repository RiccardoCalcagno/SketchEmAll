package ManagersAndServices;

import InternModels.PaintMode;

import java.awt.*;

public class StyleUtility {

    private static final Stroke BASIC_STROKE = new BasicStroke();
    private static final Stroke THICK_STROKE = new BasicStroke(5f);

    private static final Font MAIN_FONT = new Font("SansSerif", Font.PLAIN, 12);

    public static void drawPaintModeFrame(Graphics2D pen, PaintMode paintMode, Point topLeftCorner, Dimension dimensionOfPanel, Color backgroundColor){
        pen.setStroke(THICK_STROKE);

        pen.setColor(backgroundColor);
        pen.drawRect(topLeftCorner.x,topLeftCorner.y,(int)dimensionOfPanel.getWidth(), (int)dimensionOfPanel.getHeight());

        pen.setColor(paintMode.timerRepresentativeColor);
        pen.drawRoundRect(topLeftCorner.x+2,topLeftCorner.y+2,(int)dimensionOfPanel.getWidth() - 4, (int)dimensionOfPanel.getHeight() -4,10, 10);

        var nameOfMode = paintMode.uiName;
        var boundOfModeLabels =  MAIN_FONT.getStringBounds(nameOfMode, pen.getFontRenderContext());
        pen.fillRoundRect(topLeftCorner.x,topLeftCorner.y,
                (int)boundOfModeLabels.getWidth() +22,
                (int)boundOfModeLabels.getHeight() + 8,10, 10);

        pen.setColor(Color.black);
        pen.drawString(nameOfMode, topLeftCorner.x + 10, topLeftCorner.y + (int)(boundOfModeLabels.getHeight() * 0.5d + 8));
    }
}
