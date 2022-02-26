package businessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem implements Serializable {
    private List<MenuItem> items;
    private static final long serialVersionUID = 1L;


    public CompositeProduct(String itemName, int itemPrice, int itemID, List<MenuItem> items) {
        super(itemName, itemPrice, itemID);
        this.items = items;
    }


    public List<MenuItem> getItems() {
        return items;
    }

    @Override
    public int computePrice() {
      int price = 0;
      for(MenuItem i : items){
          price += i.computePrice();
      }
      this.setItemPrice(price);
      return price;
    }
}
