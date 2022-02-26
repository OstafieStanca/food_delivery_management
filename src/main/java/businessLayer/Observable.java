package businessLayer;

import java.util.HashSet;

public interface Observable {
    void update(Order order, HashSet<MenuItem> menuItems);
}
