package InternModels;

import PaintingTools.AbstractTool;

import javax.swing.*;
import java.awt.*;

public class PaintMode {

    public PaintMode(String uiName, String uiDescription, ImageIcon canvasRepresentativeIcon, Color timerRepresentativeColor, double weightedTimeInPercentage, AbstractTool paintTool) {
        this.uiName = uiName;
        this.canvasRepresentativeIcon = canvasRepresentativeIcon;
        this.timerRepresentativeColor = timerRepresentativeColor;
        this.weightedTimeInPercentage = weightedTimeInPercentage;
        this.paintTool = paintTool;
        this.uiDescription = uiDescription;
    }

    // TODO change
    public static final int MAXIMUM_SECONDS_OF_HARDEST_MODE = 80;

    public String uiName;

    public String uiDescription;

    public ImageIcon canvasRepresentativeIcon;

    public Color timerRepresentativeColor;

    public double weightedTimeInPercentage;

    public AbstractTool paintTool;
}
