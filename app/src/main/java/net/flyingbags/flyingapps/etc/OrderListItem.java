package net.flyingbags.flyingapps.etc;

/**
 * Created by User on 2017-10-22.
 */

public class OrderListItem {
    private String orderId;
    private String orderDate;
    private String packageType;
    private String price;
    private String subTotal;
    private String target;
    private String totalPrice;

    public OrderListItem(String orderId, String orderDate, String packageType, String price, String subTotal, String target, String totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.packageType = packageType;
        this.price = price;
        this.subTotal = subTotal;
        this.target = target;
        this.totalPrice = totalPrice;
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

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
