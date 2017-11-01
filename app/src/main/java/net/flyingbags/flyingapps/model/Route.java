package net.flyingbags.flyingapps.model;

public class Route {
    private String status;
    private String date;

    public Route(){
        // Default constructor required for calls to DataSnapshot.getValue(newOrders.class)
    }

    public Route(String status, String date){
        this.status = status;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
