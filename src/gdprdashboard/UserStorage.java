package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class UserStorage implements BaseStorageInterface{
    public ArrayList<User> users;
    private static UserStorage single_instance = null;

    private UserStorage()
    {
        this.users=new ArrayList<User>();
    }

    public static UserStorage getInstance()
    {
        if (single_instance == null)
            single_instance = new UserStorage();
 
        return single_instance;
    }

    public ArrayList<User> getData(){
        return this.users;
    }

    public void addData(User user){
        this.users.add(user);
    }
    
    public void removeData(User user){
        this.users.remove(user);
    }

    @Override
    public Integer getCount() {
        return this.getData().size();
    }

    @Override
    public boolean doesExist(UUID userId) {
        for (User user : this.getData()) {
            if (user.getId() == userId) {
                return true;
            }
        }
        return false;
    }
}