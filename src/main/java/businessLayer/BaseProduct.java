package businessLayer;


import java.io.Serializable;

public class BaseProduct extends MenuItem implements Comparable<BaseProduct>, Serializable {
    private String title;
    private float rating;
    private int calories;
    private int proteins;
    private int fats;
    private int sodium;
    private int price;
    private static final long serialVersionUID = 1L;

    public BaseProduct(int itemID, String title, float rating, int calories, int proteins, int fats, int sodium, int price) {
        super(title, price, itemID);
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    @Override
    public int computePrice() {
         return this.getPrice();
    }

    @Override
    public int compareTo(BaseProduct o) {
        if(this.getItemID() > o.getItemID()) return 1;
        else if(this.getItemID() < o.getItemID()) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return "BaseProduct{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", id = " + getItemID() +
                '}';
    }
}
