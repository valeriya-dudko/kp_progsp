package kp.model;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String login;
    private byte[] password;
    private int role;
    private boolean isBlocked;

    public User()
    {}

    public User(String login, byte[] password, int role)
    {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public byte[] getPassword() {
        return password;
    }
    public void setPassword(byte[] password) {
        this.password = password;
    }
    public boolean getIsBlocked()
    {
        return isBlocked;
    }
    public void setIsBlocked(boolean isBlocked)
    {
        this.isBlocked = isBlocked;
    }

}
