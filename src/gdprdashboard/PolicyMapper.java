package gdprdashboard;

import java.util.UUID;

public class PolicyMapper {

    // Todo: how to store the id rather than the policy itself? PolicyPool?
    private UUID id;
    private UUID policyId;
    private boolean userChoice;

    public PolicyMapper(UUID policyId, boolean userChoice) {
        this.policyId = policyId;
        this.userChoice = userChoice;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPolicyId() {
        return policyId;
    }

    public boolean getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(boolean userChoice) {
        this.userChoice = userChoice;
    }

}
