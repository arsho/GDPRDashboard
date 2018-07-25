package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class UserDashboardService implements ServiceInterface{
    private ArrayList<UserDashboard> userDashboards;

    public UserDashboardService() {
        this.userDashboards = new ArrayList<UserDashboard>();
    }

    public UserDashboard getUserDashboard(UUID userId){
        for (UserDashboard dashboard : this.userDashboards) {
            UserDashboard userDashboard = null;
            if (dashboard.getUserId() == userId) {
                userDashboard = dashboard;
            }
        }
        return userDashboard;
    }

    public UserDashboard getUserDashboardById(UUID dashId){
        for (UserDashboard dashboard : this.userDashboards) {
            UserDashboard userDashboard = null;
            if (dashboard.getId() == dashId) {
                userDashboard = dashboard;
            }
        }
        return userDashboard;
    }

    public UUID createUserDashboard(UUID userId, PolicyService policyService){
        UserDashboard userDashboard = new UserDashboard(userId, policyService.getPolicyPool());
        this.userDashboards.add(userDashboard);
        return userDashboard.getId();
    }

    public void deleteUserDashboard(UUID dashboardId){
        this.userDashboards.remove(this.getUserDashboardById(dashboardId));
    }

    public void updatePolicyForUser(UUID userId, UUID policyId, boolean userChoice, PolicyService policyService){
        UserDashboard userDashboard = this.getUserDashboard(userId);
        UserDashboardHelper helper = new UserDashboardHelper(userDashboard, policyService);
        helper.updatePolicyMappers(policyId, userChoice);
    }

    public ArrayList<PolicyMapper> getPolicyMappersByUserId(UUID userId, PolicyService policyService){
        UserDashboard userDashboard = this.getUserDashboard(userId);
        UserDashboardHelper helper = new UserDashboardHelper(userDashboard, policyService);
        return helper.getPolicyMappers();
    }

    public void showPolicyMappersByUserId(UUID userId, PolicyService policyService){
        for (PolicyMapper policyMapper: this.getPolicyMappersByUserId(userId, policyService)){
            System.out.println("Policy Name" + policyService.getPolicyName(policyMapper.getPolicyId()));
            System.out.println("User Choice" + policyMapper.getUserChoice());
        }
    }

    public ArrayList<UUID> getDashboards(){
        ArrayList<UUID> dashIdList = new ArrayyList<UUID>();
        for (UserDashboard dash : this.userDashboards) {
            dashIdList.add(dash.getId());
        }
        return dashIdList;
    }
}
