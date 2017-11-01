package net.flyingbags.flyingapps.etc;

import net.flyingbags.flyingapps.model.Route;

import java.util.Map;

/**
 * Created by User on 2017-10-23.
 */

public class StateListItem {
    private String orderId;
    private String orderDate;
    private Map<String, Route> route;

    public StateListItem(String orderId, String orderDate, Map<String,Route> route) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.route = route;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Map<String, Route> getRoute() {
        return route;
    }

    public void setRoute(Map<String, Route> route) {
        this.route = route;
    }
}
