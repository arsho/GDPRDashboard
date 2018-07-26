package gdprdashboard;

import java.util.ArrayList;

public class UserStorage implements BaseStorage{
    public ArrayList<Core> users;
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
        return this.users;
    }

    @Override
    public void addData(Core user){
        this.users.add(user);
    }
    
    @Override
    public void deleteData(Core user){
        this.users.delete(user);
    }

}