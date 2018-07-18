package gdprdashboard;

import java.util.UUID;

public class PolicyMapper {

    // Todo: how to store the id rather than the policy itself? PolicyPool?
    private UUID id;
    private Policy policy;
    private boolean userChoice;

    public PolicyMapper(Policy policy, boolean userChoice) {
        this.policy = policy;
        this.userChoice = userChoice;
    }

    public UUID getId() {
        return id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public boolean getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(boolean userChoice) {
        this.userChoice = userChoice;
    }

}
