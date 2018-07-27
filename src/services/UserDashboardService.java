package services;

import java.util.ArrayList;
import java.util.UUID;

import storages.UserDashboardStorage;
import models.UserDashboard;
import models.PolicyMapper;
import enums.UserConsentEnum;

public class UserDashboardService {

    private UserDashboardStorage userDashboards;
    private PolicyService policyService;
    private UserService userService;

    public UserDashboardService() {
        UserDashboardStorage storageInstance = UserDashboardStorage.getInstance();
        this.userDashboards = storageInstance;
        this.policyService = new PolicyService();
        this.userService = new UserService();
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

    public void createAllUserDashboards() {
        for (UUID userId : this.userService.getUsers()) {
            this.createUserDashboard(userId);
        }
    }

    @SuppressWarnings("empty-statement")
    public UUID createUserDashboard(UUID userId) {
        for (UserDashboard userDashboard : this.getDashboardInstances()) {
            if (userDashboard.getUserId().equals(userId)) {
                return userDashboard.getId();
            };
        };
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
        this.updatePolicyForUser(userId, policyId, UserConsentEnum.COMPLY.value());
    }

    public void userNotComplyToPolicy(UUID userId, UUID policyId) {
        this.updatePolicyForUser(userId, policyId, UserConsentEnum.OPT_OUT.value());
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
        ArrayList<UUID> dashIdList = new ArrayList<>();
        this.getDashboardInstances().forEach((dash) -> {
            dashIdList.add(dash.getId());
        });
        return dashIdList;
    }

    public ArrayList<UserDashboard> getDashboardInstances() {
        return this.userDashboards.getData();
    }

    public ArrayList<UUID> getUserByPolicyId(UUID policyId) {
        ArrayList<UUID> userIds = new ArrayList<>();
        this.getDashboardInstances().forEach((dashboard) -> {
            for (PolicyMapper policyMapper : dashboard.getPolicyMappers()) {
                if (policyMapper.getPolicyId().equals(policyId)) {
                    userIds.add(dashboard.getUserId());
                }
            }
        });
        return userIds;
    }
}
