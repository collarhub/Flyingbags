package net.flyingbags.flyingapps.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Invoice {

    public String status;
    public String location;
    public String target;

    public Invoice() {
        // Default constructor required for calls to DataSnapshot.getValue(newOrders.class)
    }

    public Invoice(String status, String location, String target) {
        this.status = status;
        this.location = location;
        this.target = target;
    }

}