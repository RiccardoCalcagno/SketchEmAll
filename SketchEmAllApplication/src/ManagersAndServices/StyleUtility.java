package ManagersAndServices;

import java.awt.*;

/**
 * Class for styling widgets
 */
public class StyleUtility {

    private static final Stroke BASIC_STROKE = new BasicStroke();
    private static final Stroke THICK_STROKE = new BasicStroke(5f);

    private static final Font MAIN_FONT = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Add a colorful border to widget
     *
     * @param pen              graphics pen
     * @param colorFrame       color of border
     * @param name             label in border
     * @param topLeftCorner    position of the top left corner of widget
     * @param dimensionOfPanel the size of the widget
     * @param backgroundColor  the color of background
     */
    public static void drawPaintModeFrame(Graphics2D pen, Color colorFrame, String name, Point topLeftCorner, Dimension dimensionOfPanel, Color backgroundColor) {
        pen.setStroke(THICK_STROKE);

        pen.setColor(backgroundColor);
        pen.drawRect(topLeftCorner.x, topLeftCorner.y, (int) dimensionOfPanel.getWidth(), (int) dimensionOfPanel.getHeight());

        pen.setColor(colorFrame);
        pen.drawRoundRect(topLeftCorner.x + 2, topLeftCorner.y + 2, (int) dimensionOfPanel.getWidth() - 4, (int) dimensionOfPanel.getHeight() - 4, 10, 10);

        var nameOfMode = name;
        var boundOfModeLabels = MAIN_FONT.getStringBounds(nameOfMode, pen.getFontRenderContext());
        pen.fillRoundRect(topLeftCorner.x, topLeftCorner.y,
                (int) boundOfModeLabels.getWidth() + 22,
                (int) boundOfModeLabels.getHeight() + 8, 10, 10);

        pen.setColor(Color.black);
        pen.drawString(nameOfMode, topLeftCorner.x + 10, topLeftCorner.y + (int) (boundOfModeLabels.getHeight() * 0.5d + 8));
    }
}
