package kp.model;

import java.io.Serializable;

public class Company implements Serializable {
    private long id;
    private String name;
    private String address;

    public Company()
    {}
    public Company(String name, String address) {
        this.name = name;
        this.address = address;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
