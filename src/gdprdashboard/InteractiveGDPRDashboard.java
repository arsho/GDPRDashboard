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

    public void showSingleUserDashboard(InteractiveGDPRDashboard gdprDashboard, UUID userId) {
        String userFormat = "| %-36s | %-48s |%n";
        String policyFormat = "| %-36s | %-30s | %-15s |%n";

        System.out.format("+--------------------------------------+--------------------------------------------------+%n");
        System.out.format("| User ID                              | User Name                                        |%n");
        System.out.format("+--------------------------------------+--------------------------------------------------+%n");
        System.out.printf(userFormat, userId, gdprDashboard.userService.getUserName(userId));
        System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");
        System.out.format("| Policy ID                            | Policy Name                    | User Consent    |%n");
        System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");

        for (PolicyMapper policyMapper : gdprDashboard.userDashboardService.getPolicyMappersByUserId(userId)) {
            System.out.printf(policyFormat, policyMapper.getPolicyId(),
                    gdprDashboard.policyService.getPolicyName(policyMapper.getPolicyId()),
                    policyMapper.getUserChoice());
            System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");
        }
        System.out.println();
    }

    public void showAllUserDashboard(InteractiveGDPRDashboard gdprDashboard) {
        String userFormat = "| %-36s | %-48s |%n";
        String policyFormat = "| %-36s | %-30s | %-15s |%n";

        for (UUID userId : gdprDashboard.userService.getUsers()) {
            showSingleUserDashboard(gdprDashboard, userId);
        }
        System.out.println("\n");
    }

    public void showMenu() {
        System.out.println("Choose from following choices");
        System.out.println("-----------------------------\n");
        System.out.println("1 - View all users' dashboard");
        System.out.println("2 - Update an user consent");
        System.out.println("3 - View an user's dashboard");
        System.out.println("4 - Add new policy");
        System.out.println("5 - Delete policy");
        System.out.println("6 - Add new user");
        System.out.println("7 - Delete user");
        System.out.println("100 - Quit");
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

        for (Policy policy : policies) {
            UUID policyId = gdprDashboard.policyService.createPolicy(policy.getName(), policy.getDescription());
        }

        for (User user : users) {
            UUID userId = gdprDashboard.userService.createUser(user.getName(), user.getEmail(), user.getCountry());
            UUID dashboardId = gdprDashboard.userDashboardService.createUserDashboard(userId);
        }
        int userChoice;
        gdprDashboard.showAllUserDashboard(gdprDashboard);
        while (true) {
            gdprDashboard.showMenu();
            userChoice = gdprDashboard.sc.nextInt();
            boolean programExit = false;
            switch (userChoice) {
                case 1:
                    // Show all users dashboard
                    gdprDashboard.showAllUserDashboard(gdprDashboard);
                    break;
                case 2:
                    // Update one user consent
                    UUID userId;
                    UUID policyId;
                    boolean consentValue;
                    System.out.print("Enter User ID: ");
                    String userIdStr = gdprDashboard.sc.next().trim();
                    userId = CommonService.fromString(userIdStr);
                    System.out.print("Enter Policy ID: ");
                    String policyIdStr = gdprDashboard.sc.next().trim();
                    policyId = CommonService.fromString(policyIdStr);
                    System.out.print("Enter Consent(1 for yes, 2 for no): ");
                    int consent = gdprDashboard.sc.nextInt();

                    consentValue = (consent == 1) ? true : false;
                    if (consentValue) {
                        gdprDashboard.userDashboardService.userComplyToPolicy(userId, policyId);
                    } else {
                        gdprDashboard.userDashboardService.userNotComplyToPolicy(userId, policyId);
                    }
                    gdprDashboard.showAllUserDashboard(gdprDashboard);
                    break;
                case 3:
                    // Show single user dashboard
                    UUID searchedUserId;
                    System.out.print("Enter User ID: ");
                    String searchedUserIdStr = gdprDashboard.sc.next().trim();
                    searchedUserId = CommonService.fromString(searchedUserIdStr);
                    gdprDashboard.showSingleUserDashboard(gdprDashboard, searchedUserId);
                    break;
                case 4:
                    // Add a policy
                    String policyName,
                     policyDescription;
                    System.out.print("Enter policy name: ");
                    policyName = gdprDashboard.sc.nextLine().trim();
                    policyName = gdprDashboard.sc.nextLine().trim();
                    System.out.print("Enter policy description: ");
                    policyDescription = gdprDashboard.sc.nextLine().trim();
                    UUID newPolicyId = gdprDashboard.policyService.createPolicy(policyName, policyDescription);
                    gdprDashboard.showAllUserDashboard(gdprDashboard);
                    break;
                case 5:
                    // Delete a policy
                    UUID deletePolicyId;
                    System.out.print("Enter Policy ID: ");
                    String deletePolicyIdStr = gdprDashboard.sc.next().trim();
                    deletePolicyId = CommonService.fromString(deletePolicyIdStr);
                    gdprDashboard.policyService.deletePolicy(deletePolicyId);
                    gdprDashboard.showAllUserDashboard(gdprDashboard);
                    break;
                case 6:
                    // Add an user
                    String userName,
                     userEmail,
                     userCountry;
                    System.out.print("Enter user name: ");
                    userName = gdprDashboard.sc.nextLine().trim();
                    userName = gdprDashboard.sc.nextLine().trim();
                    System.out.print("Enter user email: ");
                    userEmail = gdprDashboard.sc.nextLine().trim();
                    System.out.print("Enter user country: ");
                    userCountry = gdprDashboard.sc.nextLine().trim();
                    UUID newUserId = gdprDashboard.userService.createUser(userName, userEmail, userCountry);
                    UUID newDashboardId = gdprDashboard.userDashboardService.createUserDashboard(newUserId);
                    gdprDashboard.showAllUserDashboard(gdprDashboard);
                    break;
                case 7:
                    // Delete an user
                    UUID deleteUserId;
                    System.out.print("Enter User ID: ");
                    String deleteUserIdStr = gdprDashboard.sc.next().trim();
                    deleteUserId = CommonService.fromString(deleteUserIdStr);
                    gdprDashboard.userService.deleteUser(deleteUserId);
                    gdprDashboard.showAllUserDashboard(gdprDashboard);
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
