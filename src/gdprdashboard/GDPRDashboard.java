package gdprdashboard;

import java.util.ArrayList;

public class GDPRDashboard {
    
    public static void main(String[] args) {
        ArrayList<Policy> policies = new ArrayList<Policy>();
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<UserDashboard> userDashboards = new ArrayList<UserDashboard>();
        
        Policy googleAnalytics = new Policy("Google Analytics", "User data is being sent to Google Analytics");
        Policy facebookGraphApi = new Policy("Facebook Graph API", "User data is being sent to Facebook Graph API");
        
        policies.add(googleAnalytics);
        policies.add(facebookGraphApi);
        
        User shovon = new User("Ahmedur Rahman Shovon", "arshovon@cefalo.com", "Bangladesh");
        User arnab = new User("Arnab Kumar Shil", "arnab@cefalo.com", "Bangladesh");
        
        users.add(shovon);
        users.add(arnab);
        
        UserDashboard shovonDashboard = new UserDashboard(shovon.getId(), policies);
        UserDashboard arnabDashboard = new UserDashboard(arnab.getId(), policies);
        
        userDashboards.add(shovonDashboard);
        userDashboards.add(arnabDashboard);
        
        System.out.println("===========================================================");
        for (UserDashboard userDashboard : userDashboards) {
            System.out.println("Showing dashboard for user:" + userDashboard.getUserId());
            for (PolicyMapper policyMapper : userDashboard.getPolicyMappers(policies)) {
                System.out.println("Policy " + policyMapper.getPolicyId() + "-->" + policyMapper.getUserChoice());
            }
        }
        System.out.println("===========================================================\n");
        
        userDashboards.get(1).updatePolicyMappers(policies, facebookGraphApi.getId(), false);
        
        System.out.println("===========================================================");
        for (UserDashboard userDashboard : userDashboards) {
            System.out.println("Showing dashboard for user:" + userDashboard.getUserId());
            for (PolicyMapper policyMapper : userDashboard.getPolicyMappers(policies)) {
                System.out.println("Policy " + policyMapper.getPolicyId() + "-->" + policyMapper.getUserChoice());
            }
        }
        System.out.println("===========================================================\n");
        
        Policy netsPaymentApi = new Policy("Nets Payment API", "User data is being sent to Nets Payment API");
        policies.add(netsPaymentApi);
        
        System.out.println("===========================================================");
        for (UserDashboard userDashboard : userDashboards) {
            System.out.println("Showing dashboard for user:" + userDashboard.getUserId());
            for (PolicyMapper policyMapper : userDashboard.getPolicyMappers(policies)) {
                System.out.println("Policy " + policyMapper.getPolicyId() + "-->" + policyMapper.getUserChoice());
            }
        }
        System.out.println("===========================================================\n");
        
        policies.remove(googleAnalytics);
        
        System.out.println("===========================================================");
        for (UserDashboard userDashboard : userDashboards) {
            System.out.println("Showing dashboard for user:" + userDashboard.getUserId());
            for (PolicyMapper policyMapper : userDashboard.getPolicyMappers(policies)) {
                System.out.println("Policy " + policyMapper.getPolicyId() + "-->" + policyMapper.getUserChoice());
            }
        }
        System.out.println("===========================================================\n");
        
    }
    
}
