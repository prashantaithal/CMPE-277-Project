package cmpe.sjsu.food4u;

import java.util.ArrayList;

/**
 * Created by manas on 5/9/2018.
 */

public class Order {

    ArrayList<CartItem> cartItemsList;
    Double totalPrice;
    String useremail;
    String orderDate;
    String pickupDate;
    Double totalTime;

    public Order() {

    }

    public Order(Double totalTime, ArrayList<CartItem> cartItemsList, Double totalPrice, String userEmail, String orderDate, String pickupDate) {
        this.cartItemsList = cartItemsList;
        this.orderDate = orderDate;
        this.pickupDate = pickupDate;
        this.totalPrice = totalPrice;
        this.useremail = userEmail;
        this.totalTime = totalTime;
    }

    public ArrayList<CartItem> getCartItemsList() {
        return cartItemsList;
    }

    public void setCartItemsList(ArrayList<CartItem> cartItemsList) {
        this.cartItemsList = cartItemsList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
}
