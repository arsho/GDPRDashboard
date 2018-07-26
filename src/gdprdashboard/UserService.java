package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class UserService implements ServiceInterface {

    private ArrayList<User> users;

    public UserService() {
        UserStorage storageInstance = UserStorage.getInstance();
        this.users = storageInstance;
    }
    public void addUser(User user){
        this.users.addData(user);
    }

    public UUID createUser(String name, String email, String country) {
        User user = new User(name, email, country);
        this.addUser(user);
        return user.getId();
    }

    public void deleteUser(UUID userId) {
        this.users.removeData(this.getUser(userId));
    }

    public User getUser(UUID userId) {
        User currentUser = null;
        for (User user : this.getUserInstances()) {
            if (user.getId() == userId) {
                currentUser = user;
            }
        }
        return currentUser;
    }

    public String getUserName(UUID userId) {
        return this.getUser(userId).getName();
    }

    public void updateUser(UUID userId, String name, String email, String country) {
        User user = this.getUser(userId);
        user.setCountry(country);
        user.setEmail(email);
        user.setName(name);
    }

    public ArrayList<UUID> getUsers() {
        ArrayList<UUID> userIdList = new ArrayList<UUID>();
        for (User user : this.getUserInstances()) {
            userIdList.add(user.getId());
        }
        return userIdList;
    }

    public ArrayList<UUID> getUserInstances() {
        return this.users.getData();
    }
}
