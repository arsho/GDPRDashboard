package gdprdashboard;

public class PolicyManager {
    Policy policy;
    Boolean hasComplied;
    User user;

    PolicyManager(Policy policy, Boolean hasComplied, User user){
        this.policy=policy;
        this.hasComplied=hasComplied;
        this.user=user;
    }

    public bool isComplied(){
        return this.hasComplied;
    }

    public Policy getPolicy(){
        return this.policy;
    }

    public User getUser(){
        return this.user;
    }
}