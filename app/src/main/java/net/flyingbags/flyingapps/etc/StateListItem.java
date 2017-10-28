package net.flyingbags.flyingapps.etc;

/**
 * Created by User on 2017-10-23.
 */

public class StateListItem {
    private String orderId;
    private String status;

    public StateListItem(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
