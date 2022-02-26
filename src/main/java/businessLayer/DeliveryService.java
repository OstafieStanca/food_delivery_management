package businessLayer;

import dataLayer.Serializator;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Ostafie Stanca
 * This class contains the main functions that the client, administrator and employee will be able to performe
 * @Invariant ("baseProducts != null")
 */


public class DeliveryService implements IDeliveryServiceProcessing, Serializable {

    private static final long serialVersionUID = 1L;
    private HashSet<MenuItem> menuItems = new HashSet<>();
    private HashSet<BaseProduct> baseProducts = new HashSet<>();
    private List<Order> listOfOrders = new ArrayList<>();
    private HashMap<Order, HashSet<MenuItem>> mapOfOrders = new HashMap<>();
    private int indexIDProduct = 0;
    private int indexIDOrder = 1;
    private int indexOfProductCP;
    private int numberMenu = 2;
    private int userID = 1;



    public DeliveryService() {

    }

    public boolean invariant(){
        if(baseProducts.isEmpty()) return false;
        else return true;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static Predicate<BaseProduct> isRatingInInterval(float minVal, float maxVal) {
        return p -> p.getRating() > minVal && p.getRating() < maxVal;
    }

    public static Predicate<BaseProduct> isIdEqual(float minVal) {
        return p -> p.getItemID() == minVal;
    }


    public static Predicate<BaseProduct> areFatsInInterval(int minVal, int maxVal) {
        return p -> p.getFats() > minVal && p.getFats() < maxVal;
    }

    public static Predicate<BaseProduct> areCaloriesInInterval(int minVal, int maxVal) {
        return p -> p.getCalories() > minVal && p.getCalories() < maxVal;
    }

    public static Predicate<BaseProduct> isProteinInInterval(int minVal, int maxVal) {
        return p -> p.getProteins() > minVal && p.getProteins() < maxVal;
    }

    public static Predicate<BaseProduct> isSodiumInInterval(int minVal, int maxVal) {
        return p -> p.getSodium() > minVal && p.getSodium() < maxVal;
    }

    public static Predicate<BaseProduct> isPriceInInterval(int minVal, int maxVal) {
        return p -> p.getPrice() > minVal && p.getPrice() < maxVal;
    }

    public static Predicate<Order> isHourInInterval(int minVal, int maxVal) {
        return o -> o.getTheHour() >= minVal && o.getTheHour() <= maxVal;
    }

    public HashSet<MenuItem> getMenuItems() {
        return menuItems;
    }

    public HashSet<BaseProduct> getBaseProducts() {
        return baseProducts;
    }

    public void importProducts() throws IOException {
        HashSet<BaseProduct> extractBaseProducts = new HashSet<>();
        List<String> lines = Files.lines(Paths.get("products.csv")).skip(1).collect(Collectors.toList());
        for (String l : lines) {
            if (this.indexIDProduct > 0) {
                String[] base = l.split(",");
                String title = base[0];
                float rating = Float.parseFloat(base[1].replace(" ", ""));
                int calories = Integer.parseInt(base[2]);
                int proteins = Integer.parseInt(base[3]);
                int fats = Integer.parseInt(base[4]);
                int sodium = Integer.parseInt(base[5]);
                int price = Integer.parseInt(base[6]);
                BaseProduct product = new BaseProduct(indexIDProduct, title, rating, calories, proteins, fats, sodium, price);
                extractBaseProducts.add(product);
            }
            indexIDProduct++;
        }
        baseProducts = (HashSet<BaseProduct>) extractBaseProducts.stream().filter(distinctByKey(p -> p.getItemName())).collect(Collectors.toSet());

    }

    public HashSet<BaseProduct> searchBaseProductsBasedOnRating(float minVal, float maxVal) {
        assert minVal < 0 || maxVal < 0 : "Incorrect values!";
        assert invariant() : "Empty menu";
        HashSet<BaseProduct> foundOnRatingProducts;
        foundOnRatingProducts = (HashSet<BaseProduct>) baseProducts.stream().filter(isRatingInInterval(minVal, maxVal)).collect(Collectors.toSet());
        return foundOnRatingProducts;
    }

    public HashSet<BaseProduct> searchBaseProductsBasedOnId(float minVal, float maxVal) {
        assert minVal < 0 || maxVal < 0 : "Incorrect values!";
        assert invariant() : "Empty menu";
        HashSet<BaseProduct> foundOnIdProducts;
        foundOnIdProducts = (HashSet<BaseProduct>) baseProducts.stream().filter(isIdEqual(minVal)).collect(Collectors.toSet());
        return foundOnIdProducts;
    }

    public HashSet<BaseProduct> searchBaseProductsBasedOnFats(int minVal, int maxVal) {
        assert minVal < 0 || maxVal < 0 : "Incorrect values!";
        assert invariant() : "Empty menu";
        HashSet<BaseProduct> foundOnFatsProducts;
        foundOnFatsProducts = (HashSet<BaseProduct>) baseProducts.stream().filter(areFatsInInterval(minVal, maxVal)).collect(Collectors.toSet());
        return foundOnFatsProducts;
    }

    public HashSet<BaseProduct> searchBaseProductsBasedOnCalories(int minVal, int maxVal) {
        assert minVal < 0 || maxVal < 0 : "Incorrect values!";
        HashSet<BaseProduct> foundOnCaloriesProducts;
        foundOnCaloriesProducts = (HashSet<BaseProduct>) baseProducts.stream().filter(areCaloriesInInterval(minVal, maxVal)).collect(Collectors.toSet());
        return foundOnCaloriesProducts;
    }

    public HashSet<BaseProduct> searchBaseProductsBasedOnProteins(int minVal, int maxVal) {
        assert minVal < 0 || maxVal < 0 : "Incorrect values!";
        HashSet<BaseProduct> foundOnProteinsProducts;
        foundOnProteinsProducts = (HashSet<BaseProduct>) baseProducts.stream().filter(isProteinInInterval(minVal, maxVal)).collect(Collectors.toSet());
        return foundOnProteinsProducts;
    }

    public HashSet<BaseProduct> searchBaseProductsBasedOnSodium(int minVal, int maxVal) {
        assert minVal < 0 || maxVal < 0 : "Incorrect values!";
        HashSet<BaseProduct> foundOnSodiumProducts;
        foundOnSodiumProducts = (HashSet<BaseProduct>) baseProducts.stream().filter(isSodiumInInterval(minVal, maxVal)).collect(Collectors.toSet());
        return foundOnSodiumProducts;
    }

    public HashSet<BaseProduct> searchBaseProductsBasedOnPrice(int minVal, int maxVal) {
        assert minVal < 0 || maxVal < 0 : "Incorrect values!";
        HashSet<BaseProduct> foundOnPriceProducts;
        foundOnPriceProducts = (HashSet<BaseProduct>) baseProducts.stream().filter(isPriceInInterval(minVal, maxVal)).collect(Collectors.toSet());
        return foundOnPriceProducts;
    }

    public void addProduct(BaseProduct product) {
        assert product == null : "Nonexistent product!";
        product.setItemID(indexOfProductCP);
        indexIDProduct += 10;
        baseProducts.add(product);
    }

    public void deleteProduct(BaseProduct product) {
        assert product == null : "Nonexistent product!";
        for (BaseProduct b : baseProducts) {
            if (b.getItemID() == product.getItemID()) {
                baseProducts.remove(b);
                break;
            }
        }
    }

    public void modifyProduct(String name, float rating, int calories, int proteins, int fats, int sodium, int price, int productId) {
        assert name == null || calories < 0 || rating < 0 || proteins < 0 || fats < 0 || sodium < 0 || price < 0 : "Incorrect values";
        for (BaseProduct b : baseProducts) {
            if (b.getItemID() == productId) {
                b.setItemName(name);
                b.setCalories(calories);
                b.setRating(rating);
                b.setPrice(price);
                b.setProteins(proteins);
                b.setFats(fats);
                b.setSodium(sodium);
            }
        }
    }

    public CompositeProduct createCompositeProduct(List<MenuItem> selectedProducts, String productName, int productID) {
        assert selectedProducts.isEmpty() : "No products!";
        indexOfProductCP = baseProducts.size() + 10;
        CompositeProduct newProduct = new CompositeProduct(productName, 0, indexOfProductCP, selectedProducts);
        int newPrice = newProduct.computePrice();
        newProduct.setItemPrice(newPrice);
        BaseProduct bp = new BaseProduct(indexOfProductCP, productName, 0, 0, 0, 0, 0, newPrice);
        baseProducts.add(bp);
        return newProduct;
    }

    public void generateMenu(List<Integer> ids) {
        assert ids.isEmpty() : "No products to add";
        List<BaseProduct> listOfBaseProductsSorted = new ArrayList<>(baseProducts);
        Collections.sort(listOfBaseProductsSorted);
        ids.sort(Comparator.comparing(Integer::valueOf));
        int index = 0;
        for (BaseProduct b : listOfBaseProductsSorted) {
            if (b.getItemID() == ids.get(index)) {
                menuItems.add(b);
                index++;
                if (index > ids.size() - 1) break;
            }
        }
    }

    public Order createOrder(int clientID, HashSet<MenuItem> items) {

        assert clientID < 0 || items.isEmpty() : "Incorrect data!!";
        Date orderDate = new Date();
        Order order = new Order(indexIDOrder++, clientID, orderDate);
        listOfOrders.add(order);
        HashSet<MenuItem> oMenuItems = new HashSet<>(menuItems);
        mapOfOrders.put(order, oMenuItems);
        return order;

    }

    public HashSet<BaseProduct> orderBaseProducts(HashSet<BaseProduct> baseP) {
        List<BaseProduct> bs = new ArrayList<>(baseP);
        Collections.sort(bs);
        HashSet<BaseProduct> res = new HashSet<>(bs);
        return res;
    }

    public void saveInf() {
        Serializator s = new Serializator();
        try {
            s.serialize(this, "deliveryService.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processOrder() {
        Order order = null;
        int minim = 100000;
        for (Map.Entry<Order, HashSet<MenuItem>> map : mapOfOrders.entrySet()) {
            int k = map.getKey().getOrderID();
            if (k < minim) {
                minim = k;
                order = map.getKey();
            }
        }
        mapOfOrders.remove(order);

    }

    public HashMap<Order, HashSet<MenuItem>> getMapOfOrders() {
        return mapOfOrders;
    }

    public void setIndexIDProduct(int indexIDProduct) {
        this.indexIDProduct = indexIDProduct;
    }

    public int getIndexOfProductCP() {
        return indexOfProductCP;
    }

    public int getNumberMenu() {
        return numberMenu;
    }

    public void setNumberMenu(int numberMenu) {
        this.numberMenu = numberMenu;
    }

    public void generateBill(HashSet<MenuItem> menuItem, Order order) throws IOException {
        FileWriter fw = new FileWriter("bill.txt");
        int price = 0;
        String pattern = "MM-dd-yyyy";
        String content = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(order.getOrderDate());
        content += "The order with id : " + order.getOrderID() + " was placed at date : " + date + " successfully! \nProducts of the order : \n";
        for (MenuItem m : menuItem) {
            content += m.getItemName() + ", price = " + m.getItemPrice() + "\n";
            price += m.getItemPrice();
        }
        content += "Total price : " + price + "\n";
        fw.write(content);
        fw.close();
    }


    public void reportBasedOnHourInterval(int start, int end) throws IOException {
        assert start > end : "Invalid interval!";
        FileWriter fw = new FileWriter("reportBasedOnHourInterval.txt");
        List<Order> hourOrder = listOfOrders.stream().filter(isHourInInterval(start, end)).collect(Collectors.toList());
        String content = "";

        content += "The orders placed between " + start + " and " + end + " , regardless the date, are : \n";
        for (Order o : hourOrder) {
            content += "Id order " + o.getOrderID() + ", by client " + o.getClientID() + "\n";
        }
        fw.write(content);
        fw.close();

    }

    public void reportMostOrdersProducts(int value) throws IOException {
        assert value < 0 : "Invalid input value!";
        StringBuilder sb = new StringBuilder();
        List<MenuItem> mi = new ArrayList<>();
        mapOfOrders.keySet().stream().forEach(order->mi.addAll(mapOfOrders.get(order)));
        HashMap<MenuItem, Long> aparitii = mi.stream().collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));
        Set<MenuItem> rez = aparitii.keySet().stream().filter(menuItem -> aparitii.get(menuItem) >= value).collect(Collectors.toSet());
        rez.stream().forEach(menuItem -> sb.append(menuItem.toString()).append("  ").append(aparitii.get(menuItem)).append("\n\n\n"));
        FileWriter fw = new FileWriter("reportMostOrdersProduct.txt");
        fw.write(String.valueOf(rez));
        fw.close();
    }

    public void reportClients(int value, int amount) throws IOException {
        FileWriter fw = new FileWriter("reportClients.txt");
        String content = "";
        int[] timesTheyOrdered = new int[1000];
        for (int i = 0; i < 1000; i++) {
            timesTheyOrdered[i] = 0;
        }
        content += "Clients that order more than a specified number of times and the amount of the order is grater than a specified amount are :\n";
        mapOfOrders.forEach((k, v) -> {
            if (v.stream().mapToInt(MenuItem::getItemPrice).sum() >= amount) {
                timesTheyOrdered[k.getClientID()]++;
            }
        });
        for (int i = 0; i < 1000; i++) {
            if (timesTheyOrdered[i] != 0 && timesTheyOrdered[i] >= value) {
                content += "Client with id " + i + "\n";
            }
        }
        fw.write(content);
        fw.close();
    }

    public void reportMostOrderedProductsOnDay(Date date) throws IOException {

        FileWriter fw = new FileWriter("reportProductesFromADay.txt");
        String content = "";
        int[] times = new int[20000];
        for (int i = 0; i < 20000; i++) {
            times[i] = 0;
        }
        content += "The list of products ordered within the day : " + date.getDay() + " with the number of times they have been ordered\n";
        mapOfOrders.forEach((k, v) -> {
            if (k.getOrderDate().getDay() == date.getDay() && k.getOrderDate().getMonth() == date.getMonth())
                v.forEach(menuItem -> {
                    times[menuItem.getItemID()]++;
                });
        });
        for (int i = 0; i < 20000; i++) {
            if (times[i] > 0)
                content += "The product with  ID: " + i + " appears " + times[i] + " time/s\n";
        }
        fw.write(content);
        fw.close();

    }

    public int getUserID() {
        userID++;
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
