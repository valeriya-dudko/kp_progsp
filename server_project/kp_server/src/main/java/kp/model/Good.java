package kp.model;

import java.io.Serializable;

public class Good implements Serializable {
    private long id;
    private String name;
    private int amount;
    private double weight;
    private double price;

    private int rateType;
    private double rate;
    private int VAT;
    private double excise;

    private boolean isConfirmed;
    private boolean isImport;

    private Declarant declarant;

    public Good()
    {}

    public Good(String name, int amount, double weight, double price) {
        this.name = name;
        this.amount = amount;
        this.weight = weight;
        this.price = price;
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
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getRateType()
    {
        return rateType;
    }
    public void setRateType(int rateType)
    {
        this.rateType = rateType;
    }
    public double getRate()
    {
        return rate;
    }
    public void setRate(double rate)
    {
        this.rate = rate;
    }
    public Integer getVAT()
    {
        return VAT;
    }
    public void setVAT(int VAT)
    {
        this.VAT = VAT;
    }
    public double getExcise()
    {
        return excise;
    }
    public void setExcise(double excise)
    {
        this.excise = excise;
    }

    public boolean getIsConfirmed() {
        return isConfirmed;
    }
    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
    public boolean getIsImport() {
        return isImport;
    }
    public void setIsImport(boolean isImport) {
        this.isImport = isImport;
    }

    public Declarant getDeclarant() {
        return declarant;
    }
    public void setDeclarant(Declarant declarant) {
        this.declarant = declarant;
    }
}
