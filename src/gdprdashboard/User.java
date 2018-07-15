package gdprdashboard;

public class User {
    private String name;
    private String username;
    private String userId;

    public String getUserId() {
        return userId;
    }

    User(String name, String username, String userId){
        this.name=name;
        this.username=username;
        this.userId=userId;
    }
}