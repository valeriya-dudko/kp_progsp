package kp.model;

import java.io.Serializable;
import java.sql.Date;

public class Passing implements Serializable {
    private long id;
    private Date arrivalDate;
    private Date departureDate;

    private Post post;
    private Carrier carrier;

    public Passing()
    {}
    public Passing(Date arrivalDate, Date departureDate)
    {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getArrivalDate() {
        return arrivalDate;
    }
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    public Date getDepartureDate() {
        return departureDate;
    }
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public Carrier getCarrier() {
        return carrier;
    }
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
}
