package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class UserDashboard extends Core{
    private UUID userId;
    private ArrayList<PolicyMapper> policyMappers;

    public UserDashboard(UUID userId, ArrayList<Policy> policies) {
        this.userId = userId;
        this.policyMappers = new ArrayList<PolicyMapper>();
        for (Policy policy : policies) {
            PolicyMapper policyMapper = new PolicyMapper(policy.getId(), policy.getDefaultValue());
            this.policyMappers.add(policyMapper);
        }
    }

    private void syncPolicyMappers(ArrayList<Policy> policies) {
        // Add new policy to policy mapper list with policies default value
        for (Policy policy : policies) {
            boolean existingPolicy = false;
            for (PolicyMapper policyMapper : this.policyMappers) {
                if (policyMapper.getPolicyId() == policy.getId()) {
                    existingPolicy = true;
                }
            }
            if (existingPolicy == false) {
                this.policyMappers.add(new PolicyMapper(policy.getId(), policy.getDefaultValue()));
            }
        }
        // Remove deleted policie from policy mapper list
        for (PolicyMapper policyMapper : new ArrayList<PolicyMapper>(policyMappers)) {
            boolean foundPolicy = false;
            for (Policy policy : policies) {
                if (policyMapper.getPolicyId() == policy.getId()) {
                    foundPolicy = true;
                }
            }
            if (foundPolicy == false) {
                policyMappers.remove(policyMapper);
            }
        }
    }

    public void updatePolicyMappers(ArrayList<Policy> policies, UUID policyId, boolean userChoice) {
        syncPolicyMappers(policies);
        for (PolicyMapper policyMapper : this.policyMappers) {
            if (policyMapper.getPolicyId() == policyId) {
                policyMapper.setUserChoice(userChoice);
            }
        }
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public ArrayList<PolicyMapper> getPolicyMappers(ArrayList<Policy> policies) {
        syncPolicyMappers(policies);
        return policyMappers;
    }

}
