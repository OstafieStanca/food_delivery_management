package businessLayer;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * @author Ostafie Stanca
 * Interface of DeliveryService
 */


public interface IDeliveryServiceProcessing {
    /**
     * This methos imports all the base products from the cvs file (products.cvs)
     * @throws IOException throws an IO exception
     */
    void importProducts() throws IOException;

    /**
     * This method will select products from the imported list based on column's Rating value
     * @param minVal minimum Value
     * @param maxVal maximum Value
     * @return the list with the products that have the rating in interval [minimum, maximum]
     * @Needs ({"minVal >= 0" ,"maxVal >= 0"})
     */
    HashSet<BaseProduct> searchBaseProductsBasedOnRating(float minVal, float maxVal);

    /**
     * This method will select products from the imported list based on column's Fats value
     * @param minVal minimum Value
     * @param maxVal maximum Value
     * @return the list with the products that have the fats in interval [minimum, maximum]
     * @Needs ({"minVal >= 0" ,"maxVal >= 0"})
     */
    HashSet<BaseProduct> searchBaseProductsBasedOnFats(int minVal, int maxVal);

    /**
     * This method will select products from the imported list based on column's Calories value
     * @param minVal minimum Value
     * @param maxVal maximum Value
     * @return the list with the products that have the calories in interval [minimum, maximum]
     * @Needs ({"minVal >= 0" ,"maxVal >= 0"})
     */
    HashSet<BaseProduct> searchBaseProductsBasedOnCalories(int minVal, int maxVal);

    /**
     * This method will select products from the imported list based on column's Proteins value
     * @param minVal minimum Value
     * @param maxVal maximum Value
     * @returnthe list with the products that have the proteins in interval [minimum, maximum]
     * @Needs ({"minVal >= 0" ,"maxVal >= 0"})
     */
    HashSet<BaseProduct> searchBaseProductsBasedOnProteins(int minVal, int maxVal);

    /**
     * This method will select products from the imported list based on column's Sodium value
     * @param minVal minimum Value
     * @param maxVal maximum Value
     * @return list with the products that have the sodium in interval [minimum, maximum]
     * @Needs ({"minVal >= 0" ,"maxVal >= 0"})
     */
    HashSet<BaseProduct> searchBaseProductsBasedOnSodium(int minVal, int maxVal);

    /**
     * This method will select products from the imported list based on column's Price value
     * @param minVal minimum Value
     * @param maxVal maximum Value
     * @return list with the products that have the price in interval [minimum, maximum]
     * @Needs ({"minVal >= 0" ,"maxVal >= 0"})
     */
    HashSet<BaseProduct> searchBaseProductsBasedOnPrice(int minVal, int maxVal);

    /**
     * This method will select products from the imported list based on column's Id value
     * @param minVal minimum Value
     * @param maxVal maximum Value
     * @return list with the products that have the id in interval [minimum, maximum]
     * @Needs ({"minVal >= 0" ,"maxVal >= 0"})
     */
    HashSet<BaseProduct> searchBaseProductsBasedOnId(float minVal, float maxVal);

    /**
     * This method will order a base product list ascending
     * @param baseP the list that will be sorted
     * @return a HashSet structure with the ordered elements
     */
    HashSet<BaseProduct> orderBaseProducts(HashSet<BaseProduct> baseP);

    /**
     * This method will add a new Base Product to the existing list
     * @param product the element to be added
     * @Needs ({"product!=null"})
     */
    void addProduct(BaseProduct product);

    /**
     * This method will delete an existing Base Product from the list
     * @param product the product to be deleted
     * @Needs ({"product!=null"})
     */
    void deleteProduct(BaseProduct product);

    /**
     * This method will modify an existing Base Product from the list
     * @param name name
     * @param rating rating
     * @param calories calories
     * @param proteins proteins
     * @param fats fats
     * @param sodium sodium
     * @param price price
     * @param productId productId
     * @Needs ({"name != null", "rating > 0", "calories >0", "proteins >0", "fats>0", "sodium >0", "price >0"})
     */
    void modifyProduct(String name, float rating, int calories, int proteins, int fats, int sodium, int price, int productId);

    /**
     * This method will create a new Composite Product with the given selected products
     * @param selectedProducts selectedProducts list
     * @param productName the product name
     * @param productID the product id
     * @return the new Composite Product that has been created
     * @Needs ({"selectedProducts!=null"})
     */
    CompositeProduct createCompositeProduct(List<MenuItem> selectedProducts, String productName, int productID);

    /**
     * This method will generate the menu with the given ids for the products
     * @param ids list of ids
     * @Needs ({"ids!=null"})
     */
    void generateMenu(List<Integer> ids);

    /**
     * This method will create a new Order and place it in the HashSet
     * @param clientID clientID
     * @param items items
     * @return the order just created with the given data
     * @Needs ({"clientID >0", "items!=null"})
     */
    Order createOrder(int clientID, HashSet<MenuItem> items);

    /**
     * This method will serialize the data form Delivery Service
     */
    void saveInf();

    /**
     * This method generates the report for the products that have been ordered more than a giving value
     * @param value value
     * @throws IOException throws IO exception
     * @Needs ({"value>0"})
     */
    void reportMostOrdersProducts(int value) throws IOException;

    /**
     * This method generates the report for the orders that have been placed in a time interval
     * @param start start hour
     * @param end end hour
     * @throws IOException throws IO exception
     * @Needs ({"start < end"})
     */
    void reportBasedOnHourInterval(int start, int end) throws IOException;


}
