package net.flyingbags.flyingapps.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Invoice implements Serializable {
    private String orderDate;
    private String deliveryType;
    private String target;
    private String departure;
    private String minDateExpected;
    private String maxDateExpected;
    private String packageType;
    private String price;
    private String status;
    private String location;

    private Map<String, Route> route;

    public Invoice() {
        // Default constructor required for calls to DataSnapshot.getValue(newOrders.class)
    }

    public Invoice(String orderDate, String deliveryType, String target, String departure, String minDateExpected, String maxDateExpected, String packageType, String price, String status, String location) {
        this.orderDate = orderDate;
        this.deliveryType = deliveryType;
        this.target = target;
        this.departure = departure;
        this.minDateExpected = minDateExpected;
        this.maxDateExpected = maxDateExpected;
        this.packageType = packageType;
        this.price = price;
        this.status = status;
        this.location = location;
        //this.route = new HashMap<>();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getMinDateExpected() {
        return minDateExpected;
    }

    public void setMinDateExpected(String minDateExpected) {
        this.minDateExpected = minDateExpected;
    }

    public String getMaxDateExpected() {
        return maxDateExpected;
    }

    public void setMaxDateExpected(String maxDateExpected) {
        this.maxDateExpected = maxDateExpected;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, Route> getRoute() {
        return route;
    }

    public void setRoute(Map<String, Route> route) {
        this.route = route;
    }
}