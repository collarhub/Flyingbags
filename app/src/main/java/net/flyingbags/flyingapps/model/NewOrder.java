package net.flyingbags.flyingapps.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NewOrder {

    public String status;
    public String location;
    public String target;

    public NewOrder() {
        // Default constructor required for calls to DataSnapshot.getValue(newOrders.class)
    }

    public NewOrder(String status, String location, String target) {
        this.status = status;
        this.location = location;
        this.target = target;
    }

}