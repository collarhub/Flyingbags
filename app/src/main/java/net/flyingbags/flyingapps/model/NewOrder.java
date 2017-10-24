package net.flyingbags.flyingapps.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NewOrder extends Invoice{
    public NewOrder() {
        // Default constructor required for calls to DataSnapshot.getValue(newOrders.class)
    }

}