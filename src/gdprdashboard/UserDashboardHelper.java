package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class UserDashboardHelper {

    private UserDashboard userDashboard;
    private PolicyService policyService;

    public UserDashboardHelper(UserDashboard userDashboard) {
        this.userDashboard = userDashboard;
        this.policyService = new PolicyService();
    }

    private void syncPolicyMappers() {
        ArrayList<Policy> policies = this.policyService.getPolicyPool();
        for (Policy policy : policies) {
            boolean existingPolicy = false;
            for (PolicyMapper policyMapper : this.userDashboard.getPolicyMappers()) {
                if (policyMapper.getPolicyId() == policy.getId()) {
                    existingPolicy = true;
                }
            }
            if (existingPolicy == false) {
                this.userDashboard.addPolicyMapper(new PolicyMapper(policy.getId(), policy.getDefaultValue()));
            }
        }
        for (PolicyMapper policyMapper : new ArrayList<PolicyMapper>(this.userDashboard.getPolicyMappers())) {
            boolean foundPolicy = false;
            for (Policy policy : policies) {
                if (policyMapper.getPolicyId() == policy.getId()) {
                    foundPolicy = true;
                }
            }
            if (foundPolicy == false) {
                this.userDashboard.removePolicyMapper(policyMapper);
            }
        }
    }

    public void updatePolicyMappers(UUID policyId, boolean userChoice) {
        syncPolicyMappers();

        for (PolicyMapper policyMapper : this.userDashboard.getPolicyMappers()) {
            if (policyMapper.getPolicyId() == policyId) {
                policyMapper.setUserChoice(userChoice);
            }
        }
    }

    public UUID getUserId() {
        return this.userDashboard.getUserId();
    }

    public ArrayList<PolicyMapper> getPolicyMappers() {
        syncPolicyMappers();
        return this.userDashboard.getPolicyMappers();
    }

}
