package ManagersAndServices;

import javax.swing.*;

public class RepositoryService {

    public String chooseNextWord(){

        return null;
    }


    public ImageIcon loadImageFromResources(String iconName){

        String imgLocation = iconName + ".png";

        // how to select an image in the resources: https://www.jetbrains.com/help/idea/add-items-to-project.html#import-items
        java.net.URL image = getClass().getResource(imgLocation);

        ImageIcon sourceImgae = new ImageIcon(image);

        return sourceImgae;
    }
}
