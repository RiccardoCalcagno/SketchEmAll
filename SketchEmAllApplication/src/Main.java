import ManagersAndServices.AppLayoutManager;
import ManagersAndServices.SessionManager;

public class Main {
    public static void main(String[] args) {
        SessionManager sessionManager = new SessionManager();

        sessionManager.startSession();
    }
}