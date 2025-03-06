package kp.model;

import java.io.Serializable;

public class Declarant implements Serializable {
    private long id;
    private String name;

    private User user;
    private Company company;

    public Declarant()
    {}

    public Declarant (String name) {
        this.name = name;
        user = new User();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
}
