package gdprdashboard;

import java.util.ArrayList;

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
        UUID shovonDashboardId = userDashboardService.createUserDashboard(shovon, policyService);
        UUID arnabDashboardId = userDashboardService.createUserDashboard(arnab, policyService);

        
        System.out.println("==============showing default policies for users===========");
        System.out.println("===========================================================");
        for (UUID userId : userService.getUsers()) {
            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
            userDashboardService.showPolicyMappersByUserId(userId, policyService);
        }
        System.out.println("===========================================================\n");
        
        System.out.println("==================updating policy for arnab==================");
        userDashboardService.userNotComplyToPolicy(arnabId, googleAnalyticsID, policyService);
        System.out.println("===========================================================");
        for (UUID userId : userService.getUsers()) {
            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
            userDashboardService.showPolicyMappersByUserId(userId, policyService);
        }
        System.out.println("===========================================================\n");
        
        System.out.println("====================creating new policy===================");
        UUID netsPaymentApiID = policyService.createPolicy("Nets Payment API", "User data is being sent to Nets Payment API");
        System.out.println("===========================================================");
        for (UUID userId : userService.getUsers()) {
            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
            userDashboardService.showPolicyMappersByUserId(userId, policyService);
        }
        System.out.println("===========================================================\n");
        
        System.out.println("======================remove ga policy=====================");
        policyService.deletePolicy(googleAnalyticsID);
        System.out.println("===========================================================");
        for (UUID userId : userService.getUsers()) {
            System.out.println("Showing dashboard for user:" + userService.getUserName(userId));
            userDashboardService.showPolicyMappersByUserId(userId, policyService);
        }
        System.out.println("===========================================================\n");
        
    }
    
}
