package gdprdashboard;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class InteractiveGDPRDashboard {
    public static void main(String[] args) {
        System.out.println("WELCOME TO GDPR DASHBOARD");
        System.out.println("===========================================================\n");
        UserDashboardService userDashboardService = new UserDashboardService();
        FileService fileService = new FileService();
        InteractiveService interactiveService = new InteractiveService();
        fileService.processPolicyData();
        fileService.processUserData();
        userDashboardService.createAllUserDashboards();
      
        while (true) {
            interactiveService.showMenu();
            int userChoice;
            userChoice = interactiveService.sc.nextInt();
            boolean programExit = false;
            switch (userChoice) {
                case 1:
                    // Show all users dashboard
                    interactiveService.showAllUserDashboard();
                    break;
                case 2:
                    // Update one user consent
                    interactiveService.updateUserConscentInteractive();
                    break;
                case 3:
                    // Show single user dashboard
                    interactiveService.showSingleUserDashboardInteractive();
                    break;
                case 4:
                    // Add a policy
                    interactiveService.addSinglePolicyInteractive();
                    break;
                case 5:
                    // Delete a policy
                    interactiveService.deleteSinglePolicyInteractive();
                    break;
                case 6:
                    // Add an user
                    interactiveService.addUserInteractive();
                    break;
                case 7:
                    // Delete an user
                    interactiveService.deleteUserInteractive();
                    break;
                case 100:
                    // Perform "quit" case.
                    programExit = true;
                    break;
                default:
                    // The user input an unexpected choice.
                    programExit = true;
                    break;
            }
            if (programExit) {
                break;
            }
        }
    }

}
