package InternModels;

import PaintingTools.AbstractPaintTool;

import javax.swing.*;
import java.awt.*;

public class PaintMode {

    public PaintMode(String uiName, String uiDesription, ImageIcon canvasRepresentativeIcon, Color timerRepresentativeColor, int weightedTimeInMilliseconds, AbstractPaintTool paintTool) {
        this.uiName = uiName;
        this.canvasRepresentativeIcon = canvasRepresentativeIcon;
        this.timerRepresentativeColor = timerRepresentativeColor;
        this.weightedTimeInMilliseconds = weightedTimeInMilliseconds;
        this.paintTool = paintTool;
        this.uiDesription = uiDesription;
    }

    public String uiName;

    public String uiDesription;

    public ImageIcon canvasRepresentativeIcon;

    public Color timerRepresentativeColor;

    public int weightedTimeInMilliseconds;

    public AbstractPaintTool paintTool;
}
