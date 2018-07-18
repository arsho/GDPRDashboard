package gdprdashboard;

import java.util.ArrayList;

public class GDPRDashboard {

    public static void main(String[] args) {
        Policy googleAnalytics = new Policy("Google Analytics", "User data is being sent to Google Analytics");
        Policy facebookGraphApi = new Policy("Facebook Graph API", "User data is being sent to Facebook Graph API");

        User shovon = new User("Ahmedur Rahman Shovon", "arshovon@cefalo.com", "Bangladesh");
        User arnab = new User("Arnab Kumar Shil", "arnab@cefalo.com", "Bangladesh");

        PolicyMapper shovonGA = new PolicyMapper(googleAnalytics, true);
        PolicyMapper shovonFB = new PolicyMapper(facebookGraphApi, false);

        PolicyMapper arnabGA = new PolicyMapper(googleAnalytics, false);
        PolicyMapper arnabFB = new PolicyMapper(facebookGraphApi, true);

        Dashboard shovonDashboard = new Dashboard(shovon);
        shovonDashboard.addPolicyMapper(shovonGA);
        shovonDashboard.addPolicyMapper(shovonFB);

        Dashboard arnabDashboard = new Dashboard(arnab);
        arnabDashboard.addPolicyMapper(arnabGA);
        arnabDashboard.addPolicyMapper(arnabFB);

        ArrayList<PolicyMapper> shovonPolicies = shovonDashboard.getPolicyMapperList();
        System.out.println(shovon.getName() + "'s GDPR consents:");
        for (PolicyMapper policyMapper : shovonPolicies) {
            String policyName = policyMapper.getPolicy().getName();
            boolean userChoice = policyMapper.getUserChoice();
            System.out.println(policyName + "-->" + userChoice);
        }

        ArrayList<PolicyMapper> arnabPolicies = arnabDashboard.getPolicyMapperList();
        System.out.println(arnab.getName() + "'s GDPR consents:");
        for (PolicyMapper policyMapper : arnabPolicies) {
            String policyName = policyMapper.getPolicy().getName();
            boolean userChoice = policyMapper.getUserChoice();
            System.out.println(policyName + "-->" + userChoice);
        }

    }

}
