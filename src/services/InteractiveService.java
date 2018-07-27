package services;

import java.util.Scanner;
import java.util.UUID;
import models.PolicyMapper;
import enums.UserMenuEnum;

public class InteractiveService {

    Scanner sc = new Scanner(System.in);
    private UserService userService;
    private PolicyService policyService;
    private UserDashboardService userDashboardService;

    public InteractiveService() {
        this.policyService = new PolicyService();
        this.userService = new UserService();
        this.userDashboardService = new UserDashboardService();
    }

    public String userNextLineInput(String promptMsg) {
        System.out.print(promptMsg);
        while (true) {
            try {
                String newLineValue = "";
                newLineValue = this.sc.nextLine().trim();
                if (newLineValue.length() == 0) {
                    //known java bug
                    newLineValue = this.sc.nextLine().trim();
                }
                return newLineValue;
            } catch (Exception ex) {
                System.out.println("Invalid input, please enter again");
            }
        }
    }

    public String userNextInput(String promptMsg) {
        System.out.print(promptMsg);
        while (true) {
            try {
                return this.sc.next().trim();
            } catch (Exception ex) {
                System.out.println("Invalid input, please enter again");
            }
        }
    }

    public int nextIntegerInput(String promptMsg) {
        System.out.print(promptMsg);
        while (true) {
            try {
                return this.sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Invalid input, please enter again");
            }
        }
    }

    public void deleteUserInteractive() {
        UUID deleteUserId;
        String deleteUserIdStr = this.userNextLineInput("Enter User ID: ");
        deleteUserId = CommonService.fromString(deleteUserIdStr);
        try {
            this.userService.deleteUser(deleteUserId);
        } catch (Exception e) {
            System.err.println("User not found");
        }

        this.showAllUserDashboard();
    }

    public void addUserInteractive() {
        String userName,
                userEmail,
                userCountry;
        userName = this.userNextLineInput("Enter user name: ");
        userEmail = this.userNextLineInput("Enter user email: ");
        userCountry = this.userNextLineInput("Enter user country: ");
        UUID newUserId = this.userService.createUser(userName, userEmail, userCountry);
        this.userDashboardService.createUserDashboard(newUserId);
        this.showAllUserDashboard();
    }

    public void deleteSinglePolicyInteractive() {
        UUID deletePolicyId;
        String deletePolicyIdStr = this.userNextLineInput("Enter Policy ID: ");
        deletePolicyId = CommonService.fromString(deletePolicyIdStr);
        if (deletePolicyId == null) {
            System.err.println("invalid input");
            return;
        };
        try {
            this.policyService.deletePolicy(deletePolicyId);
            this.showAllUserDashboard();
        } catch (Exception exp) {
            System.err.println("Policy not found");
        }

    }

    public void addSinglePolicyInteractive() {
        String policyName,
                policyDescription;
        policyName = this.userNextLineInput("Enter policy name: ");
        policyDescription = this.userNextLineInput("Enter policy description: ");
        this.policyService.createPolicy(policyName, policyDescription);
        this.showAllUserDashboard();
    }

    public void showSingleUserDashboardInteractive() {
        UUID searchedUserId;
        String searchedUserIdStr = this.userNextLineInput("Enter User ID: ");
        searchedUserId = CommonService.fromString(searchedUserIdStr);
        if (searchedUserId == null) {
            System.err.println("Invalid input");
            return;
        }
        try {
            this.showSingleUserDashboard(searchedUserId);
        } catch (Exception exp) {
            System.err.println("User not found");
        }
    }

    public void updateUserConscentInteractive() {
        UUID userId;
        UUID policyId;
        boolean consentValue;
        String userIdStr = this.userNextLineInput("Enter User ID: ");
        userId = CommonService.fromString(userIdStr);
        if (userId == null) {
            System.err.println("Invalid input");
            return;
        }
        String policyIdStr = this.userNextLineInput("Enter Policy ID: ");
        policyId = CommonService.fromString(policyIdStr);
        if (policyId == null) {
            System.err.println("Invalid input");
            return;
        }
        int consent = this.nextIntegerInput("Enter Consent(1 for yes, 2 for no): ");
        consentValue = (consent == 1) ? true : false;
        if (consentValue) {
            try {
                this.userDashboardService.userComplyToPolicy(userId, policyId);
            } catch (Exception exp) {
                System.err.println("Unable to process request");
            }
        } else {
            try {
                this.userDashboardService.userNotComplyToPolicy(userId, policyId);
            } catch (Exception exp) {
                System.err.println("Unable to process request");
            }
        }
        this.showAllUserDashboard();
    }

    public void showSingleUserDashboard(UUID userId) {
        String userFormat = "| %-36s | %-48s |%n";
        String policyFormat = "| %-36s | %-30s | %-15s |%n";

        System.out.format("+======================================+==================================================+%n");
        System.out.format("| User ID                              | User Name                                        |%n");
        System.out.format("+======================================+==================================================+%n");
        //System.out.format("+--------------------------------------+--------------------------------------------------+%n");
        System.out.printf(userFormat, userId, this.userService.getUserName(userId));
        System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");
        System.out.format("| Policy ID                            | Policy Name                    | User Consent    |%n");
        System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");

        for (PolicyMapper policyMapper : this.userDashboardService.getPolicyMappersByUserId(userId)) {
            System.out.printf(policyFormat, policyMapper.getPolicyId(),
                    this.policyService.getPolicyName(policyMapper.getPolicyId()),
                    policyMapper.getUserChoice());
            System.out.format("+--------------------------------------+--------------------------------+-----------------+%n");
        }
        System.out.println();
    }

    public void showGDPRDashboardHeader() {
        System.out.format("+=========================================================================================+%n");
        System.out.format("|                             WELCOME TO GDPR DASHBOARD                                   |%n");
        System.out.format("+=========================================================================================+%n");
        for (UUID userId : this.userService.getUsers()) {
            showSingleUserDashboard(userId);
        }
        System.out.println("\n");
    }

    public void showAllUserDashboard() {
        System.out.format("+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+%n");
        System.out.format("|                                  GDPR DASHBOARDS                                        |%n");
        System.out.format("+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+%n");
        for (UUID userId : this.userService.getUsers()) {
            showSingleUserDashboard(userId);
        }
        System.out.println("\n");
    }

    public void showMenu() {
        String menuFormat = " %2d - %-48s %n";
        System.out.println("Choose from following choices");
        System.out.println("-----------------------------");
        for (UserMenuEnum item : UserMenuEnum.values()) {
            System.out.printf(menuFormat, item.getIndex(), item.getDescription());
        }
        System.out.println("");
    }
}
