package org.jfox.example.ejb3.stateful;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 * @author <a href="mailto:yy.young@gmail.com">Young Yang</a>
 */
@Stateful(name = "stateful.ShoppingCartBean")
public class ShoppingCartBean implements ShoppingCart, Serializable {

    private Map<String, Integer> cart = new HashMap<String, Integer>();

    public void buy(String product, int quantity) {
        if (cart.containsKey(product)) {
            int currq = cart.get(product);
            currq += quantity;
            cart.put(product, currq);
        }
        else {
            cart.put(product, quantity);
        }
    }

    public Map<String, Integer> getCartContents() {
        return cart;
    }

    @Remove
    public void checkout() {
        System.out.println("To be implemented");
    }
}
