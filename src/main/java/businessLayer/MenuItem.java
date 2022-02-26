package businessLayer;

import java.io.Serializable;
import java.util.List;

public abstract class MenuItem implements Serializable {
    private String itemName;
    private int itemPrice;
    private int itemID;

    public MenuItem(String itemName, int itemPrice, int itemID) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public abstract int computePrice();

    @Override
    public String toString() {
        return "MenuItem{" +
                "itemName='" + itemName + "" +
                ", itemPrice=" + itemPrice +
                ", itemID=" + itemID +
                '}';
    }
}
