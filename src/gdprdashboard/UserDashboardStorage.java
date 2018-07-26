package gdprdashboard;

import java.util.ArrayList;

public class UserDashboardStorage implements BaseStorage{
    public ArrayList<Core> userDashboards;
    private static Singleton single_instance = null;

    private UserStorage()
    {
        this.userDashboards = new ArrayList<UserDashboard>();
    }

    public static UserStorage getInstance()
    {
        if (single_instance == null)
            single_instance = new BaseStorage();
 
        return single_instance;
    }

    @Override
    public ArrayList<Core> getData(){
        return this.userDashboards;
    }

    @Override
    public void addData(Core userDashboard){
        this.userDashboards.add(userDashboard);
    }
    
    @Override
    public void deleteData(Core userDashboard){
        this.userDashboards.delete(userDashboard);
    }

}