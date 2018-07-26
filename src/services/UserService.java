package services;

import java.util.ArrayList;
import java.util.UUID;
import interfaces.ServiceInterface;
import storages.UserStorage;
import models.User;

public class UserService implements ServiceInterface {

    private UserStorage users;

    public UserService() {
        UserStorage storageInstance = UserStorage.getInstance();
        this.users = storageInstance;
    }

    public void addUser(User user) {
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
            if (user.getId().equals(userId)) {
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

    public ArrayList<User> getUserInstances() {
        return this.users.getData();
    }
}
