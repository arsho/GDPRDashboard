package gdprdashboard;

import java.util.ArrayList;

public class PolicyStorage implements BaseStorage{
    public ArrayList<Core> policies;
    private static Singleton single_instance = null;

    private UserStorage()
    {
        this.users=new ArrayList<User>();
    }

    public static UserStorage getInstance()
    {
        if (single_instance == null)
            single_instance = new BaseStorage();
 
        return single_instance;
    }

    @Override
    public ArrayList<Core> getData(){
        return this.policies;
    }

    @Override
    public void addData(Core policy){
        this.policies.add(policy);
    }
    
    @Override
    public void deleteData(Core policy){
        this.policies.delete(policy);
    }

}