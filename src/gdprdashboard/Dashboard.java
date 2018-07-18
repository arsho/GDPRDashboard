package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class Dashboard {

    // Todo: how to store the user id rather than the user itself? UserPool?
    private UUID id;
    private User user;
    private ArrayList<PolicyMapper> policyMapperList;

    public Dashboard(User user) {
        this.id = UUID.randomUUID();
        this.user = user;
        policyMapperList = new ArrayList<PolicyMapper>();
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<PolicyMapper> getPolicyMapperList() {
        return policyMapperList;
    }

    public void addPolicyMapper(PolicyMapper policyMapper) {
        this.policyMapperList.add(policyMapper);
    }

    public boolean removePolicyMapperById(UUID policyMapperId) {
        for (PolicyMapper policyMapper : this.policyMapperList) {
            if (policyMapper.getId() == policyMapperId) {
                this.policyMapperList.remove(policyMapper);
                return true;
            }
        }
        return false;
    }

    // Todo: Check if the policyMapperList is updated
    public boolean updatePolicyMapperById(UUID policyMapperId, boolean userChoice) {
        for (PolicyMapper policyMapper : this.policyMapperList) {
            if (policyMapper.getId() == policyMapperId) {
                policyMapper.setUserChoice(userChoice);
                return true;
            }
        }
        return false;
    }

}
