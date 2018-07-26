package gdprdashboard;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class InteractiveGDPRDashboard {

    Scanner sc = new Scanner(System.in);
    private Scanner fileScanner;
    UserService userService = new UserService();
    PolicyService policyService = new PolicyService();
    UserDashboardService userDashboardService = new UserDashboardService();

    /**
     * Get input file path from user.
     *
     * @return file
     */
    public File getInputFile(String promptMessage, String defaultFile) {
        String errorMessage = "File not found, try again.";
        File file = null;
        String filePath = "";
        // Run an infinite loop until user gives correct file path
        while (true) {
            System.out.print(promptMessage);
            filePath = sc.nextLine().trim();
            try {
                if (filePath.equals("")) {
                    filePath = defaultFile;
                }
                file = new File(filePath);
                if (file.exists()) {
                    System.out.println("Imported " + filePath);
                    return file;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (Exception ex) {
                System.out.println(errorMessage);
            }
        }
    }

    public File getUserDataFile() {
        File file = null;
        String filePath = "";
        String promptMessage = "Enter path for user data file or for default file press Enter: ";
        String defaultFile = "src/gdprdashboard/data/user-data.txt";
        return getInputFile(promptMessage, defaultFile);
    }

    public File getPolicyDataFile() {
        File file = null;
        String filePath = "";
        String promptMessage = "Enter path for user data file or for default file press Enter: ";
        String defaultFile = "src/gdprdashboard/data/policy-data.txt";
        return getInputFile(promptMessage, defaultFile);
    }

    /**
     * Check if user given users file has valid data
     *
     * @param file
     */
    public ArrayList<User> getUserData(File file) {
        ArrayList<User> users = new ArrayList<User>();
        String errorMessageData = "Data in input file is not correct. Try again.";
        String errorMessageLineContent = "Each line must have 3 Strings. Try again.";
        try {
            fileScanner = new Scanner(file);
            // Loop each line of the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                // Split each line using space
                String[] parts = line.split(",");

                // Each line should have two parts: name, description
                if (parts.length != 3) {
                    System.out.println(errorMessageLineContent);
                    return null;
                }
                // Create a new object of policy if the given data is valid
                // Add the new customer to existing customer list
                users.add(new User(parts[0], parts[1], parts[2]));
            }
            // Close the file scanner after reading the file
            fileScanner.close();
        } catch (Exception ex) {
            System.out.println(errorMessageData);
            return null;
        }
        return users;
    }

    /**
     * Check if user given policy file has valid data
     *
     * @param file
     * @return policies
     */
    public ArrayList<Policy> getPolicyData(File file) {
        ArrayList<Policy> policies = new ArrayList<Policy>();
        String errorMessageData = "Data in input file is not correct. Try again.";
        String errorMessageLineContent = "Each line must have 2 Strings. Try again.";
        try {
            fileScanner = new Scanner(file);
            // Loop each line of the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                // Split each line using space
                String[] parts = line.split(",");

                // Each line should have two parts: name, description
                if (parts.length != 2) {
                    System.out.println(errorMessageLineContent);
                    return null;
                }
                // Create a new object of policy if the given data is valid
                // Add the new customer to existing customer list
                policies.add(new Policy(parts[0], parts[1]));
            }
            // Close the file scanner after reading the file
            fileScanner.close();
        } catch (Exception ex) {
            System.out.println(errorMessageData);
            return null;
        }
        return policies;
    }

    public void showAllUserDashboard(InteractiveGDPRDashboard gdprDashboard) {
        String userFormat = "| %-36s | %-48s |%n";
        String policyFormat = "| %-36s | %-30s | %-15s |%n";

        System.out.println("===========================================================");
        for (UUID userId : gdprDashboard.userService.getUsers()) {
            System.out.format("+--------------------------------------+--------------------------------------------------+%n");
            System.out.format("| User ID                              | User Name                                        |%n");
            System.out.format("+--------------------------------------+--------------------------------------------------+%n");
            System.out.printf(userFormat, userId, gdprDashboard.userService.getUserName(userId));
            System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");
            System.out.format("| Policy ID                            | Policy Name                    | User Consent    |%n");
            System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");

            for (PolicyMapper policyMapper : gdprDashboard.userDashboardService.getPolicyMappersByUserId(userId, gdprDashboard.policyService)) {
                System.out.printf(policyFormat, policyMapper.getPolicyId(),
                        gdprDashboard.policyService.getPolicyName(policyMapper.getPolicyId()),
                        policyMapper.getUserChoice());
                System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");
            }
            System.out.println();
        }
        System.out.println("===========================================================\n");

    }

    public void showMenu() {
        System.out.println("Choose from following choices");
        System.out.println("-----------------------------\n");
        System.out.println("1 - Update an user consent");
        System.out.println("2 - View an user's dashboard");
        System.out.println("3 - Add a policy");
        System.out.println("4 - Delete a policy");
        System.out.println("5 - Quit");
        System.out.println("");
        System.out.print("Enter your choice: ");

    }

    public static void main(String[] args) {
        InteractiveGDPRDashboard gdprDashboard = new InteractiveGDPRDashboard();

        System.out.println("WELCOME TO GDPR DASHBOARD");
        System.out.println("===========================================================\n");

        File userDataFile;
        userDataFile = gdprDashboard.getUserDataFile();
        File policyDataFile;
        policyDataFile = gdprDashboard.getPolicyDataFile();
        ArrayList<User> users = gdprDashboard.getUserData(userDataFile);
        ArrayList<Policy> policies = gdprDashboard.getPolicyData(policyDataFile);
        System.out.println("Users: " + users.size());
        System.out.println("Policies: " + policies.size());

        UUID facebookGraphApiID = null;

        for (Policy policy : policies) {
            UUID policyId = gdprDashboard.policyService.createPolicy(policy.getName(), policy.getDescription());
            facebookGraphApiID = policyId;
        }

        UUID arnabId = null;
        for (User user : users) {
            UUID userId = gdprDashboard.userService.createUser(user.getName(), user.getEmail(), user.getCountry());
            UUID dashboardId = gdprDashboard.userDashboardService.createUserDashboard(userId, gdprDashboard.policyService);
            arnabId = userId;
        }
        gdprDashboard.showAllUserDashboard(gdprDashboard);
        int userChoice;
        while (true) {
            gdprDashboard.showMenu();
            userChoice = gdprDashboard.sc.nextInt();
            boolean programExit = false;
            switch (userChoice) {
                case 1:
                    // Update one user consent
                    UUID userId;
                    UUID policyId;
                    boolean consentValue;
                    System.out.println("Enter User ID: ");
                    String userIdStr = gdprDashboard.sc.next().trim();
                    userId = CommonService.fromString(userIdStr);
                    System.out.println("Enter Policy ID: ");
                    String policyIdStr = gdprDashboard.sc.next().trim();
                    policyId = CommonService.fromString(policyIdStr);
                    System.out.println("Enter Consent(1 for yes, 2 for no): ");
                    int consent = gdprDashboard.sc.nextInt();
                    
                    consentValue = (consent == 1) ? true : false;
                    System.out.println(userIdStr + "\n" + userId + "\n" + arnabId);
                    System.out.println(policyIdStr + "\n" + policyId + "\n" + facebookGraphApiID);
                    if (userId.equals(arnabId)) {
                        System.out.println("user id mached");
                        
                    }
                    if (policyId.equals(facebookGraphApiID)) {
                        System.out.println("policy id mached");
                    }
                    if (consentValue) {
                        gdprDashboard.userDashboardService.userComplyToPolicy(userId, policyId, gdprDashboard.policyService);
                    } else {
                        gdprDashboard.userDashboardService.userNotComplyToPolicy(userId, policyId, gdprDashboard.policyService);
                    }
                    //gdprDashboard.userDashboardService.userNotComplyToPolicy(arnabId, facebookGraphApiID, gdprDashboard.policyService);
                    gdprDashboard.showAllUserDashboard(gdprDashboard);
                    break;
                case 2:
                    // Perform "encrypt number" case.
                    break;
                case 3:
                    // Perform "decrypt number" case.
                    break;
                case 4:
                    // Perform "quit" case.
                    break;
                case 5:
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

        // delcaring policies
//        UUID googleAnalyticsID = policyService.createPolicy("Google Analytics", "User data is being sent to Google Analytics");
//        UUID facebookGraphApiID = policyService.createPolicy("Facebook Graph API", "User data is being sent to Facebook Graph API");
        // delcaring users
//        UUID shovonId = userService.createUser("Ahmedur Rahman Shovon", "arshovon@cefalo.com", "Bangladesh");
//        UUID arnabId = userService.createUser("Arnab Kumar Shil", "arnab@cefalo.com", "Bangladesh");
        // delcaring dashboards
//        UUID shovonDashboardId = userDashboardService.createUserDashboard(shovonId, policyService);
//        UUID arnabDashboardId = userDashboardService.createUserDashboard(arnabId, policyService);
//        System.out.println("==================updating policy for arnab==================");
//        userDashboardService.userNotComplyToPolicy(arnabId, facebookGraphApiID, policyService);
//        System.out.println("===========================================================");
//        for (UUID userId : userService.getUsers()) {
//            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
//            userDashboardService.showPolicyMappersByUserId(userId, policyService);
//        }
//        System.out.println("===========================================================\n");
//
//        System.out.println("====================creating new policy===================");
//        UUID netsPaymentApiID = policyService.createPolicy("Nets Payment API", "User data is being sent to Nets Payment API");
//        System.out.println("===========================================================");
//        for (UUID userId : userService.getUsers()) {
//            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
//            userDashboardService.showPolicyMappersByUserId(userId, policyService);
//        }
//        System.out.println("===========================================================\n");
//
//        System.out.println("======================remove ga policy=====================");
//        policyService.deletePolicy(googleAnalyticsID);
//        System.out.println("===========================================================");
//        for (UUID userId : userService.getUsers()) {
//            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
//            userDashboardService.showPolicyMappersByUserId(userId, policyService);
//        }
//        System.out.println("===========================================================\n");
//
//        UUID musaId = userService.createUser("Ahmed Musa", "musa@cefalo.com", "Bangladesh");
//
//        // delcaring dashboards
//        UUID musaDashboardId = userDashboardService.createUserDashboard(musaId, policyService);
//        System.out.println("======================add new user=====================");
//        policyService.deletePolicy(googleAnalyticsID);
//        System.out.println("===========================================================");
//        for (UUID userId : userService.getUsers()) {
//            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
//            userDashboardService.showPolicyMappersByUserId(userId, policyService);
//        }
//        System.out.println("===========================================================\n");
    }

}
