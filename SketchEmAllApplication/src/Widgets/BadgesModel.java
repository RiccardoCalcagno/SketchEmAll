package Widgets;

import javax.swing.*;

public class BadgesModel {
    private ImageIcon currBadge;

    public void setCurrBadge(ImageIcon badgeIcon) {
        this.currBadge = badgeIcon;
    }
    public ImageIcon getCurrBadge(){
        return  this.currBadge;
    }


}
