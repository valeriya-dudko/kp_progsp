package kp.model;

import java.io.Serializable;

public class Carrier implements Serializable {
    private long id;
    private String name;

    private Company company;

    public Carrier()
    {}

    public Carrier (String name) {
        this.name = name;
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

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
