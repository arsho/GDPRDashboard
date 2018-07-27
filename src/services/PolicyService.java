package services;

import java.util.ArrayList;
import java.util.UUID;
import storages.PolicyStorage;
import models.Policy;

public class PolicyService {

    private PolicyStorage policyPool;

    public PolicyService() {
        this.policyPool = PolicyStorage.getInstance();
    }

    public void addPolicy(Policy policy) {
        this.policyPool.addData(policy);
    }

    public UUID createPolicy(String name, String description) {
        Policy policy = new Policy(name, description);
        this.addPolicy(policy);
        return policy.getId();
    }

    public void deletePolicy(UUID policyId) {
        this.policyPool.removeData(this.getPolicy(policyId));
    }

    private Policy getPolicy(UUID policyId) {
        Policy nPolicy = null;
        for (Policy policy : this.policyPool.getData()) {
            if (policy.getId() == policyId) {
                nPolicy = policy;
            }
        }
        return nPolicy;
    }

    public String getPolicyName(UUID policyId) {
        return getPolicy(policyId).getName(); // toDo: Exp handling
    }

    public void updatePolicyName(UUID policyId, String name) {
        this.getPolicy(policyId).setName(name);
    }

    public void updatePolicyDescription(UUID policyId, String description) {
        this.getPolicy(policyId).setDescription(description);
    }

    public ArrayList<Policy> getPolicyPool() {
        return this.policyPool.getData();
    }

    public ArrayList<UUID> getPolicyIDPool() {
        ArrayList<UUID> policyIdList = new ArrayList<UUID>();
        for (Policy policy : this.getPolicyPool()) {
            policyIdList.add(policy.getId());
        }
        return policyIdList;
    }
}
