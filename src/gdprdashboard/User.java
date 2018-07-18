package gdprdashboard;

import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String email;
    private String country;

    public User(String name, String email, String country) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
