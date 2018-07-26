package models;

import java.util.ArrayList;
import java.util.UUID;

public class UserDashboard extends Core {

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

    public UUID getUserId() {
        return this.userId;
    }

    public void addPolicyMapper(PolicyMapper policyMapper) {
        this.policyMappers.add(policyMapper);
    }

    public void removePolicyMapper(PolicyMapper policyMapper) {
        this.policyMappers.remove(policyMapper);
    }

    public ArrayList<PolicyMapper> getPolicyMappers() {
        return this.policyMappers;
    }
}
