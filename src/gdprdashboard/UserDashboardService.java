package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class UserDashboardService implements ServiceInterface {

    private UserDashboardStorage userDashboards;
    private PolicyService policyService;


    public UserDashboardService() {
        UserDashboardStorage storageInstance = UserDashboardStorage.getInstance(); 
        this.userDashboards = storageInstance;
        this.policyService = new PolicyService();
    }

    public UserDashboard getUserDashboard(UUID userId) {
        UserDashboard userDashboard = null;
        for (UserDashboard dashboard : this.getDashboardInstances()) {
            if (dashboard.getUserId().equals(userId)) {
                userDashboard = dashboard;
            }
        }
        return userDashboard;
    }

    public UserDashboard getUserDashboardById(UUID dashId) {
        UserDashboard userDashboard = null;
        for (UserDashboard dashboard : this.getDashboardInstances()) {
            if (dashboard.getId().equals(dashId)) {
                userDashboard = dashboard;
            }
        }
        return userDashboard;
    }

    public UUID createUserDashboard(UUID userId) {
        UserDashboard userDashboard = new UserDashboard(userId, this.policyService.getPolicyPool());
        this.userDashboards.addData(userDashboard);
        return userDashboard.getId();
    }

    public void deleteUserDashboard(UUID dashboardId) {
        this.userDashboards.removeData(this.getUserDashboardById(dashboardId));
    }

    private void updatePolicyForUser(UUID userId, UUID policyId, boolean userChoice) {
        UserDashboard userDashboard = this.getUserDashboard(userId);
        UserDashboardHelper helper = new UserDashboardHelper(userDashboard);
        helper.updatePolicyMappers(policyId, userChoice);
    }

    public void userComplyToPolicy(UUID userId, UUID policyId) {
        this.updatePolicyForUser(userId, policyId, UserChoiceEnum.COMPLY.value());
    }

    public void userNotComplyToPolicy(UUID userId, UUID policyId) {
        this.updatePolicyForUser(userId, policyId, UserChoiceEnum.OPT_OUT.value());
    }

    public ArrayList<PolicyMapper> getPolicyMappersByUserId(UUID userId) {
        UserDashboard userDashboard = this.getUserDashboard(userId);
        UserDashboardHelper helper = new UserDashboardHelper(userDashboard);
        return helper.getPolicyMappers();
    }
    
    public void showPolicyMappersByUserId(UUID userId) {
        for (PolicyMapper policyMapper : this.getPolicyMappersByUserId(userId)) {
            System.out.println("\tPolicy Name: " + this.policyService.getPolicyName(policyMapper.getPolicyId()));
            System.out.println("\tUser Choice: " + policyMapper.getUserChoice());
        }
    }

    public ArrayList<UUID> getDashboards() {
        ArrayList<UUID> dashIdList = new ArrayList<UUID>();
        for (UserDashboard dash : this.getDashboardInstances()) {
            dashIdList.add(dash.getId());
        }
        return dashIdList;
    }

    public ArrayList<UserDashboard> getDashboardInstances() {
        return this.userDashboards.getData();
    }
    
    public ArrayList<UUID> getUserByPolicyId(UUID policyId){
        ArrayList<UUID> userIds = new ArrayList<UUID>();
        for (UserDashboard dashboard : this.getDashboardInstances()) {
            for ( PolicyMapper policyMapper: dashboard.getPolicyMappers()){
                if (policyMapper.getPolicyId().equals(policyId)){
                    userIds.add(dashboard.getUserId());
                }
            }
        }
        return userIds;
    }
}
