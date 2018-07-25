package gdprdashboard;

import java.util.UUID;

public class PolicyService implements ServiceInterface {
    private ArrayList<Policy> policyPool;

    public PolicyService() {
        this.policyPool = new ArrayList<Policy>();
    }

    public void addPolicy(Policy policy){
        this.policyPool.add(policy);
    }

    public UUID createPolicy(String name, String description){
        Policy policy = new Policy(name, description);
        this.addPolicy(policy);
        return policy.getId();
    }

    public void deletePolicy(UUID policyId){
        this.policyPool.remove(this.getPolicy(policyId));
    }

    private Policy getPolicy(UUID policyId){
        Policy nPolicy = null;
        for (Policy policy : this.policyPool) {
            if (policy.getId() == policyId) {
                nPolicy = policy;
            }
        }
        return nPolicy;
    }

    public String getPolicyName(UUID policyId){
        return getPolicy(policyId).getName(); // toDo: Exp handling
    }

    public void updatePolicyName(UUID policyId, String name){
        this.getPolicy(polidyId).setName(name);
    }

    public void updatePolicyDescription(UUID policyId, String description){
        this.getPolicy(polidyId).setDescription(description);
    }

    public ArrayList<Policy> getPolicyPool(){
        return this.policyPool;
    }

    public ArrayList<UUID> getPolicyIDPool(){
        ArrayList<UUID> policyIdList = new ArrayyList<UUID>();
        for (Policy policy : this.policyPool) {
            policyIdList.add(policy.getId());
        }
        return policyIdList;
    }
}