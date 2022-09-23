package ManagersAndServices;

import javax.swing.*;

public class RepositoryService {

    private static SessionManager sessionManager;

    public static void initializeAsSingleton(SessionManager sessionManager){
        RepositoryService.sessionManager = sessionManager;
    }

    public static String chooseNextWord(){

        return "cup";
    }

    public static ImageIcon loadImageFromResources(String iconName){

        String imgLocation = "/./"+iconName;

        // how to select an image in the resources: https://www.jetbrains.com/help/idea/add-items-to-project.html#import-items
        java.net.URL image = sessionManager.getClass().getResource(imgLocation);

        ImageIcon sourceImgae = new ImageIcon(image);

        return sourceImgae;
    }
}
