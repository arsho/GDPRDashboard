package gdprdashboard;

import enums.UserMenuEnum;
import services.UserDashboardService;
import services.FileService;
import services.InteractiveService;

public class GDPRDashboard {

    public static void main(String[] args) {

        UserDashboardService userDashboardService = new UserDashboardService();
        FileService fileService = new FileService();
        InteractiveService interactiveService = new InteractiveService();
        interactiveService.showGDPRDashboardHeader();
        fileService.processPolicyData();
        fileService.processUserData();
        userDashboardService.createAllUserDashboards();

        while (true) {
            interactiveService.showMenu();
            int userChoice;
            String promptMsg = "Enter your choice: ";
            userChoice = interactiveService.nextIntegerInput(promptMsg);
            UserMenuEnum userChoosenItem = UserMenuEnum.getMenuFromIndex(userChoice);
            boolean programExit = false;
            switch (userChoosenItem) {
                case VIEW_ALL_CONSENT:
                    // Show all users dashboard
                    interactiveService.showAllUserDashboard();
                    break;
                case UPDATE_ONE_CONSENT:
                    // Update one user consent
                    interactiveService.updateUserConscentInteractive();
                    break;
                case VIEW_ONE_CONSENT:
                    // Show single user dashboard
                    interactiveService.showSingleUserDashboardInteractive();
                    break;
                case ADD_POLICY:
                    // Add a policy
                    interactiveService.addSinglePolicyInteractive();
                    break;
                case DELETE_POLICY:
                    // Delete a policy
                    interactiveService.deleteSinglePolicyInteractive();
                    break;
                case ADD_USER:
                    // Add an user
                    interactiveService.addUserInteractive();
                    break;
                case DELETE_USER:
                    // Delete an user
                    interactiveService.deleteUserInteractive();
                    break;
                case QUIT:
                    // Perform "quit" case.
                    programExit = true;
                    break;
                default:
                    // The user input an unexpected choice. Exit the program.
                    programExit = true;
                    break;
            }
            if (programExit) {
                break;
            }
        }
    }

}
