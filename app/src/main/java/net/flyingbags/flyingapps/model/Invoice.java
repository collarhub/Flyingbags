package net.flyingbags.flyingapps.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Invoice {

    public String status;
    public String location;
    public String target;

    public Date startDate;
    public Date endDate;
    public int sizeOfBox;

    public Date orderDate;

    //route



    public Invoice() {
        // Default constructor required for calls to DataSnapshot.getValue(newOrders.class)
    }

    public Invoice(String status, String location, String target) {
        this.status = status;
        this.location = location;
        this.target = target;
    }

}