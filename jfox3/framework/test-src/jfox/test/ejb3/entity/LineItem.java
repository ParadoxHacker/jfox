package jfox.test.ejb3.entity;

import javax.persistence.Column;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class LineItem {
    @Column(name="id")
    Integer id;

    @Column(name="product")
    String product;

    @Column(name = "price")
    Double price;

    @Column(name="quantity")
    int quantity;

    @Column(name="orderid")
    int orderId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
