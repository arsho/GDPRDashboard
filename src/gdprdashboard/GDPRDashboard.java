package gdprdashboard;

import java.util.UUID;

public class GDPRDashboard {

    public static void main(String[] args) {
        // delcaring services
        UserService userService = new UserService();
        PolicyService policyService = new PolicyService();
        UserDashboardService userDashboardService = new UserDashboardService();

        // delcaring policies
        UUID googleAnalyticsID = policyService.createPolicy("Google Analytics", "User data is being sent to Google Analytics");
        UUID facebookGraphApiID = policyService.createPolicy("Facebook Graph API", "User data is being sent to Facebook Graph API");

        // delcaring users
        UUID shovonId = userService.createUser("Ahmedur Rahman Shovon", "arshovon@cefalo.com", "Bangladesh");
        UUID arnabId = userService.createUser("Arnab Kumar Shil", "arnab@cefalo.com", "Bangladesh");

        // delcaring dashboards
        UUID shovonDashboardId = userDashboardService.createUserDashboard(shovonId);
        UUID arnabDashboardId = userDashboardService.createUserDashboard(arnabId);

        System.out.println("==============showing default policies for users===========");
        System.out.println("===========================================================");
        for (UUID userId : userService.getUsers()) {
            System.out.println("Showing dashboard for user: " + userService.getUserName(userId));
            userDashboardService.showPolicyMappersByUserId(userId);
        }
        System.out.println("===========================================================\n");

        System.out.println("==================updating policy for arnab==================");
        userDashboardService.userNotComplyToPolicy(arnabId, facebookGraphApiID);
        System.out.println("===========================================================");
        userService.getUsers().stream().map((userId) -> {
            System.out.println("Showing dashboard for user: " + userService.getUserName(userId));
            return userId;
        }).forEachOrdered((userId) -> {
            userDashboardService.showPolicyMappersByUserId(userId);
        });
        System.out.println("===========================================================\n");

        System.out.println("====================creating new policy===================");
        UUID netsPaymentApiID = policyService.createPolicy("Nets Payment API", "User data is being sent to Nets Payment API");
        System.out.println("===========================================================");
        userService.getUsers().stream().map((userId) -> {
            System.out.println("Showing dashboard for user: " + userService.getUserName(userId));
            return userId;
        }).forEachOrdered((userId) -> {
            userDashboardService.showPolicyMappersByUserId(userId);
        });
        System.out.println("===========================================================\n");

        System.out.println("======================remove ga policy=====================");
        policyService.deletePolicy(googleAnalyticsID);
        System.out.println("===========================================================");
        userService.getUsers().stream().map((userId) -> {
            System.out.println("Showing dashboard for user: " + userService.getUserName(userId));
            return userId;
        }).forEachOrdered((userId) -> {
            userDashboardService.showPolicyMappersByUserId(userId);
        });
        System.out.println("===========================================================\n");

        UUID musaId = userService.createUser("Abu Saleh Musa", "musa@cefalo.com", "Bangladesh");
        UUID musaDashboardId = userDashboardService.createUserDashboard(musaId);
        System.out.println("======================add new user=========================");
        policyService.deletePolicy(googleAnalyticsID);
        System.out.println("===========================================================");
        userService.getUsers().stream().map((userId) -> {
            System.out.println("Showing dashboard for user: " + userService.getUserName(userId));
            return userId;
        }).forEachOrdered((userId) -> {
            userDashboardService.showPolicyMappersByUserId(userId);
        });
        System.out.println("===========================================================\n");

        System.out.println("====================get users from a policy==================");
        System.out.println("===========================================================");
        System.out.println("Showing user ids for policy: " + facebookGraphApiID + "\n");
        userDashboardService.getUserByPolicyId(facebookGraphApiID).forEach((userId) -> {
            System.out.println("user id"+ userId + ", name: " + userService.getUserName(userId));
        });
        System.out.println("===========================================================\n");

    }

}
