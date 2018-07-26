package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public class UserService implements ServiceInterface {

    private ArrayList<User> users;

    public UserService() {
        this.users = new ArrayList<User>();
    }

    public UUID createUser(String name, String email, String country) {
        User user = new User(name, email, country);
        this.users.add(user);
        return user.getId();
    }

    public void deleteUser(UUID userId) {
        this.users.remove(this.getUser(userId));
    }

    public User getUser(UUID userId) {
        User currentUser = null;
        for (User user : this.users) {
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
        for (User user : this.users) {
            userIdList.add(user.getId());
        }
        return userIdList;
    }
}
