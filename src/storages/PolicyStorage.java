package storages;

import java.util.ArrayList;
import java.util.UUID;
import interfaces.BaseStorageInterface;
import models.Policy;

public class PolicyStorage implements BaseStorageInterface {

    public ArrayList<Policy> policies;
    private static PolicyStorage single_instance = null;

    private PolicyStorage() {
        this.policies = new ArrayList<Policy>();
    }

    public static PolicyStorage getInstance() {
        if (single_instance == null) {
            single_instance = new PolicyStorage();
        }

        return single_instance;
    }

    public ArrayList<Policy> getData() {
        return this.policies;
    }

    public void addData(Policy policy) {
        this.policies.add(policy);
    }

    public void removeData(Policy policy) {
        this.policies.remove(policy);
    }

    @Override
    public Integer getCount() {
        return this.policies.size();
    }

    @Override
    public boolean doesExist(UUID instanceId) {
        for (Policy policy : this.getData()) {
            if (policy.getId() == instanceId) {
                return true;
            }
        }
        return false;
    }

}
